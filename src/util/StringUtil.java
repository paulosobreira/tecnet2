package util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class StringUtil {
    /**
     * Mtodo para testar de uma String  formada apenas por dgitos
     *
     * @param valor
     * @return
     */
    public static boolean isNumber(String valor) {
        char[] caracteres = valor.toCharArray();

        for (int i = 0; i < caracteres.length; i++) {
            if (!Character.isDigit(caracteres[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Metodo estatico para Tratamento de String
     *
     * @param tag
     *            String
     * @return String
     */
    public static String encodeHtmlTag(String tag) {
        if (tag == null) {
            return "";
        }

        int length = tag.length();
        StringBuffer encodedTag = new StringBuffer(2 * length);

        for (int i = 0; i < length; i++) {
            char c = tag.charAt(i);

            if (c == '<') {
                encodedTag.append("&lt;");
            } else if (c == '>') {
                encodedTag.append("&gt;");
            } else if (c == '&') {
                encodedTag.append("&amp;");
            } else if (c == '"') {
                encodedTag.append("&quot;");
            } else if (c == ' ') {
                encodedTag.append("&nbsp;");
            } else if (c == 'ç') {
                encodedTag.append("&ccedil;");
            } else {
                encodedTag.append(c);
            }
        }

        return encodedTag.toString();
    }

    /**
     * Metodo estatico para recuperar html Armazenado no banco
     *
     * @param entrace
     *            String
     * @return String
     */
    public static String rescueHtmlStringFromDataBase(String entrace) {
        if ((entrace == null) || entrace.equals("")) {
            return "";
        }

        StringBuffer retorno = new StringBuffer();

        for (int i = 0; i < entrace.length(); i++) {
            if (!((entrace.charAt(i) == '\r') || (entrace.charAt(i) == '\n') ||
                    ((int) entrace.charAt(i) == 10) ||
                    ((int) entrace.charAt(i) == 13))) {
                if (entrace.charAt(i) == '"') {
                    retorno.append("&#34;");
                } else {
                    retorno.append(entrace.charAt(i));
                }
            }
        }

        return retorno.toString();
    }

    /**
     * Metodo estatico para formatar o caminho do Diretorio
     *
     * @param entrace
     *            String
     * @return String
     */
    public static String formatDir(String entrace) {
        if ((entrace == null) || entrace.equals("")) {
            return "";
        }

        StringBuffer retorno = new StringBuffer();

        for (int i = 0; i < entrace.length(); i++) {
            if (entrace.charAt(i) == '\\') {
                retorno.append(File.separator + File.separator);
            } else {
                retorno.append(entrace.charAt(i));
            }
        }

        return retorno.toString();
    }

    /**
     * Metodo estatico para formatar o caminho do Diretorio HTTP
     *
     * @param entrace
     *            String
     * @return String
     */
    public static String formatDirHttp(String entrace) {
        if ((entrace == null) || entrace.equals("")) {
            return "";
        }

        StringBuffer retorno = new StringBuffer();

        for (int i = 0; i < entrace.length(); i++) {
            if (entrace.charAt(i) == '\\') {
                retorno.append("/");
            } else {
                retorno.append(entrace.charAt(i));
            }
        }

        return retorno.toString();
    }

    /**
     * Metodo estatico para recuperar valores dos Elementos Tag Genericos
     *
     * @param param
     *            String
     * @param entrace
     *            String
     * @return String
     */
    public static String findParam(String param, String entrace) {
        String retorno = "";

        if (entrace == null) {
            return retorno;
        }

        StringTokenizer token = new StringTokenizer(entrace, "&");

        while (token.hasMoreTokens()) {
            retorno = token.nextToken();

            if (retorno.indexOf(param) > -1) {
                retorno =
                    retorno.substring(retorno.indexOf("=") + 1, retorno.length());

                break;
            }
        }

        return retorno;
    }

    /**
     * Metodo estatico para formatar Data
     *
     * @param date
     *            String
     * @param pattern
     *            String
     * @return java.util.Date
     * @throws ParseException
     */
    public static java.util.Date formatStringToDate(String date, String pattern)
        throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    /**
     * Metodo para formatar a String colocando a Primeira letra Maiuscula
     *
     * @param entrace
     *            String
     * @return String
     */
    public static String formatFirstUpper(String entrace) {
        String result = "";
        String finalResult = "";
        StringTokenizer st = new StringTokenizer(entrace);
        int tokenCount = st.countTokens();

        for (int i = 0; st.hasMoreTokens(); i++) {
            result = st.nextToken();

            finalResult += result.substring(0, 1).toString().toUpperCase()
                                 .concat(result.substring(1).toLowerCase());

            if (i < (tokenCount - 1)) {
                finalResult += " ";
            }
        }

        return finalResult;
    }

    /**
     * metodo para quebra as String deixando as Strings no tamanho desejado
     *
     * @param entrace
     *            String
     * @param maxLength
     *            int
     * @return String
     */
    public static String brokeString(String entrace, int maxLength) {
        String result = "";
        String tokenstr = "";
        String test = "";
        entrace =
            entrace.substring(0, 1).toUpperCase().concat(entrace.substring(1)
                                                                .toLowerCase());

        if (entrace.length() > maxLength) {
            StringTokenizer token = new StringTokenizer(entrace);
            int countToken = token.countTokens();

            for (int i = 0; token.hasMoreTokens(); i++) {
                tokenstr = token.nextToken();

                test += tokenstr.substring(0, 1).toUpperCase().concat(tokenstr.substring(
                        1).toLowerCase());
                ;

                if (test.length() < maxLength) {
                    result += tokenstr.substring(0, 1).toUpperCase().concat(tokenstr.substring(
                            1).toLowerCase());
                }

                if (i < (countToken - 1)) {
                    result += " ";
                }
            }

            if (result.trim().equals("")) {
                result += entrace.substring(0, maxLength);
            }
        } else {
            StringTokenizer token = new StringTokenizer(entrace);
            int countToken = token.countTokens();

            for (int i = 0; token.hasMoreTokens(); i++) {
                tokenstr = token.nextToken();

                result += tokenstr.substring(0, 1).toUpperCase().concat(tokenstr.substring(
                        1).toLowerCase());
                ;

                if (i < (countToken - 1)) {
                    result += " ";
                }
            }
        }

        return result;
    }

    /**
     * metodo para substituir uma substring em um string
     *
     * @param original
     *            String original
     * @param matcher
     *            String procurada
     * @param replaceBy
     *            String a ser colocada
     * @return String
     */
    public static String replaceFirst(String original, String matcher,
        String replaceBy) {
        StringBuffer exit = new StringBuffer(original);
        int start = exit.indexOf(matcher);

        if (start == -1) {
            return original;
        } else {
            exit.replace(start, start + matcher.length(), replaceBy);
        }

        return exit.toString();
    }

    /**
     * Mtodo para substituir todas as substrings em um string por uma outra
     *
     * @param original
     *            String original
     * @param matcher
     *            String procurada
     * @param replaceBy
     *            String a ser colocada
     * @return String
     */
    public static String replaceAll(String original, String matcher,
        String replaceBy) {
        String exit = original;
        int start = original.indexOf(matcher);

        if (start == -1) {
            return original;
        } else {
            while (start != -1) {
                exit = replaceFirst(exit, matcher, replaceBy);
                start = exit.indexOf(matcher);
            }
        }

        return exit;
    }

    public static String brokeHTML(String html, int maxLength) {
        String exit = "";
        StringTokenizer st = new StringTokenizer(html, "<");
        String strFinal = "";

        while (st.hasMoreTokens()) {
            String line = st.nextToken();

            if (line.indexOf(">") > -1) {
                StringTokenizer stdMenor = new StringTokenizer("<" + line, ">");

                while (stdMenor.hasMoreTokens()) {
                    line = stdMenor.nextToken();

                    if (line.indexOf("<") > -1) {
                        strFinal += (line + ">");
                    } else {
                        if (line.length() >= maxLength) {
                            strFinal += line.substring(0, maxLength);
                        } else {
                            strFinal += line.substring(0, line.length());
                        }
                    }
                }
            }
        }

        return strFinal;
    }

    public static String removeHtmlTags(String html, int maxLength) {
        String exit = "";
        String strFinal = "";

        if ((html.indexOf("<") > -1) && (html.indexOf(">") > -1)) {
            strFinal =
                StringUtil.replaceAll(html,
                    html.substring(html.indexOf("<"), html.indexOf(">") + 1), "");

            while ((strFinal.indexOf("<") > -1) &&
                    (strFinal.indexOf(">") > -1)) {
                strFinal =
                    StringUtil.replaceAll(strFinal,
                        strFinal.substring(strFinal.indexOf("<"),
                            strFinal.indexOf(">") + 1), "");
            }
        } else {
            strFinal = html;
        }

        if (strFinal.length() > maxLength) {
            strFinal = strFinal.substring(0, maxLength);
        }

        return strFinal;
    }

    public static String formatNameFirstUpper(String attribute) {
        return attribute.substring(0, 1).toUpperCase() +
        attribute.substring(1).toLowerCase();
    }

    public static String formatNameFirstLower(String attribute) {
        return attribute.substring(0, 1).toLowerCase() +
        attribute.substring(1);
    }

    public static String formatNameColumn(String entrada, String delim,
        String substDelim) {
        String saida = "";
        StringTokenizer token = new StringTokenizer(entrada, delim);
        int count = token.countTokens();

        while (token.hasMoreTokens()) {
            saida += (substDelim + formatNameFirstUpper(token.nextToken()));
        }

        if (count == 0) {
            saida = entrada;
        }

        return saida;
    }

    public static String formatFirstLetterUpperFromWordSeparetedBy(
        String entrada, String separetedBy) {
        String saida = "";
        StringTokenizer token = new StringTokenizer(entrada, separetedBy);
        int count = token.countTokens();

        for (int i = 0; token.hasMoreTokens(); i++) {
            saida += formatNameFirstUpper(token.nextToken());
        }

        if (count == 0) {
            saida = entrada;
        }

        return saida;
    }

    public static String doFilter(int filter, String param) {
        if (param == null) {
            param = "";
        } else {
            param = param.trim();
        }

        switch (filter) {
        case Constantes.EQUALS:return param;

        case Constantes.CONTAINS:return "%" + param + "%";

        case Constantes.START_WITH:return param + "%";

        case Constantes.END_WITH:return "%" + param;

        default:return "%" + param + "%";
        }
    }

    public static String replaceQuotes(String str) {
        if (str == null) {
            return str;
        }

        return str.replaceAll("'", "''");
    }

    /**
     * Replaces some of the most common special characteres by their scape
     * sequencies to avoid problems with the request URL.
     *
     * @param url The url to be Encoded
     * @return The encoded URL
     */
    public static String escape(String url) {
        String encodedUrl = null;

        try {
            encodedUrl = URLEncoder.encode(url, Constantes.ENCODER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // %
        encodedUrl = encodedUrl.replaceAll("\u0025", "%25");

        return encodedUrl;
    }

    /**
     * This method coverts string using the wildcard pattern matching
     * characters as literal characters. To use a wildcard character
     * as a literal character, the method enclose the wildcard character
     * in brackets.
     * @param  the string s to be converted
     * @return the string converted
     */
    public static String sqlWildcardCharactersAsLiterals(String s) {
        if (s == null) {
            return s;
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                buffer.append("[[]");
            } else {
                buffer.append(s.charAt(i));
            }
        }

        s = buffer.toString();
        s = s.replaceAll("%", "[%]");
        s = s.replaceAll("_", "[_]");
        s = s.replaceAll("-", "[-]");

        return s;
    }

    /**
     * It makes the validation of a String, checking if its characters
     * are between the following intervals: 0 - 9, a - z, A - Z, # e -.
     *
     * @param  the string s to be validation
     * @return boolean
     */
    public static boolean validateString(String s) {
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);

            if (!((c >= 65) && (c <= 90)) && !((c >= 97) && (c <= 122)) &&
                    !((c >= 48) && (c <= 57)) && !(c == 35) && !(c == 45)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method returns a string with comma separated values with quotaions<br>
     * marks.
     *
     *
     * @param List of values (String).
     * @return String with comma separated values with quotaions<br>
     * marks.
     */
    public static String getCSVWithQuotationsMarks(List list) {
        StringBuffer buffer = new StringBuffer();
        Iterator values = list.iterator();

        while (values.hasNext()) {
            String value = (String) values.next();

            if (buffer.length() > 0) {
                buffer.append(", ");
            }

            buffer.append("'").append(value).append("'");
        }

        return buffer.toString();
    }

    /**
     * This method sorts the specified <b>list</b> of objects ignoring the case.
     *
     *
     * @param List of objects.
     */
    public static void sort(List list) {
        Collections.sort(list, new StringUtil().new IgnoreCaseComparator());
    }

    /**

    * This method returns a string without comma
    * separated values with quotaions<br>
    * marks.
    * @param List of values (String).
    * @return String with comma separated values without quotaions<br>
    * marks.
    */
    public static String getCSVWithoutQuotationsMarks(List list) {
        String exit = "";

        if (list != null) {
            exit = list.toString();

            if (exit.length() > -1) {
                exit = exit.substring(1, exit.length() - 1);
            }
        }

        return exit;
    }

    /**
     * This method returns a string without comma
     * separated values with quotaions<br>
     * marks.
     * @param String array of values (String).
     * @return String with comma separated values without quotaions<br>
     * marks.
     */
    public static String getCSVWithoutQuotationsMarks(String[] list) {
        return getCSVWithoutQuotationsMarks(Arrays.asList(list));
    }

    /**
     * Retorn a mesma string do parametro com aspas se no nula.
     * caso null retorna a string null.
     *
     * @param string
     * @return
     */
    public static String Aspas(String string) {
        if (string == null) {
            return null;
        }

        return "'" + string + "'";
    }
/**
 * Recebe uma data e retorna a mesma com aspas.
 * @author Rafael Carneiro
 * @date 25/10/2005
 * @param timestamp - Recebe uma data.
 * @return Uma data entre aspas.
 */
    public static String aspasTimestamp(Timestamp timestamp) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("'").append(timestamp).append("'");

        if (timestamp == null) {
            return null;
        }

        return buffer.toString();
    }

    /**
     * This inner class is used to compare with ignore case.
     */
    private class IgnoreCaseComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    }
}
