package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;


public class Util {
    public static Locale loc_brasil = new Locale("pt", "BR");
    public static Vector conectivos;
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static final ResourceBundle bundle = ResourceBundle.getBundle(Constantes.CONFIG);
    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_CNPJ = "##.###.###/####-##";

    static {
        conectivos = new Vector();
        conectivos.addElement("DE");
        conectivos.addElement("E");
        conectivos.addElement("DA");
        conectivos.addElement("DO");
        conectivos.addElement("DOS");
        conectivos.addElement("DAS");
    }

    /**
     * Verifica se um campo string  nulo ou vazio lanando um Exception caso
     * verdadeiro.
     * @param campo - campo a analizar
     * @param descricaoCampo - Complemento da messagem de erro
     * @throws Exception
     */
    public static void campoMandatorio(String campo, String descricaoCampo)
        throws Exception {
        if ((campo == null) || "".equals(campo)) {
            throw new Exception("O campo " + descricaoCampo.trim() +
                "  mandatrio");
        }
    }

    /**
     * Retorna true ou false ao receber uma String por parmetro e verificar
     * se a mesma  igual a null ou vazio.
     * @param campo
     * @return
     */
    public static boolean isNullOrEmpty(String campo) {
        return ((campo == null) || "".equals(campo));
    }

    public static Date alteraData(Date date, int qtdDias) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, qtdDias);

        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Verifica se o intervalo de data  vlido
     * @param dataInicial - data inicial
     * @param dataFinal - data final
     * @return
     */
    public static boolean validaIntervaloData(Dia dataInicial, Dia dataFinal) {
        if (dataInicial.maiorQue(dataFinal)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retorna <b>true</b> se o perodo de datas for vlido (data inicial deve
     * ser menor que a final).
     * @param dataInicial - Date
     * @param dataFinal - Date
     * @return
     */
    public static boolean validaIntervaloData(java.sql.Date dataInicial,
        java.sql.Date dataFinal) {
        /**
         * Se a data inicial for aps a data final retonar um nmero maior
         * que 0.
         */
        if (dataInicial.compareTo(dataFinal) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retorna <b>true</b> se o perodo de datas for vlido (data inicial deve
     * ser menor que a final).
     * @param dataInicial - String
     * @param dataFinal - String
     * @throws Exception
     */
    public static void validaIntervaloData(String dataInicial, String dataFinal)
        throws Exception {
        if (!validaIntervaloData(dataInicial, dataFinal, Constantes.DATA_FORMATO)) {
            throw new Exception("Intervalo de datas invalido");
        }
    }

    /**
     * Retorna <b>true</b> se o perodo de datas for vlido (data inicial deve
     * ser menor que a final).
     *
     * @param dataInicial - String
     * @param dataFinal - String
     * @param formato - Formato da data
     * @return
     * @throws Exception
     */
    public static boolean validaIntervaloData(String dataInicial,
        String dataFinal, String formato) throws Exception {
        java.sql.Date dt1 = FormatDate.parseDate(dataInicial, formato);
        java.sql.Date dt2 = FormatDate.parseDate(dataFinal, formato);

        return validaIntervaloData(dt1, dt2);
    }

    /**
     * Retorna <b>true</b> se o perodo de datas for vlido (data inicial deve
     * ser menor que a final).
     *
     * @param data_inicio - Timestamp
     * @param data_fim - Timestamp
     * @return
     * @throws Exception
     */
    public static boolean validaIntervaloData(Timestamp data_inicio,
        Timestamp data_fim) throws Exception {
        String inicio = FormatDate.format(data_inicio);
        String fim = FormatDate.format(data_fim);

        return validaIntervaloData(inicio, fim, Constantes.DATA_FORMATO);
    }

    public static String removeQuotes(String text) {
        String ret = "";

        if (text != null) {
            for (int i = 0; i < text.length(); i++) {
                if (text.substring(i, i + 1).equals("\"")) {
                    ret += "'";
                } else {
                    ret += text.substring(i, i + 1);
                }
            }
        }

        return ret;
    }

    public static String formatNumber(String patern, double number) {
        DecimalFormat form = new DecimalFormat(patern);

        return form.format(number);
    }

    public static String formatNumberReal(String number) {
        return formatNumberReal(Double.parseDouble(number));
    }

    public static String formatNumberReal(double number) {
        return FormatNumber.format(number, " #,##0.00; (#,##0.00)");
    }

    public static double convercaoPorcentagem(String valor)
        throws Exception {
        valor = extrairNumerosPontoVirgula(valor);

        if (valor.indexOf('.') != -1) {
            throw new Exception("Ponto no acito em porcentagens.");
        }

        valor = valor.replace(',', '.');

        return Double.parseDouble(valor);
    }

    public static double convercaoMonetaria(String valor)
        throws Exception {
        if (isNullOrEmpty(valor)) {
            Debuger.debugar("Valor nulo na convero monetria", Util.class);

            return 0;
        }

        valor = extrairNumerosPontoVirgula(valor);

        try {
            return Double.parseDouble(valor);
        } catch (Exception e) {
        }

        StringBuffer retorno = new StringBuffer();

        if (valor.indexOf(",") != -1) {
            String[] v = valor.split(",");
            retorno.append(extrairSomenteNumerosInteiro(v[0]));

            if (!"00".equals(v[1])) {
                retorno.append("." + extrairSomenteNumerosInteiro(v[1]));
            }
        } else {
            retorno.append(extrairSomenteNumerosInteiro(valor));
        }

        if (valor.startsWith("-")) {
            return Double.parseDouble("-" + retorno.toString());
        }

        return Double.parseDouble(retorno.toString());
    }

    /**
     * Recebe um valor como um tipo monetrio 1.000,10 e retorna uma
     * string com o valor 100010
     * @param numero
     * @return
     */
    public static String extrairSomenteNumerosInteiro(String numero) {
        if ((numero == null) || "".equals(numero)) {
            return "0";
        }

        char[] valores = numero.toCharArray();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < valores.length; i++) {
            if (Character.isDigit(valores[i])) {
                buffer.append(valores[i]);
            }
        }

        return buffer.toString();
    }

    /**
     * Recebe um valor como um tipo monetrio R$1.000,10 e retorna uma
     * string com o valor 1.000,10
     * @param numero
     * @return
     */
    public static String extrairNumerosPontoVirgula(String numero) {
        if ((numero == null) || "".equals(numero)) {
            return null;
        }

        char[] valores = numero.toCharArray();
        StringBuffer buffer = new StringBuffer();

        if ((valores.length > 0) && ('-' == valores[0])) {
            buffer.append("-");
        }

        for (int i = 0; i < valores.length; i++) {
            if (Character.isDigit(valores[i])) {
                buffer.append(valores[i]);
            } else if (',' == valores[i]) {
                buffer.append(valores[i]);
            } else if ('.' == valores[i]) {
                buffer.append(valores[i]);
            }
        }

        return buffer.toString();
    }

    public static InputStream getBinaryStream(ResultSet rs, String fieldName)
        throws Exception {
        try {
            Blob blob = rs.getBlob(fieldName);

            return blob.getBinaryStream();
        } catch (Exception e) {
            throw new Exception("getBinaryStream", e);
        }
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                             .substring(1, 3));
        }

        return sb.toString();
    }

    public static String md5(String message)
        throws NoSuchAlgorithmException,UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        return hex(md.digest(message.getBytes("CP1252")));
    }

    public static boolean CPF(String strCpf) {
        if ("000.000.000-00".equals(strCpf)) {
            return false;
        }

        strCpf = extrairSomenteNumerosInteiro(strCpf);

        try {
            int d1;
            int d2;
            int digito1;
            int digito2;
            int resto;
            int digitoCPF;
            String nDigResult;
            d1 = d2 = 0;
            digito1 = digito2 = resto = 0;

            for (int nCount = 1; nCount < (strCpf.length() - 1); nCount++) {
                digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount))
                                   .intValue();

                //multiplique a ltima casa por 2, a seguinte por 3, a seguinte 
                //por 4 e assim por diante.
                d1 = d1 + ((11 - nCount) * digitoCPF);

                //para o segundo digito repita o procedimento incluindo 
                //o primeiro digito calculado no passo anterior.
                d2 = d2 + ((12 - nCount) * digitoCPF);
            }

            ;

            //Primeiro resto da diviso por 11.
            resto = (d1 % 11);

            //Se o resultado for 0 ou 1 o digito  0 caso contrrio o 
            //digito  11 menos o resultado anterior.
            if (resto < 2) {
                digito1 = 0;
            } else {
                digito1 = 11 - resto;
            }

            d2 += (2 * digito1);

            //Segundo resto da diviso por 11.
            resto = (d2 % 11);

            //Se o resultado for 0 ou 1 o digito  0 caso contrrio o
            //digito  11 menos o resultado anterior.
            if (resto < 2) {
                digito2 = 0;
            } else {
                digito2 = 11 - resto;
            }

            //Digito verificador do CPF que est sendo validado.
            String nDigVerific = strCpf.substring(strCpf.length() - 2,
                    strCpf.length());

            //Concatenando o primeiro resto com o segundo.
            nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

            //comparar o digito verificador do cpf com o 
            //primeiro resto + o segundo resto.
            return nDigVerific.equals(nDigResult);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDegugMode() {
        if (bundle.getString("MODODEBUG").equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean CNPJ(String str_cnpj) {
        str_cnpj = extrairSomenteNumerosInteiro(str_cnpj);

        try {
            int soma = 0;

            int dig;
            String cnpj_calc = str_cnpj.substring(0, 12);

            if (str_cnpj.length() != 14) {
                return false;
            }

            char[] chr_cnpj = str_cnpj.toCharArray();

            /* Primeira parte */
            for (int i = 0; i < 4; i++)
                if (((chr_cnpj[i] - 48) >= 0) && ((chr_cnpj[i] - 48) <= 9)) {
                    soma += ((chr_cnpj[i] - 48) * (6 - (i + 1)));
                }

            for (int i = 0; i < 8; i++)
                if (((chr_cnpj[i + 4] - 48) >= 0) &&
                        ((chr_cnpj[i + 4] - 48) <= 9)) {
                    soma += ((chr_cnpj[i + 4] - 48) * (10 - (i + 1)));
                }

            dig = 11 - (soma % 11);
            cnpj_calc += (((dig == 10) || (dig == 11)) ? "0"
                                                       : Integer.toString(dig));

            /* Segunda parte */
            soma = 0;

            for (int i = 0; i < 5; i++)
                if (((chr_cnpj[i] - 48) >= 0) && ((chr_cnpj[i] - 48) <= 9)) {
                    soma += ((chr_cnpj[i] - 48) * (7 - (i + 1)));
                }

            for (int i = 0; i < 8; i++)
                if (((chr_cnpj[i + 5] - 48) >= 0) &&
                        ((chr_cnpj[i + 5] - 48) <= 9)) {
                    soma += ((chr_cnpj[i + 5] - 48) * (10 - (i + 1)));
                }

            dig = 11 - (soma % 11);
            cnpj_calc += (((dig == 10) || (dig == 11)) ? "0"
                                                       : Integer.toString(dig));

            return str_cnpj.equals(cnpj_calc);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este mtodo retorna o nome da tabela atravs do nome do VO.
     * O VO deve ter o mesmo nome da tabela.
     * @param vo
     * @return
     */
    public static String tabelaVONome(Object vo) {
        String packageVo = vo.getClass().getPackage().getName();
        String classVo = vo.getClass().getName();

        return classVo.replaceAll((packageVo + "."), "").trim();
    }


    /**
     * Se a String for nula ou vazia retorna 0 caso contrrio retorna 1.
     * @param valor
     * @return
     */
    public static String null0(Object obj) {
        if (obj instanceof String) {
            String string = (String) obj;

            return String.valueOf((((string == null) || "".equals(string)) ? 0 : 1));
        }

        return String.valueOf((((obj == null)) ? 0 : 1));
    }

    /**
     * Se a String for nula ou vazia retorna 0 caso contrrio retorna o numero.
     * @param valor
     * @return
     */
    public static int zeroOuInt(Object obj) {
        try {
            if (obj instanceof String) {
                String string = (String) obj;

                return (((string == null) || "".equals(string)) ? 0
                                                                : Integer.parseInt(string));
            }

            return (((obj == null)) ? 0 : Integer.parseInt((String) obj));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Se a String for nula ou vazia retorna 0 caso contrrio retorna o numero.
     * @param valor
     * @return
     */
    public static double zeroOuDouble(Object obj) {
        try {
            if (obj instanceof String) {
                String string = (String) obj;

                return (((string == null) || "".equals(string)) ? 0
                                                                : Double.parseDouble(string));
            }

            return (((obj == null)) ? 0 : Double.parseDouble((String) obj));
        } catch (Exception e) {
            return 0;
        }
    }

    public static long zeroOuLong(String obj) {
        if (obj instanceof String) {
            String string = (String) obj;

            return (((string == null) || "".equals(string)) ? 0
                                                            : Long.parseLong(string));
        }

        return (((obj == null)) ? 0 : Long.parseLong((String) obj));
    }

    /**
     * @deprecated Use BeanUtil.copiarVO(origem, destino);
     * @param origem
     * @param destino
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copiarVO(Object origem, Object destino)
        throws IllegalAccessException,InvocationTargetException {
        BeanUtil.copiarVO(origem, destino);
    }

    /**
     * @deprecated  Use  BeanUtil.copiarVOStringTipo(origem, destino);
     *
     * Copia as propriedades de um objeto para o outro, fazendo as converses de
     * tipo necessrias.
     * Todas as propriedades do objeto de origem devem ser do tipo String.
     * As propriedades a serem copiadas do objeto de origem devem ter exatamente
     * o mesmo nome no objeto de destino.
     *
     * @param origem - o bean de origem, todos os seus campos devem ser do tipo
     *                 String
     * @param destino - o bean de destino, as propriedades do bean de origem
     *                  sero copiadas para esse bean, com as devidas converses
     * @return void
     * @throws Exception
     */
    public static void copiarVOStringTipo(Object origem, Object destino)
        throws Exception {
        BeanUtil.copiarVOStringTipo(origem, destino);
    }

    /**
     *
     * Se o valor for igual a 10 usar dd/MM/yyyy caso no, usar
     * yyyy-MM-dd HH:mm:ss.mmm
     */
    public static Timestamp converteStringTimestamp(String valor)
        throws Exception {
        if (valor == null) {
            return null;
        }

        if ((valor.indexOf('/') != -1) && (valor.length() < 10)) {
            DecimalFormat format = new DecimalFormat("00");
            String[] parts = valor.split("/");
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < parts.length; i++) {
                buffer.append(format.format(Long.parseLong(parts[i])));

                if (i < (parts.length - 1)) {
                    buffer.append("/");
                }
            }

            valor = buffer.toString();
        }

        return ((valor.length() == 10)
        ? new java.sql.Timestamp(FormatDate.parseDate(valor).getTime())
        : FormatDate.parseTimestamp(valor, Constantes.DATA_FORMATO_COMPLETO));
    }

    /**
     * @deprecated - Use BeanUtil.copiarVOTipoString(origem, destino);
     *
     * Copia as propriedades de um objeto para o outro, fazendo as converses de
     * tipo necessrias.
     * Todas as propriedades do objeto de destino devem ser do tipo String.
     * As propriedades a serem copiadas do objeto de origem devem ter exatamente
     * o mesmo nome no objeto de destino.
     *
     * @param origem - o bean de origem, propridades sero convertidas para
     *                 String.
     * @param destino - o bean de destino, todos os seus campos devem ser do
     *                 tipo String.
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void copiarVOTipoString(Object origem, Object destino)
        throws IllegalAccessException,InvocationTargetException,
            NoSuchMethodException {
        BeanUtil.copiarVOTipoString(origem, destino);
    }

    /**
     * Substitui o ponto de uma String por uma outra qualquer.
     * @param string
     * @param replacement
     * @return
     */
    public static String substituirPonto(String string, String replacement) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {
            char value = string.charAt(i);

            if (value == '.') {
                buffer.append(replacement);
            } else {
                buffer.append(value);
            }
        }

        return buffer.toString();
    }

    /**
     * Formata parmetros quem vem no formato de String[] para String.
     */
    public static void formataParametros(Map parametros, Map unLockedMap)
        throws IllegalAccessException,InvocationTargetException {
        for (Iterator iter = parametros.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            Object object = parametros.get(key);

            if (object instanceof String[]) {
                String[] array = (String[]) object;

                if (array.length > 0) {
                    unLockedMap.put(key, array[0]);
                }
            }
        }
    }

    /**
     * Retorna a chave formada pela primeiras letras de cada palavra que juntas
     * formam o nome da pessoa concatenando com a data de nascimento, com o se-
     * xo e com as trs primeiras letras do primeiro nome e as trs do ltimo
     * nome.
     * @param nome - nome completo da pessoa (conectivos sero ignorados)
     * @param dtnasc - formato dd/mm/aaaa
     * @param sexo - "M" ou "F"
     * @return
     * @throws Exception
     */
    public static String gerarChavePessoa(String nome, String dtnasc,
        String sexo) throws Exception {
        campoMandatorio(nome, "nome da pessoa");
        campoMandatorio(dtnasc, "data de nascimento");
        campoMandatorio(sexo, "sexo");

        StringBuffer retorno = new StringBuffer();
        StringTokenizer stringTokenizer = new StringTokenizer(nome, " ");
        String primeiroToken = null;
        String ultimoToken = null;

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();

            if (primeiroToken == null) {
                primeiroToken = token;
            }

            ultimoToken = token;

            if (!conectivos.contains(token.toUpperCase())) {
                retorno.append(token.toUpperCase().charAt(0));
            }
        }

        retorno.append(FormatDate.format(FormatDate.parseDate(dtnasc,
                    Constantes.DATA_FORMATO)));
        retorno.append(sexo.toUpperCase());

        while (primeiroToken.length() < 3) {
            primeiroToken += "@";
        }

        while (ultimoToken.length() < 3) {
            ultimoToken += "@";
        }

        retorno.append(primeiroToken.toUpperCase().substring(0, 3));
        retorno.append(ultimoToken.toUpperCase().substring(0, 3));

        return retorno.toString();
    }

    /**
     * Lana uma Exceo caso o campo no seja numrico.
     * @param campo - String a ser verificada
     * @param msg - Messagem de Erro
     * @param nulls - o capo pode vir nulll
     */
    public static void campoNumerio(String campo, String msg, boolean nulls)
        throws Exception {
        try {
            if (!nulls) {
                int dumie = Integer.parseInt(campo);
            }
        } catch (Exception e) {
            throw new Exception(msg);
        }
    }

    /**
     * Converte uma lista dinmica em um array de String.
     * @param item
     * @return retorno
     */
    public static String[] converterListDeStringParaArrayDeString(
        Collection item) {
        String[] retorno = new String[item.size()];
        int i = 0;

        for (Iterator iter = item.iterator(); iter.hasNext();) {
            String string = (String) iter.next();
            retorno[i++] = string;
        }

        return retorno;
    }

    public static Timestamp stringtoTimestamp(String timeStamp)
        throws Exception {
        if ((timeStamp != null) && !"".equals(timeStamp)) {
            /**
             * Milsimos de segundos truncados
             * 0000-00-00 00:00:00
             */
            if (timeStamp.length() == 19) {
                timeStamp += ".000";
            }

            if (timeStamp.indexOf('/') != -1) {
                return new Timestamp(FormatDate.parse(timeStamp,
                        Constantes.DATA_FORMATO).getTime());
            } else {
                return new Timestamp(FormatDate.parse(timeStamp,
                        Constantes.DATA_FORMATO_COMPLETO).getTime());
            }
        } else {
            return null;
        }
    }

    /**
     * Retorna o primeiro elemento de uma lista caso ela na estaja vazia
     * @param list
     * @return
     */
    public static Object get0(List list) {
        if ((list != null) && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    /**
     * Retona o valor Integer caso diferente de zero.
     */
    public static Integer integerOrNull(int num) {
        if (num != 0) {
            return new Integer(num);
        }

        return null;
    }

    /**
     * Retona o valor Double caso diferente de zero.
     */
    public static Double doubleOrNull(double num) {
        if (num != 0) {
            return new Double(num);
        }

        return null;
    }

    public static double doubleOr0(Double num) {
        if (num == null) {
            return 0;
        }

        return double2Decimal(num.doubleValue());
    }

    /**
     * @param qtEntrada
     * @return
     */
    public static int intOr0(Integer i) {
        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    public static boolean isNumero(String planoConta) {
        try {
            Integer.parseInt(planoConta);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(md5("sobreira"));
    }

    public static double double2Decimal(double dob) {
        String doubleValue = String.valueOf(dob);

        if (doubleValue.indexOf("E-") != -1) {
            return 0;
        }

        return Double.parseDouble(decimalFormat.format(dob).replace(',', '.'));
    }

    public static void serializarEmArquivo(Object o) {
        String filename = o.getClass().getName();
        File file = new File(filename);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(o);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String mascarar(String valor, String mascara)
        throws ParseException {
        MaskFormatter formatter = new MaskFormatter(mascara);
        JFormattedTextField textField = new JFormattedTextField();
        formatter.install(textField);
        textField.setText(valor);

        return textField.getText();
    }

    public static String mascararCpf(String cpf) throws ParseException {
        return mascarar(cpf, MASCARA_CPF);
    }

    public static String mascararCnpj(String cnpj) throws ParseException {
        return mascarar(cnpj, MASCARA_CNPJ);
    }

    public static String extrairSomenteNumerosInteiro(double d) {
        return extrairSomenteNumerosInteiro(String.valueOf(d));
    }
}
