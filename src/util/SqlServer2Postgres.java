package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author Sobreira
 * Criado em 18/04/2005
 * Classe utilitaria de gerao de sequences para postgres
 */
public class SqlServer2Postgres {
    public static File carregarArquivo(String texto) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showDialog(null, texto);

        return fileChooser.getSelectedFile();
    }

    public static void main(String[] args) throws Exception {
        File scriptSQL = carregarArquivo("Script Sql Server");
        File scriptPOstgres = carregarArquivo("Script Postgres");

        Map tabelaChave = gerarTabelaChave(scriptSQL);
        JTextArea scriptfinal = new JTextArea(30, 60);
        scriptfinal.append(gererScriptPostgres(scriptPOstgres, tabelaChave));

        JScrollPane pane = new JScrollPane(scriptfinal);
        JOptionPane.showMessageDialog(null, pane);
        System.out.println(tabelaChave);
        System.exit(0);
    }

    /**
     * @param scriptPOstgres
     * @param tabelaChave
     * @return
     * @throws Exception
     */
    private static String gererScriptPostgres(File scriptPOstgres,
        Map tabelaChave) throws Exception {
        InputStreamReader inputStreamReader =
            new InputStreamReader(new FileInputStream(scriptPOstgres));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String linha = reader.readLine();
        StringBuffer script = new StringBuffer();
        boolean ptVirgula = false;
        Set pksCriadas = new HashSet();

        while (linha != null) {
            if ((linha.indexOf("create table") != -1) ||
                    ((linha.indexOf("alter table") != -1) ||
                    (linha.indexOf("create unique") != -1))) {
                if (!ptVirgula) {
                    ptVirgula = true;
                } else {
                    script.append("\n;\n");
                }
            }

            if ((linha.indexOf("create table") != -1) &&
                    (tabelaChave.get(obterToken(linha, 3)) != null)) {
                String pk = (String) tabelaChave.get(obterToken(linha, 3));
                script.append(linha + "\n");
                script.append("	" + pk + " serial ,\n");
                pksCriadas.add(pk);
            } else if (linha.indexOf("DATETIME") != -1) {
                script.append(linha.replaceAll("DATETIME", "TIMESTAMP") + "\n");
            } else if (linha.indexOf("LONGVARCHAR") != -1) {
                script.append(linha.replaceAll("LONGVARCHAR", "TEXT") + "\n");
            } else {
                String lnTemp = obterToken(linha, 1);

                if (!pksCriadas.contains(lnTemp)) {
                    script.append(linha + "\n");
                } else {
                    /**
                     * Se a pk tive algo mais junto na mesma linha
                     * como em :
                     *  MERC_ID INTEGER not null, constraint MERCADORIA_PK primary key (MERC_ID) )
                     *  adiciona o conteudo :
                     *  constraint MERCADORIA_PK primary key (MERC_ID) )
                     */
                    String[] array = linha.split(",");

                    if (array!=null && array.length > 1) {
                        script.append(array[1] + "\n");
                    }
                }
            }

            linha = reader.readLine();
        }

        return script.toString();
    }

    /**
     * Gera um mapa com o nome da tabela e chave primaria.
     * @param scriptSQL
     * @return
     * @throws Exception
     */
    private static Map gerarTabelaChave(File scriptSQL)
        throws Exception {
        InputStreamReader inputStreamReader =
            new InputStreamReader(new FileInputStream(scriptSQL));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        Map map = new HashMap();
        String nomeTabela = "";
        String linha = reader.readLine();

        while (linha != null) {
            if (linha.indexOf("create table") != -1) {
                nomeTabela = obterToken(linha, 3);
            }

            /*
             * verifica se tem a palavra identity na linha
             */
            if (linha.indexOf("identity") != -1) {
                /*
                 * obtem nome da chave primaria
                 */
                String str = obterToken(linha, 1);
                map.put(nomeTabela, str);
            }

            linha = reader.readLine();
        }

        return map;
    }

    /**
     * Ler uma linha e retonar a paralvra passda como parametro.
     * @param str
     * @param pos
     * @return
     * @throws Exception
     */
    private static String obterToken(String str, int pos)
        throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(str);
        int cont = 0;

        while (tokenizer.hasMoreElements()) {
            String token = (String) tokenizer.nextElement();
            cont++;

            if (cont == pos) {
                return token;
            }
        }

        return null;

        //throw new Exception("String no encontrada");
    }
}
