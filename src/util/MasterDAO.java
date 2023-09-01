package util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.collections.CollectionUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import java.lang.reflect.InvocationTargetException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * @author Sobreira
 * Created on 09/08/2004
 *
 */
public abstract class MasterDAO {
    protected static final ResourceBundle bundle = ResourceBundle.getBundle(Constantes.CONFIG);
    private static SingleConnection singleConnection;
    protected static final int NUM_MAX_REGS_PESQUISA = Integer.parseInt(bundle.getString(
                "NUM_MAX_REGS_PESQUISA"));
    private static final String[] arrayVazio = new String[] {  };
    public static final byte UNKNOWN = 0;
    public static final byte ORACLE = 1;
    public static final byte CACHE = 2;
    public static final byte POSTGRES = 3;
    public static final byte DB2 = 4;
    public static final byte SQLSERVER = 5;
    public static final byte FIREBIRD = 6;
    public static final byte ACCESS = 7;
    public static final byte MYSQL = 8;
    public static final byte PERVASIVE = 9;
    public static final byte HIPERSONIC = 10;
    public static final byte DATABASE = Byte.parseByte(bundle.getString(
                "DATABASE"));
    public static final String P01 = "P01";
    public static final String P02 = "P02";
    public static final String P03 = "P03";
    public static final String P04 = "P04";
    public static final String P05 = "P05";
    public static final String P06 = "P06";
    public static final String P07 = "P07";
    public static final String P08 = "P08";
    public static final String P09 = "P09";
    public static final String P10 = "P10";
    public static final String P11 = "P11";
    public static final String P12 = "P12";
    public static final String P13 = "P13";
    public static final String P14 = "P14";
    public static final String P15 = "P15";
    public static final String P16 = "P16";
    public static final String P17 = "P17";
    public static final String P18 = "P18";
    public static final String P19 = "P19";
    public static final String P20 = "P20";
    private static final String TOTAL = "TOTAL";
    private static boolean dataSourcePool = false;
    protected static Writer sqls;
    protected static boolean gerarSqlsArquivo = false;

    static {
        /**
         * Corrige o bug do BeanUtils pra converter valores de data que esto
         * null.
         */
        ConvertUtils.register(new Converter() {
                public Object convert(Class type, Object value) {
                    SqlTimestampConverter sqlTimestampConverter = new SqlTimestampConverter();

                    if ((value == null) || (value.toString().length() < 1)) {
                        return null;
                    }

                    return sqlTimestampConverter.convert(type, value);
                }
            }, Timestamp.class);
    }

    protected Map queries;

    public MasterDAO(String banco) throws Exception {
        String classNome = this.getClass().getName();
        classNome = classNome.replaceAll(this.getClass().getPackage().getName() +
                ".", "");
        queries = loadXml(classNome + ".xml");

        if (queries == null) {
            queries = loadXml(classNome + "_" + banco + ".xml");
        }
    }

    /**
     * Este mtodo inicia uma transao e devolve o objeto Connection
     * para uso nos demais mtodos envolvidos na transao.
     * PARA EFETIVAO DA TRANSAO DEVE-SE EXECUTAR O MTODO
     * <b>concluirTransacao();</b>.
     * @return
     * @throws Exception
     */
    public Object iniciarTransacao() throws Exception {
        Connection connection = getConexao();
        beginTrans(connection);

        return connection;
    }

    /**
     * Comita uma transao.
     * DEVE SER PRECEDIDO DE UM <b>iniciarTransacao();</b>.
     * @param o - Objeto Connection
     * @throws Exception
     * @throws SQLException
     */
    public void concluirTransacao(Object o) throws Exception, SQLException {
        if (o instanceof Connection) {
            Connection connection = (Connection) o;

            try {
                commitTrans(connection);
            } catch (SQLException e) {
                throw e;
            } finally {
                liberaConeccao(connection);
            }
        } else {
            throw new Exception("O objeto deve ser um Connection!");
        }
    }

    /**
     * Aborta uma transao.
     * DEVE SER PRECEDIDO DE UM <b>iniciarTransacao();</b>.
     * @param o - Objeto Connection
     * @throws Exception
     * @throws SQLException
     * @throws SQLException
     *
     */
    public void abortarTransacao(Object o) throws Exception, SQLException {
        if (o == null) {
            return;
        }

        if (o instanceof Connection) {
            Connection connection = (Connection) o;

            try {
                rollBackTrans(connection);
            } finally {
                liberaConeccao(connection);
            }
        } else {
            throw new Exception("O objeto deve ser um Connection!");
        }
    }

    /**
     * Previne que um mtodo de DAO transacional seja chamado fora de uma
     *  transao.
     * @param o - Objeto Connection
     * @return
     * @throws Exception
     * @throws SQLException
     */
    protected Connection validarTransacao(Object o)
        throws Exception, SQLException {
        if ("S".equals(bundle.getString("MODODEBUG")) &&
                "S".equals(bundle.getString("SINGLE_CONNECTION"))) {
            return (Connection) o;
        }

        if (o instanceof Connection) {
            Connection conn = (Connection) o;

            if (!conn.getAutoCommit()) {
                return conn;
            }
        }

        throw new Exception("Este mtodo deve ser executado em uma transao!");
    }

    /**
     * Traz uma consulta registrada no XML.
     * @param queryName
     * @return
     * @throws Exception
     */
    protected String getQuery(String queryName) throws Exception {
        if (queryName == null) {
            throw new Exception("Requisio de uma Consulta SQL NULL\n" +
                "Inicialize as constantes de consulta!");
        }

        if (queries == null) {
            throw new Exception("Este DAO, " + this.getClass().getName() +
                ", no possui \n Arquivo XML relacionado. \nVerifique se" +
                "o nome do XMl  o mesmo do DAO.");
        }

        return (String) queries.get(queryName);
    }

    /**
     * Carrega o arquivo XML trazendo as consultas para o mapa de queries.
     * @param arquivoXML
     * @return
     * @throws Exception
     */
    protected Map loadXml(String arquivoXML) throws Exception {
        HashMap queries = new HashMap();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);

            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream stream = getClass().getResourceAsStream(arquivoXML);

            if (stream == null) {
                return null;
            }

            Document doc = db.parse(stream);
            Element elem = doc.getDocumentElement();
            NodeList nl = elem.getElementsByTagName("query");

            for (int i = 0; i < nl.getLength(); i++) {
                Element query = (Element) nl.item(i);
                queries.put(query.getAttribute("nome"),
                    query.getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            Debuger.debugar(e, getClass());
            throw new Exception(
                "Erro carregando arquivo xml associado ao DAO :" + arquivoXML);
        }

        return queries;
    }

    /**
     * Obtm uma coneco do pool ou abre uma se o contexto web estiver como <i>N</i>
     * no arquivo de propriedades.
     * @return
     * @throws Exception
     */
    public static Connection getConexao() throws Exception {
        Connection connection = null;

        if ("S".equals(bundle.getString("SINGLE_CONNECTION")) &&
                (singleConnection != null)) {
            return singleConnection;
        }

        try {
            Context initContext = new InitialContext();

            if (initContext == null) {
                throw new Exception("Boom - No Context");
            }

            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup(bundle.getString(
                        "DATA_SOURCE"));

            connection = ds.getConnection();
            dataSourcePool = true;
            gerarSqlsArquivo = false;
        } catch (Exception e) {
            if ("N".equals(bundle.getString("MODODEBUG"))) {
                e.printStackTrace();
                throw new Exception("Problemas acessando a base de dados.");
            }

            while (connection == null) {
                connection = carregarConneccaoProperties();
            }

            dataSourcePool = false;
        }

        return connection;
    }

    private static Connection carregarConneccaoProperties() {
        try {
            if ("S".equals(bundle.getString("SINGLE_CONNECTION")) &&
                    (singleConnection != null)) {
                return singleConnection;
            }

            Class.forName(bundle.getString("DBDRIVER"));

            Connection connection = DriverManager.getConnection(bundle.getString(
                        "DBURL"), bundle.getString("DBUSER"),
                    bundle.getString("DBPASS"));

            if (connection == null) {
                Debuger.debugar("Coneco " + bundle.getString("DBURL") +
                    " Nula", MasterDAO.class);
            }

            singleConnection = new SingleConnection(connection);

            return singleConnection;
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }

    /**
     * Retorna informao sobre o estado do pool de coneces.
     * @return - true se o pool estiver ok.
     */
    public static boolean isPoolOK() {
        return dataSourcePool;
    }

    /**
     * Devolve uma coneco ao pool.
     * @param conn
     */
    public void liberaConeccao(Connection conn) {
        try {
            if (gerarSqlsArquivo) {
                return;
            }

            conn.setAutoCommit(true);

            if (conn.isClosed()) {
                throw new Exception("Coneco j fechada pelo banco " +
                    "devido algum erro.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mtodo para iniciar uma transao.
     * @param conn - Connection
     * @throws SQLException
     *
     **/
    protected void beginTrans(Connection conn) throws SQLException {
        if (gerarSqlsArquivo) {
            return;
        }

        if (conn != null) {
            conn.setAutoCommit(false);
        }
    }

    /**
     * Mtodo para roll back.
     * @param conn - Connection
     * @throws SQLException
     *
     * */
    protected void rollBackTrans(Connection conn) throws SQLException {
        if (gerarSqlsArquivo) {
            return;
        }

        if (conn != null) {
            conn.rollback();
        }
    }

    /**
     * Mtodo usado para comitar.
     * @param conn - Connection
     * @throws SQLException
     *
     * */
    protected void commitTrans(Connection conn) throws SQLException {
        if (gerarSqlsArquivo) {
            return;
        }

        if (conn != null) {
            conn.commit();
        }
    }

    /**
     * @deprecated
     * Preenche um VO dado cujo o nome da tabela  igual ao do VO.
     * @param conn - Coneco ativa
     * @param VO -  Instncia de amostra do VO
     * @param where - Clusula Where
     * @return - Retorna Uma lista com Instacia do VO passado como parametro
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String where)
        throws Exception {
        return preencheVO(conn, VO, Util.tabelaVONome(VO), where);
    }

    /**
     * @deprecated
     * Preenche um VO dado cujo o nome da tabela  igual ao do VO e sem
     * a clusula where.
     * @param conn -  Coneco ativa
     * @param VO   -  Instncia de amostra do VO
     * @return - Retorna Uma lista com Instacia do VO passado como parametro
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO)
        throws Exception {
        return preencheVO(conn, VO, Util.tabelaVONome(VO), "");
    }

    /**
     * @deprecated
     * Preenche um VO dado cujo o nome da tabela  igual ao do VO e com
     * a clusula where e propriedades excluidas.
     * @param conn -  Coneco ativa
     * @param VO   -  Instncia de amostra do VO
     * @param where - Clusula Where
     * @param excluidas - String com o nome das propiedade que no iro na clausula select
     * @return - Retorna Uma lista com Instacia do VO passado como parametro
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String where,
        String[] excluidas) throws Exception {
        return preencheVO(conn, VO, Util.tabelaVONome(VO), where, excluidas);
    }

    /**
     * @deprecated
     * @param conn -  Coneco ativa
     * @param VO   -  Instncia de amostra do VO
     * @param tabela - Nome da(s) tabela(s) contempladas na clausula From
     * @param where - Clusula Where
     *  @return - Retorna Uma lista com Instacia do VO passado como parametro
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String tabela,
        String where) throws Exception {
        return preencheVO(conn, VO, tabela, where, arrayVazio);
    }

    /**
     * @deprecated
     * Preenche um VO dado o nome da tabela passando como parmetro um List de
     * String com os nomes das propriedades que devem ser exibidas.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     * @param VO
     * @param tabela
     * @param where
     * @param propriedadesExibiveis
     * @return
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String tabela,
        String where, List propriedadesExibiveis) throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) &&
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        return preencheVO(conn, VO, tabela, where,
            Util.converterListDeStringParaArrayDeString(remover));
    }

    /**
     * Pesquisa e Preenche um VO dado o nome da tabela passando como parmetro um List de
     * String com os nomes das propriedades que devem ser exibidas.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     * @param VO
     * @param tabela
     * @param where
     * @param propriedadesExibiveis
     * @param validaSql
     * @return Lista do VO pesquisado
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object VO, String where,
        String tabelas, List propriedadesExibiveis, boolean validaSql)
        throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) &&
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        return pesquisarVO(conn, VO, where, tabelas,
            Util.converterListDeStringParaArrayDeString(remover), 0, validaSql);
    }

    /**
     * Pesquisa e Preenche um VO dado o nome da tabela passando como parmetro um List de
     * String com os nomes das propriedades que devem ser exibidas.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     * @param VO
     * @param tabela
     * @param where
     * @param propriedadesExibiveis
     * @param validaSql
     * @return Lista do VO pesquisado
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object VO, String where,
        String tabelas, String[] propriedadesExcluidas, boolean validaSql)
        throws Exception {
        return pesquisarVO(conn, VO, where, tabelas, propriedadesExcluidas, 0,
            validaSql);
    }

    /**
     * Pesquisa e Preenche um VO dado o nome da tabela passando como parmetro um List de
     * String com os nomes das propriedades que devem ser exibidas.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     * @param VO
     * @param tabela
     * @param where
     * @param propriedadesExibiveis
     * @return Lista do VO pesquisado
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object VO, String where,
        String tabela, List propriedadesExibiveis) throws Exception {
        return pesquisarVO(conn, VO, where, tabela, propriedadesExibiveis, false);
    }

    /**
     * @deprecated
     * Preenche um VO dado o nome da tabela passando como parmetro um List de
     * String com os nomes das propriedades que devem ser exibidas.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     * @param conn
     * @param VO
     * @param where
     * @param propriedadesExibiveis
     * @return
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String where,
        List propriedadesExibiveis) throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) &&
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        return preencheVO(conn, VO, where,
            Util.converterListDeStringParaArrayDeString(remover));
    }

    /**
     * @deprecated
     * Preenche um VO dado o nome da tabela (Metodo Original)
     * @param conn   - Coneco ativa
     * @param VO     - Instncia de amostra do VO
     * @param tabela - Nome da Tabela
     * @param where  - Clusula Where
     * @param propriedadesExcluidas - propriedades do VO que no dever ser pesquisadas.
     * @return
     * @throws Exception
     */
    protected List preencheVO(Connection conn, Object VO, String tabela,
        String where, String[] propriedadesExcluidas) throws Exception {
        Map propriedades = BeanUtils.describe(VO);
        StringBuffer query = new StringBuffer("");

        /**
         * Formatando nomes das propriedades para ingnorar case.
         */
        List remover = new ArrayList();

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propName = (String) iter.next();

            if (propriedadesExcluidas != null) {
                for (int i = 0; i < propriedadesExcluidas.length; i++) {
                    if (propName.equalsIgnoreCase(propriedadesExcluidas[i])) {
                        remover.add(propName);
                    }
                }
            }
        }

        propriedades.keySet().removeAll(remover);

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                if ((query.toString() != null) &&
                        (query.toString().length() > 0)) {
                    query.append(",");
                }

                query.append(propriedadeNome);
            }
        }

        query.append(" FROM " + tabela);

        if ((where != null) && !"".equals(where)) {
            query.append(" WHERE " + where);
        }

        String select = "SELECT " + query.toString();

        PreparedStatement statement = conn.prepareStatement(select);

        /**
         * Usado para evitar que o banco feche a coneco automaticamente e
         * apenas ignore os registros a mais.
         */
        statement.setMaxRows(NUM_MAX_REGS_PESQUISA + 1);
        Debuger.debugar(statement, this.getClass());

        ResultSet resultSet = statement.executeQuery();
        List retorno = preencherResultSet(resultSet, propriedades, VO, 0);
        resultSet.close();

        return retorno;
    }

    /**
     * Preeche uma lista de de vo com o conteudo de um result set
     * @param resultSet
     * @param propriedades
     * @param VO
     * @return
     * @throws Exception
     */
    protected List preencherResultSet(ResultSet resultSet, Map propriedades,
        Object VO, int qtdeMax) throws Exception {
        List resultado = new ArrayList();

        /**
         * Contador de linha para limitar a quantidade de registros
         * vindos do banco.
         */
        int rowConut = 0;

        if (qtdeMax <= 0) {
            qtdeMax = NUM_MAX_REGS_PESQUISA;
        }

        while (resultSet.next()) {
            rowConut++;

            if (rowConut > qtdeMax) {
                resultSet.close();
                throw new Exception("Restrinja sua consulta. Nmero maximo " +
                    "de registros atingido. " + qtdeMax);
            }

            for (Iterator iter = propriedades.keySet().iterator();
                    iter.hasNext();) {
                String propriedadeNome = (String) iter.next();

                if ((propriedadeNome != null) &&
                        !propriedadeNome.equalsIgnoreCase("class")) {
                    String propriedadeTipo = PropertyUtils.getPropertyType(VO,
                            propriedadeNome).getName();
                    Object valor = getRs(resultSet, propriedadeTipo,
                            propriedadeNome);
                    propriedades.put(propriedadeNome, valor);
                }
            }

            Class voClass = VO.getClass();
            Object voInstace = voClass.newInstance();
            BeanUtils.populate(voInstace, propriedades);
            resultado.add(voInstace);
        }

        return resultado;
    }

    /**
     * So usar em migraes
     * @deprecated - No usar coisa de banco nos controles...
     */
    public void inserirVOGenericoSemIdentity(Object key, Object vo)
        throws Exception {
        Connection connection = validarTransacao(key);
        inserirVO(connection, vo);
    }

    /**
     * So usar em migraes
     * @deprecated - No usar coisa de banco nos controles...
     */
    public void excluirVOGenericoSemIdentity(Object key, Object vo)
        throws Exception {
        Connection connection = validarTransacao(key);
        excluirVO(connection, vo);
    }

    /**
     * @deprecated - No usar coisa de banco nos controles...
     */
    public void inserirVOGenericoIdentity(Object key, Object vo, String pk)
        throws Exception {
        Connection connection = validarTransacao(key);
        inserirVO(connection, vo, new String[] { pk });
    }

    protected void inserirVO(Connection conn, Object VO)
        throws Exception {
        inserirVO(conn, VO, arrayVazio);
    }

    protected void inserirVO(Connection conn, Object VO, String tabela)
        throws Exception {
        inserirVO(conn, VO, tabela, arrayVazio);
    }

    protected void inserirVO(Connection conn, Object VO, String tabela,
        List propriedadesExibiveis) throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) &&
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        inserirVO(conn, VO, tabela,
            Util.converterListDeStringParaArrayDeString(remover));
    }

    /**
     * Insere o contedo do VO no banco, dada uma tabela.
     * @param conn - coneco a se usada
     * @param VO   - VO de amostra
     * @param propriedadesExcluidas - propriedades que no pertecem
     *  a tabela em questo. (Strings)
     * @throws Exception
     */
    protected void inserirVO(Connection conn, Object VO,
        String[] propriedadesExcluidas) throws Exception {
        inserirVO(conn, VO, Util.tabelaVONome(VO), propriedadesExcluidas);
    }

    /**
     * Insere o contedo do VO no banco dada uma tabela.
     * @param conn - coneco a se usada
     * @param VO - VO de amostra
     * @param tabela -  nome da tabela
     * @param propriedadesExcluidas - propriedades que no pertecem
     *  a tabela em questo. (Strings)
     * @throws Exception
     */
    protected void inserirVO(Connection conn, Object VO, String tabela,
        String[] propriedadesExcluidas) throws Exception {
        Map propriedades = BeanUtils.describe(VO);
        BeanUtil.removerPropriedadesExcluidas(propriedades,
            propriedadesExcluidas);

        StringBuffer query = new StringBuffer("INSERT INTO ");
        query.append(tabela + " (");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String qTmp = query.toString();

                if ((qTmp != null) && (qTmp.charAt(qTmp.length() - 1) != '(')) {
                    query.append(",");
                }

                query.append(propriedadeNome);
            }
        }

        query.append(") VALUES (");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String qTmp = query.toString();

                if ((qTmp != null) && (qTmp.charAt(qTmp.length() - 1) != '(')) {
                    query.append(",");
                }

                query.append("?");
            }
        }

        query.append(")");

        String strQuery = query.toString();

        PreparedStatement statement = conn.prepareStatement(strQuery);
        int set = 1;

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String propriedadeTipo = PropertyUtils.getPropertyType(VO,
                        propriedadeNome).getName();
                set = setPs(set, statement, propriedadeTipo,
                        propriedades.get(propriedadeNome), propriedadeNome, VO,
                        DATABASE);
            }
        }

        Debuger.debugar(statement, this.getClass());

        if (gerarSqlsArquivo) {
            try {
                sqls.write(statement.toString());
                sqls.write(";\n");

                return;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        statement.executeUpdate();
    }

    /**
     * Atualiza uma tupla dado um VO e suas chaves
     *  primrias.
     * @param conn - Coneco ativa
     * @param VO   - tupla que sera atualizada
     * @param pks  - nomes das chaves primarias
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int atualizarVO(Connection conn, Object VO, String[] pks)
        throws Exception {
        return atualizarVO(conn, VO, Util.tabelaVONome(VO), pks, arrayVazio);
    }

    /**
     * Atualiza uma tupla dado um VO e suas chaves primrias.
     * @param conn - Coneco ativa.
     * @param VO   - tupla que sera atualizada.
     * @param pks  - nomes das chaves primarias.
     * @param propriedadesExcluidas - propriedades que no devem ser alteradas.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int atualizarVO(Connection conn, Object VO, String[] pks,
        String[] propriedadesExcluidas) throws Exception {
        return atualizarVO(conn, VO, Util.tabelaVONome(VO), pks,
            propriedadesExcluidas);
    }

    /**
     * Atualiza uma tupla dado um VO e suas chaves primrias.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     *
     * @param conn   - Coneco ativa.
     * @param VO     - tupla que sera atualizada.
     * @param tabela - nome da tabela.
     * @param pks - nomes das chaves primarias.
     * @param propriedadesExibiveis - propiedades que devem ser alteradas.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int atualizarVO(Connection conn, Object VO, String tabela,
        String[] pks, List propriedadesExibiveis) throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) ||
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        return atualizarVO(conn, VO, tabela, pks,
            Util.converterListDeStringParaArrayDeString(remover));
    }

    /**
     * Atualiza uma tupla dado um VO e suas chaves primrias.
     *
     * Cuidado ao usar Arrays.asList(new String[]{"valores"});  nas
     * propriedade exibiveis onde o valores devem ser casedown
     *
     * @param conn   - Coneco ativa.
     * @param VO     - tupla que sera atualizada (Classe VO com mesmo nome tabela).
     * @param pks - nomes das chaves primarias.
     * @param propriedadesExibiveis - propiedades que devem ser alteradas.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int atualizarVO(Connection conn, Object VO, String[] pks,
        List propriedadesExibiveis) throws Exception {
        return atualizarVO(conn, VO, Util.tabelaVONome(VO), pks,
            propriedadesExibiveis);
    }

    /**
     * Atualiza uma tupla dado um VO e suas chaves primrias.
     * @param conn   - Coneco ativa.
     * @param VO     - tupla que sera atualizada.
     * @param tabela - nome da tabela.
     * @param pks - nomes das chaves primarias.
     * @param propriedadesExcluidas - propiedades que no devem ser alteradas.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int atualizarVO(Connection conn, Object VO, String tabela,
        String[] pks, String[] propriedadesExcluidas) throws Exception {
        Map propriedades = BeanUtils.describe(VO);
        Set pksSet = new HashSet();
        Set pksExcluidas = new HashSet();

        for (int i = 0; i < pks.length; i++) {
            String pkNome = pks[i].toLowerCase();
            pksSet.add(pkNome);

            if (propriedadesExcluidas != null) {
                for (int j = 0; j < propriedadesExcluidas.length; j++) {
                    if (pkNome.equalsIgnoreCase(propriedadesExcluidas[j])) {
                        pksExcluidas.add(pkNome);
                    }
                }
            }
        }

        List propriedadesExcluidasSemPKs = new ArrayList();

        for (int i = 0; i < propriedadesExcluidas.length; i++) {
            if (!pksExcluidas.contains(propriedadesExcluidas[i])) {
                propriedadesExcluidasSemPKs.add(propriedadesExcluidas[i]);
            }
        }

        BeanUtil.removerPropriedadesExcluidas(propriedades,
            Util.converterListDeStringParaArrayDeString(
                propriedadesExcluidasSemPKs));

        StringBuffer query = new StringBuffer("UPDATE " + tabela);
        Iterator names = propriedades.keySet().iterator();
        StringBuffer set_clause = new StringBuffer("");

        while (names.hasNext()) {
            String name = (String) names.next();

            if (name == null) {
                continue;
            }

            if (!name.equalsIgnoreCase("class") && !pksSet.contains(name)) {
                Object property_value = propriedades.get(name);

                if ((property_value != null) &&
                        (property_value.toString().length() > 0)) {
                    if ((set_clause.toString() == null) ||
                            (set_clause.toString().length() == 0)) {
                        set_clause.append(" SET ");
                    } else {
                        set_clause.append(",");
                    }

                    set_clause.append(name + " = ?");
                }
            }
        }

        query.append(set_clause.toString());

        StringBuffer where_clause = new StringBuffer(" WHERE ");

        for (int i = 0; i < pks.length; i++) {
            if (i != 0) {
                where_clause.append(" AND ");
            }

            where_clause.append(pks[i] + " = ? ");
        }

        query.append(where_clause.toString() + " ");

        String strQuery = query.toString();

        PreparedStatement ps = conn.prepareStatement(strQuery);
        int set = 1;
        names = propriedades.keySet().iterator();

        /**
         * Setando os valores a serem atualizados.
         */
        while (names.hasNext()) {
            String name = (String) names.next();

            if (name == null) {
                continue;
            }

            if (!name.equalsIgnoreCase("class") && !pksSet.contains(name)) {
                Object property_value = propriedades.get(name);

                if ((property_value != null) &&
                        (property_value.toString().length() > 0)) {
                    Class property_type = PropertyUtils.getPropertyType(VO, name);
                    String datatype = property_type.getName();
                    set = setPs(set, ps, datatype, property_value, name, VO,
                            DATABASE);
                }
            }
        }

        /**
         * Setando os valores das pks
         */
        for (int i = 0; i < pks.length; i++) {
            names = propriedades.keySet().iterator();

            while (names.hasNext()) {
                String name = (String) names.next();

                if ((name != null) && !name.equalsIgnoreCase("class")) {
                    if (pks[i].equalsIgnoreCase(name)) {
                        Object property_value = propriedades.get(name);
                        Debuger.debugar("Clusula Update PK :" + name +
                            " Valor :" + property_value, this.getClass());

                        Class property_type = PropertyUtils.getPropertyType(VO,
                                name);
                        String datatype = property_type.getName();
                        set = setPs(set, ps, datatype, property_value, name,
                                VO, DATABASE);
                    }
                }
            }
        }

        Debuger.debugar(ps, this.getClass());

        if (gerarSqlsArquivo) {
            try {
                sqls.write(ps.toString());
                sqls.write(";\n");

                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        int linhaAfetadas = ps.executeUpdate();
        Debuger.debugar("Linhas Atualizadas :" + linhaAfetadas, this.getClass());

        return linhaAfetadas;
    }

    /**
     * Apaga uma tupla dado um VO e suas chaves primrias.
     * @param conn   - Coneco ativa.
     * @param VO     - tupla que sera atualizada.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int excluirVO(Connection conn, Object VO)
        throws Exception {
        return excluirVO(conn, VO, Util.tabelaVONome(VO));
    }

    /**
     * Apaga uma tupla dado um VO e suas chaves primrias.
     * @param conn   - Coneco ativa.
     * @param VO     - tupla que sera atualizada.
     * @param tabela - nome da tabela.
     * @return quantidade de tuplas alteradas
     * @throws Exception
     */
    protected int excluirVO(Connection conn, Object vo, String tabela)
        throws Exception {
        Map propriedades = BeanUtils.describe(vo);
        StringBuffer query = new StringBuffer("DELETE FROM " + tabela);
        StringBuffer set_clause = new StringBuffer("");

        query.append(set_clause.toString());

        StringBuffer where_clause = new StringBuffer(" WHERE 1=1 ");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class") &&
                    (propriedades.get(propriedadeNome) != null) &&
                    primitivoNULL(propriedadeNome, propriedades, vo)) {
                where_clause.append(" and " + propriedadeNome + " = ?");
            }
        }

        query.append(where_clause.toString() + " ");

        String strQuery = query.toString();

        PreparedStatement ps = conn.prepareStatement(strQuery);
        int set = 1;

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class") &&
                    (propriedades.get(propriedadeNome) != null) &&
                    primitivoNULL(propriedadeNome, propriedades, vo)) {
                String propriedadeTipo = PropertyUtils.getPropertyType(vo,
                        propriedadeNome).getName();
                set = setPs(set, ps, propriedadeTipo,
                        propriedades.get(propriedadeNome), propriedadeNome, vo,
                        DATABASE);
            }
        }

        Debuger.debugar(ps, this.getClass());

        if (gerarSqlsArquivo) {
            try {
                sqls.write(ps.toString());
                sqls.write("\n");

                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        int linhaAfetadas = ps.executeUpdate();
        Debuger.debugar("Linhas Atualizadas :" + linhaAfetadas, this.getClass());

        return linhaAfetadas;
    }

    /**
     * Descobre qual o tipo do parmetro
     * @param set - nmero da ?
     * @param ps  - statement
     * @param datatype -  tipo do dado
     * @param property_value - valor do dado
     * @param name - nome do campo
     * @param TABLE - VO
     * @param DATABASE - tipo de DB
     * @return
     * @throws Exception
     */
    protected static int setPs(int set, PreparedStatement ps, String datatype,
        Object property_value, String name, Object TABLE, final byte DATABASE)
        throws Exception {
        if (datatype.equals("java.lang.String") || datatype.equals("String")) {
            ps.setString(set, (String) property_value);
            set++;
        } else if (datatype.equals("java.sql.Date")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setDate(set, (java.sql.Date) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("java.util.Date")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            java.util.Date d = (java.util.Date) method.invoke(TABLE,
                    new Object[0]);
            ps.setTimestamp(set, new java.sql.Timestamp(d.getTime()));
            set++;
        } else if (datatype.equals("java.sql.Timestamp")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setTimestamp(set,
                (java.sql.Timestamp) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("java.sql.Time")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setTime(set, (java.sql.Time) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("byte")) {
            ps.setByte(set, (new Byte((String) property_value)).byteValue());
            set++;
        } else if (datatype.equals("short")) {
            ps.setShort(set, (new Short((String) property_value)).shortValue());
            set++;
        } else if (datatype.equals("int")) {
            ps.setInt(set, (new Integer((String) property_value)).intValue());
            set++;
        } else if (datatype.equals("long")) {
            ps.setLong(set, (new Long((String) property_value)).longValue());
            set++;
        } else if (datatype.equals("double")) {
            ps.setDouble(set,
                (new Double((String) property_value)).doubleValue());
            set++;
        } else if (datatype.equals("boolean")) {
            if ((DATABASE == ORACLE) || (DATABASE == DB2)) {
                String flag = ((new Boolean((String) property_value)).booleanValue())
                    ? "1" : "0";
                ps.setString(set, flag);
            } else {
                ps.setBoolean(set,
                    ((new Boolean((String) property_value)).booleanValue()));
            }

            set++;
        } else if (datatype.equals("java.sql.Array")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setArray(set,
                (java.sql.Array) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("java.sql.Blob")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setBlob(set, (java.sql.Blob) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("java.sql.Clob")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setClob(set, (java.sql.Clob) method.invoke(TABLE, new Object[0]));
            set++;
        } else if (datatype.equals("java.lang.Object")) {
            Class object_class = TABLE.getClass();
            java.lang.reflect.Method method = object_class.getMethod("get" +
                    name.substring(0, 1).toUpperCase() + name.substring(1),
                    new Class[0]);
            ps.setObject(set,
                (java.lang.Object) method.invoke(TABLE, new Object[0]));
            set++;
        } else {
            ps.setObject(set, property_value);
            set++;
        }

        return set;
    }

    /**
     * Descobre pelo tipo o parmetro a obter do ResultSet.
     * @param rs
     * @param datatype
     * @param name
     * @return
     * @throws Exception
     */
    protected static Object getRs(ResultSet rs, String datatype, String name)
        throws Exception {
        if (datatype.equals("java.lang.String") || datatype.equals("String")) {
            return rs.getString(name);
        } else if (datatype.equals("java.sql.Date")) {
            return rs.getDate(name);
        } else if (datatype.equals("java.sql.Timestamp")) {
            return rs.getTimestamp(name);
        } else if (datatype.equals("java.sql.Time")) {
            return rs.getTime(name);
        } else if (datatype.equals("double")) {
            return new Double(rs.getDouble(name));
        } else if (datatype.equals("int")) {
            return new Integer(rs.getInt(name));
        } else if (datatype.equals("long")) {
            return new Long(rs.getLong(name));
        } else if (datatype.equals("java.lang.Double")) {
            return new Double(rs.getDouble(name));
        } else if (datatype.equals("java.lang.Integer")) {
            return new Integer(rs.getInt(name));
        } else if (datatype.equals("java.lang.Long")) {
            return new Long(rs.getLong(name));
        } else if (datatype.equals("java.lang.Float")) {
            return new Float(rs.getFloat(name));
        } else if (datatype.equals("java.lang.Short")) {
            return new Short(rs.getShort(name));
        } else if (datatype.equals("java.lang.Byte")) {
            return new Byte(rs.getByte(name));
        } else if (datatype.equals("java.math.BigDecimal")) {
            return rs.getBigDecimal(name);
        } else if (datatype.equals("java.io.InputStream")) {
            return rs.getAsciiStream(name);
        } else if (datatype.equals("boolean")) {
            if ((DATABASE == ORACLE) || (DATABASE == DB2)) {
                String flag = rs.getString(name);
                boolean boolean_flag = ((flag != null) &&
                    (flag.equalsIgnoreCase("1") ||
                    flag.equalsIgnoreCase("true") ||
                    flag.equalsIgnoreCase("on"))) ? true : false;

                return new Boolean(boolean_flag);
            } else {
                return new Boolean(rs.getBoolean(name));
            }
        } else if (datatype.equals("java.sql.Array")) {
            return rs.getArray(name);
        } else if (datatype.equals("java.sql.Blob")) {
            return rs.getBlob(name);
        } else if (datatype.equals("java.sql.Clob")) {
            return rs.getClob(name);
        } else {
            return rs.getObject(name);
        }
    }

    protected static void setInteger(int index, PreparedStatement ps, Integer i)
        throws SQLException {
        if (i == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, i.intValue());
        }
    }

    protected static void setInt(int index, PreparedStatement ps, int i)
        throws SQLException {
        ps.setInt(index, i);
    }

    protected static void setLong(int index, PreparedStatement ps, Long i)
        throws SQLException {
        if (i == null) {
            ps.setNull(index, Types.BIGINT);
        } else {
            ps.setLong(index, i.longValue());
        }
    }

    protected static void setInteger(int index, CallableStatement cstmt,
        Integer i) throws SQLException {
        if (i == null) {
            cstmt.setNull(index, Types.INTEGER);
        } else {
            cstmt.setInt(index, i.intValue());
        }
    }

    protected static void setDate(int index, CallableStatement cstmt,
        java.util.Date date) throws SQLException {
        if (date == null) {
            cstmt.setNull(index, Types.DATE);
        } else {
            cstmt.setDate(index, new java.sql.Date(date.getTime()));
        }
    }

    protected static void setDate(int index, PreparedStatement ps,
        java.util.Date date) throws SQLException {
        if (date == null) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setDate(index, new java.sql.Date(date.getTime()));
        }
    }

    protected static void setString(int index, CallableStatement cstmt,
        String str) throws SQLException {
        if (str == null) {
            cstmt.setNull(index, Types.VARCHAR);
        } else {
            cstmt.setString(index, str);
        }
    }

    protected static void setString(int index, PreparedStatement ps, String str)
        throws SQLException {
        if (str == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, str);
        }
    }

    protected static void setDouble(int index, PreparedStatement ps, double val)
        throws SQLException {
        ps.setDouble(index, val);
    }

    protected static void setDouble(int index, PreparedStatement ps, Double d)
        throws SQLException {
        if (d == null) {
            ps.setNull(index, Types.DOUBLE);
        } else {
            ps.setDouble(index, d.doubleValue());
        }
    }

    protected static void setObject(int index, PreparedStatement ps,
        Object object) throws SQLException {
        if (object == null) {
            ps.setNull(index, Types.JAVA_OBJECT);
        } else {
            ps.setObject(index, object);
        }
    }

    protected static void setBinaryStream(int index, PreparedStatement ps,
        InputStream inputStream) throws SQLException {
        if (inputStream == null) {
            ps.setNull(index, Types.BLOB);
        } else {
            try {
                ps.setBinaryStream(index, inputStream, inputStream.available());
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }
    }

    protected static void setShort(int index, PreparedStatement ps, Short object)
        throws SQLException {
        if (object == null) {
            ps.setNull(index, Types.SMALLINT);
        } else {
            ps.setShort(index, object.shortValue());
        }
    }

    protected static void setTimeStamp(int index, PreparedStatement ps,
        Timestamp timestamp) throws SQLException {
        if (timestamp == null) {
            ps.setNull(index, Types.DATE);
        } else {
            ps.setTimestamp(index, timestamp);
        }
    }

    /**
     * Adiciona as aspas simples ( '' ) a uma String.
     * @param string
     * @return
     */
    protected String aspas(String string) {
        return "'" + string + "'";
    }

    /**
     * Adiciona as aspas simples ( '%%' ) a uma String.
     * @param string
     * @return
     */
    protected String aspasLike(String string) {
        return "'%" + string + "%'";
    }

    /**
     * Se a String for nula ou vazia retorna 0 caso contrrio retorna 1.
     * @param string
     * @return
     */
    protected String null0(String string) {
        return Util.null0(string);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propriedades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - coneco
     * @param vo - VO de amostra
     * @param excluidas -  propriedades que no sero preechidas
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String[] excluidas)
        throws Exception {
        return pesquisarVO(conn, vo, "", "", excluidas, 0);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propriedades
     * a serem exibidas para a consulta.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - coneco
     * @param vo - VO de amostra
     * @param excluidas -  propriedades que no sero preechidas
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object VO,
        List propriedadesExibiveis) throws Exception {
        Collection remover = new ArrayList();

        if ((propriedadesExibiveis != null) &&
                !propriedadesExibiveis.isEmpty()) {
            Map propriedades = BeanUtils.describe(VO);
            remover = CollectionUtils.subtract(propriedades.keySet(),
                    propriedadesExibiveis);
        }

        return pesquisarVO(conn, VO, "", "",
            Util.converterListDeStringParaArrayDeString(remover));
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propriedades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - vo com os campos preenchidos para clausualas where
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo)
        throws Exception {
        return pesquisarVO(conn, vo, "");
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propriedades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * @param conn - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param where - clusula <code>where</code> para uma ou mais propriedades
     * especficas.
     * Propriedades contempladas no where devem ser anuladas no VO para evitar
     * duplicidade.
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where)
        throws Exception {
        return pesquisarVO(conn, vo, where, "", arrayVazio, 0);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propriedades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param connection - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param where - clusula where para uma ou mais propriedades especficas
     * Propriedades contempladas no where devem ser anuladas no VO para evitar
     * duplicidade.
     * @param tabela - tabelas as quais o VO ser contemplado.
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String tabela) throws Exception {
        return pesquisarVO(conn, vo, where, tabela, null, 0);
    }

    /**
     * Preenche uma lista de vo com base numa pesquisa feita usado as propridades
     * diferentes de null ou de 0 nocas de tipos int.
     * No pesquisa valores ou qantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato Completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - vo com os campos preenchidos para clausualas where
     * @param where - clasula where para uma ou mais propriedades especificas
     * propriedades contempladas no where deve ser anula no VO para evitar duplicidade.
     * @param propriedadesExcluidas
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String[] propriedadesExcluidas) throws Exception {
        return pesquisarVO(conn, vo, where, null, propriedadesExcluidas, 0);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usado as propriedades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param where - clusula where para uma ou mais propriedades especficas
     * Propriedades contempladas no where devem ser anuladas no VO para evitar
     * duplicidade.
     * @param tabela - tabelas as quais o VO se contempla.
     * @param propriedadesExcluidas -  propriedades que no sero preechidas
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String tabela, String[] propriedadesExcluidas)
        throws Exception {
        return pesquisarVO(conn, vo, where, tabela, propriedadesExcluidas, 0);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propridades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param tabela - tabelas as quais o VO se contempla.
     * Propriedades contempladas no where deve ser anuladas no VO para evitar
     * duplicidade.
     * @param propriedadesExcluidas -  propriedades que no sero preechidas.
     * @param qdtMaxRetorno - Quantidade mxima de retorno.
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String tabela, String[] propriedadesExcluidas, int qdtMaxRetorno)
        throws Exception {
        return pesquisarVO(conn, vo, where, tabela, propriedadesExcluidas,
            qdtMaxRetorno, false);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propridades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param tabela - tabelas as quais o VO se contempla.
     * Propriedades contempladas no where deve ser anuladas no VO para evitar
     * duplicidade.
     * @param qdtMaxRetorno - Quantidade mxima de retorno.
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String tabela, int qdtMaxRetorno) throws Exception {
        return pesquisarVO(conn, vo, where, tabela, arrayVazio, qdtMaxRetorno);
    }

    /**
     * Preenche uma lista de VO com base numa pesquisa feita usando as propridades
     * diferentes de null ou de 0 no caso de tipos int.
     * No pesquisa valores ou quantidades igual a zero.
     * Pesquisa tipos TimeStamp levando em conta o formato completo:
     * yyyy-MM-dd HH:mm:ss.mmm
     * @param conn - Coneco
     * @param vo - VO com os campos preenchidos para clusulas where
     * @param tabela - tabelas as quais o VO ser contemplado.
     * Propriedades contempladas no where devem ser anuladas no VO para evitar
     * duplicidade.
     * @param propriedadesExcluidas -  propriedades que no sero preechidas.
     * @param qdtMaxRetorno - Quantidade mxima de retorno.
     * @return - lista do VO de amostra
     * @throws Exception
     */
    protected List pesquisarVO(Connection conn, Object vo, String where,
        String tabela, String[] propriedadesExcluidas, int qdtMaxRetorno,
        boolean validaSql) throws Exception {
        Map propriedades = gerarMapaPropriedades(propriedadesExcluidas, vo);

        StringBuffer query = new StringBuffer("SELECT * FROM " +
                ((Util.isNullOrEmpty(tabela)) ? Util.tabelaVONome(vo) : tabela) +
                " WHERE  1=1 ");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class") &&
                    (propriedades.get(propriedadeNome) != null) &&
                    primitivoNULL(propriedadeNome, propriedades, vo)) {
                query.append(" AND " + propriedadeNome + " = ?");
            }
        }

        if (where != null) {
            query.append(" ");
            query.append(where);
            query.append(" ");
        }

        PreparedStatement statement = conn.prepareStatement(query.toString());
        int set = 1;

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class") &&
                    (propriedades.get(propriedadeNome) != null) &&
                    primitivoNULL(propriedadeNome, propriedades, vo)) {
                String propriedadeTipo = PropertyUtils.getPropertyType(vo,
                        propriedadeNome).getName();
                set = setPs(set, statement, propriedadeTipo,
                        propriedades.get(propriedadeNome), propriedadeNome, vo,
                        DATABASE);
            }
        }

        if (validaSql) {
            validarSql(statement, conn, qdtMaxRetorno);
        }

        Debuger.debugar(statement, this.getClass());

        ResultSet resultSet = statement.executeQuery();
        List retorno = preencherResultSet(resultSet, propriedades, vo,
                qdtMaxRetorno);
        resultSet.close();

        return retorno;
    }

    protected Map gerarMapaPropriedades(String[] propriedadesExcluidas,
        Object vo)
        throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Map propriedades = BeanUtils.describe(vo);

        /**
         * Formatando nomes das propriedades para ignorar case.
         */
        List remover = new ArrayList();

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propName = (String) iter.next();

            if (propriedadesExcluidas != null) {
                for (int i = 0; i < propriedadesExcluidas.length; i++) {
                    if (propName.equalsIgnoreCase(propriedadesExcluidas[i])) {
                        remover.add(propName);
                    }
                }
            }
        }

        propriedades.keySet().removeAll(remover);

        return propriedades;
    }

    private void validarSql(PreparedStatement statementQuery, Connection conn,
        int qdeMaxRetorno) throws Exception {
        String query = "";

        //TODO implementar.
        if (true) {
            return;
        }

        //        if (statementQuery instanceof DelegatingPreparedStatement) {
        //            DelegatingPreparedStatement delegatingPreparedStatement =
        //                (DelegatingPreparedStatement) statementQuery;
        //            query = delegatingPreparedStatement.getDelegate().toString();
        //        } else if (statementQuery instanceof org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement) {
        //            org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement delegatingPreparedStatement =
        //                (org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement) statementQuery;
        //            query = delegatingPreparedStatement.getDelegate().toString();
        //        } else {
        //            query = statementQuery.toString();
        //        }
        query = query.replaceAll("\\*", "count(*) as total");

        StringTokenizer tokenizer = new StringTokenizer(query);
        StringBuffer buffer = new StringBuffer();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (token.equalsIgnoreCase("*")) {
                buffer.append("count(*) as total");
            } else if (token.equalsIgnoreCase("order")) {
                break;
            } else {
                buffer.append(token + " ");
            }
        }

        PreparedStatement statement = conn.prepareStatement(buffer.toString());
        Debuger.debugar(statement, getClass());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int numRegs = resultSet.getInt(TOTAL);

            if (qdeMaxRetorno <= 0) {
                qdeMaxRetorno = NUM_MAX_REGS_PESQUISA;
            }

            if (numRegs > qdeMaxRetorno) {
                throw new Exception("Restrinja sua consulta. Nmero mximo " +
                    "de registros atingido. " + qdeMaxRetorno);
            }
        }
    }

    /**
     * Retorna falso caso a prpriedade do tipo primitivo int ou double seja
     * igual a zero
     * @param propriedadeNome
     * @param propriedades
     * @param vo
     * @return
     * @throws Exception
     */
    private boolean primitivoNULL(String propriedadeNome, Map propriedades,
        Object vo) throws Exception {
        String propriedadeTipo = PropertyUtils.getPropertyType(vo,
                propriedadeNome).getName();

        if ("int".equals(propriedadeTipo) &&
                "0".equals(propriedades.get(propriedadeNome))) {
            return false;
        }

        if ("long".equals(propriedadeTipo) &&
                "0".equals(propriedades.get(propriedadeNome))) {
            return false;
        }

        if ("double".equals(propriedadeTipo) &&
                "0.0".equals(propriedades.get(propriedadeNome))) {
            return false;
        }

        if ("java.lang.String".equals(propriedadeTipo) &&
                "".equals(propriedades.get(propriedadeNome))) {
            return false;
        }

        return true;
    }

    public static String gerarInsert(Object VO, Connection conn)
        throws Exception {
        return gerarInsert(VO, conn, null);
    }

    public static String gerarInsert(Object VO, Connection connection,
        String[] propriedadesExcluidas) throws Exception {
        String tabela = Util.tabelaVONome(VO);
        Map propriedades = BeanUtils.describe(VO);
        BeanUtil.removerPropriedadesExcluidas(propriedades,
            propriedadesExcluidas);

        StringBuffer query = new StringBuffer("INSERT INTO ");
        query.append(tabela + " (");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String qTmp = query.toString();

                if ((qTmp != null) && (qTmp.charAt(qTmp.length() - 1) != '(')) {
                    query.append(",");
                }

                query.append(propriedadeNome);
            }
        }

        query.append(") VALUES (");

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String qTmp = query.toString();

                if ((qTmp != null) && (qTmp.charAt(qTmp.length() - 1) != '(')) {
                    query.append(",");
                }

                query.append("?");
            }
        }

        query.append(");");

        String strQuery = query.toString();

        PreparedStatement statement = connection.prepareStatement(strQuery);
        int set = 1;

        for (Iterator iter = propriedades.keySet().iterator(); iter.hasNext();) {
            String propriedadeNome = (String) iter.next();

            if ((propriedadeNome != null) &&
                    !propriedadeNome.equalsIgnoreCase("class")) {
                String propriedadeTipo = PropertyUtils.getPropertyType(VO,
                        propriedadeNome).getName();
                set = setPs(set, statement, propriedadeTipo,
                        propriedades.get(propriedadeNome), propriedadeNome, VO,
                        DATABASE);
            }
        }

        return statement.toString();
    }
}
