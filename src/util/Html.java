/* Html.java
 * Criado em 25/10/2005.
 */
package util;

import java.sql.Timestamp;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author Rafael Carneiro (<a href="mailto:rafael@portaljava.com">e-mail</a>)
 * @author Paulo Sobreira
 */
public class Html {
    
    public static final String CHECKBOX_ON = "on";

    //ITALO COmENTAR
    /**
     * @author Sobreira
     */
    public static boolean checkBoxMarcado(String value) {
        return ("on".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value));
    }

    /**
    * Este mtodo recebe uma String como parmetro e retorna uma String com
    * uma tag HTML center (centralizando-a).
    * @param  - str.
    * @return - String com cdigo HTML.
    */
    public static String center(String str) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<center>").append(str).append("</center>");

        return buffer.toString();
    }

    /**
     * Este mtodo recebe um int como parmetro e retorna uma String com
     * uma tag HTML center (centralizando-a).
     * @param  - str.
     * @return - String com cdigo HTML.
     */
    public static String center(int str) {
        return center(String.valueOf(str));
    }

    /**
     * Este mtodo recebe uma String como parmetro e retorna uma String com
     * uma tag HTML bold (negrito).
     * @param  - str.
     * @return - String com cdigo HTML.
     */
    public static String bold(String str) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<b>").append(str).append("</b>");

        return buffer.toString();
    }

    /**
     * Este mtodo recebe uma String (nome da funo JavaScript) como parmetro
     * e retorna uma String com o cdigo JavaScript <b>onclick</b> junto com
     * seu respectivo nome (nome da funo recebida).
     * @param  - str.
     * @param  - title - Ttulo do link
     * @return - String com cdigo JavaScript <b>onclick</b>.
     */
    public static String onClick(String funcaoJs, String[] parametros,
        String nome, String title) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<a ").append("title=").append("'").append(title)
              .append("'").append(" onclick=\"").append(funcaoJs).append("(");

        StringBuffer paramBuffer = new StringBuffer();

        for (int i = 0; i < parametros.length; i++) {
            paramBuffer.append("'").append(parametros[i]).append("'").append(",");
        }

        buffer.append(paramBuffer.toString().substring(0,
                paramBuffer.toString().length() - 1));
        buffer.append(");\" href=\"javascript:void();\" >");
        buffer.append(nome).append("</a>");

        return buffer.toString();
    }

    /**
     * Este mtodo recebe o caminho da a jsp ou action, um mapa de parametros,
     * o target da pgina, o title do link.
     * @param path - "../seila/Nada.do"
     * @param parametros  map.put("pess_id", "6");  map.put("resetfull_action", "");
     * @return <a href="../seila/Nada.do?pess_id=6&resetfull_action=">
     */
    public static String href(String path, Map parametros, String target,
        String title) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<a ");

        if (!Util.isNullOrEmpty(target)) {
            buffer.append("target=" + target);
        }

        buffer.append("title=\"").append(title).append("\"");
        buffer.append(" href=\"").append(path).append("?");

        for (Iterator iter = parametros.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            String value = ((parametros.get(key) != null)
                ? parametros.get(key).toString() : "");
            buffer.append(key).append("=").append(value).append("&");
        }

        String retorno = buffer.toString().substring(0,
                buffer.toString().length() - 1);

        retorno += "\">";

        return retorno;
    }

    /**
     * Este mtodo recebe o caminho da a jsp ou action  e um mapa de parametros
     * @param path - "../seila/Nada.do"
     * @param parametros  map.put("pess_id", "6");  map.put("resetfull_action", "");
     * @return <a href="../seila/Nada.do?pess_id=6&resetfull_action=">
     */
    public static String href(String path, Map parametros, String target) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" <a ");

        if (!Util.isNullOrEmpty(target)) {
            buffer.append(" target= '" + target+"'");
        }

        buffer.append(" href='").append(path).append("?");

        for (Iterator iter = parametros.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            String value = ((parametros.get(key) != null)
                ? parametros.get(key).toString() : "");
            buffer.append(key).append("=").append(value).append("&amp;");
        }

        String retorno = buffer.toString().substring(0,
                buffer.toString().length() - 1);

        retorno += "'>";

        return retorno;
    }

    /**
     * Este mtodo recebe o caminho da a jsp ou action  e um mapa de parametros
     * @param path - "../seila/Nada.do"
     * @param parametros  map.put("pess_id", "6");  map.put("resetfull_action", "");
     * @return <a href="../seila/Nada.do?pess_id=6&resetfull_action=">
     */
    public static String href(String path, Map parametros) {
        return href(path, parametros, null);
    }

    /**
     * Mtodo que recebe duas Strings por parmetro e retorna a tag HTML maito.
     * @param email - E-mail
     * @param nome - Pode ser tanto o prprio e-mail como qualquer outro nome
     * @return
     */
    public static String mailto(String email, String nome) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<a href=\"").append(email).append("\"")
              .append(" target=\"_blank\">").append(nome).append("</a>");

        return null;
    }

    /**
     * Este mtodo retorna o cdigo HTML do checkbox.
     * @param nome - Nome da lista
     * @param valor - Valor do checkbox
     * @return
     */
    public static String checkBox(String nome, String valor) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<input type=\"checkbox\" name=\"").append(nome)
              .append("\"").append(" Class=\"baseField\"").append(" value=\"")
              .append(valor).append("\"").append(" >");

        return buffer.toString();
    }

    /**
     * Este mtodo retorna o cdigo HTML do checkbox.
     * @param nome - Nome da lista
     * @param valor - Valor do checkbox
     * @return
     */
    public static String checkBox(String nome, int valor) {
        return checkBox(nome, String.valueOf(valor));
    }
    public static String itemExpandivel(String descricao, List obj){
        return itemExpandivel(descricao, obj,false); 
    }
    
    public static String itemExpandivel(String descricao, List obj,boolean expandir) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<div>");
        buffer.append("<p><a onclick=togNode(this)><u>");
        buffer.append(descricao);
        buffer.append("</u></a></p>");
        buffer.append("<p></p>");
        buffer.append("<table style=\"display:none\" id=\"expandTable\">");
        buffer.append("<tbody>");

        for (Iterator iter = obj.iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            buffer.append("<tr>");
            buffer.append("<td>");
            buffer.append(element);
            buffer.append("<br>");
            buffer.append("</td>");
        }

        buffer.append("</tr>");
        buffer.append("</tbody>");
        buffer.append("</table>");
        buffer.append("</div>");

        if (expandir){
            buffer.append("<script>");
            buffer.append("dynExpandAll();");
            buffer.append("</script>");
            
        }
        return buffer.toString();
    }

    public static String inputType(String type, int size, int maxlength,
        String name, int sufixoDoNome, String style, String valor) {
        return inputType(type, size, maxlength, name, sufixoDoNome, style,
            valor, false);
    }

    /**
     * Input Type apropriado
     * @param type - Tipo do input
     * @param size - Tamanho
     * @param maxlength - Nmero mximo de caracteres
     * @param name - Nome do campo
     * @param sufixoDoNome - usado para tags sequenciais como data1 ,data2 , data3
     * @param style - Estilo
     * @param valor - Valor do campo (String)
     * @return a String com a tag HTML input.
     */
    public static String inputType(String type, int size, int maxlength,
        String name, int sufixoDoNome, String style, String valor,
        boolean readyOnly) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(" <input type=\"").append(type).append("\"");
        buffer.append(" size=\"").append(size).append("\"");
        buffer.append(" maxlength=\"").append(maxlength).append("\"");
        buffer.append(" name=\"").append(name).append(sufixoDoNome).append("\"");
        buffer.append(" class=\"")
              .append(Util.isNullOrEmpty(style) ? "baseField" : style).append("\"");
        buffer.append(" value=\"").append(valor).append("\"");

        if (readyOnly) {
            buffer.append(" readonly=\"true\"");
        }

        buffer.append("/>");

        return buffer.toString();
    }

    /**
     * Input Type .
     * @param type - Tipo do input
     * @param size - Tamanho
     * @param maxlength - Nmero mximo de caracteres
     * @param name - Nome do campo
     * @param sufixoDoNome - usado para tags sequenciais como data1 ,data2 , data3
     * @param style - Estilo
     * @param valor - Valor do campo (int)
     * @return a String com a tag HTML input.
     */
    public static String inputType(String type, int size, int maxlength,
        String name, int sufixoDoNome, String style, int valor) {
        return inputType(type, size, maxlength, name, sufixoDoNome, style,
            String.valueOf(valor));
    }

    /**
     * Input Type apropriado para JavaScripts..
     * @param type - Tipo do input
     * @param size - Tamanho
     * @param maxlength - Nmero mximo de caracteres
     * @param name - Nome do campo
     * @param sufixoDoNome - usado para tags sequenciais como data1 ,data2 , data3
     * @param style - Estilo
     * @param valor - Valor do campo
     * @return a String com a tag HTML input.
     */
    public static String inputType(String type, int size, int maxlength,
        String name, int sufixoDoNome, String style, String valor,
        String javascript) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<input type=\"").append(type).append("\"");
        buffer.append("size=\"").append(size).append("\"");
        buffer.append("maxlength=\"").append(maxlength).append("\"");
        buffer.append("name=\"").append(name).append(sufixoDoNome).append("\"");
        buffer.append("class=\"")
              .append(Util.isNullOrEmpty(style) ? "baseField" : style).append("\"");
        buffer.append("value=\"").append(valor).append("\"");
        buffer.append(javascript);
        buffer.append("/>");

        return buffer.toString();
    }

    /**
     * Input Type apropriado com valor Timestamp.
     * @param type - Tipo do input
     * @param size - Tamanho
     * @param maxlength - Nmero mximo de caracteres
     * @param name - Nome do campo
     * @param sufixoDoNome - usado para tags sequenciais como data1 ,data2 , data3
     * @param style - Estilo
     * @param valor - Valor do campo
     * @return a String com a tag HTML input.
     */
    public static String inputType(String type, int size, int maxlength,
        String name, int sufixoDoNome, String style, Timestamp timestamp) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<input type=\"").append(type).append("\"");
        buffer.append("size=\"").append(size).append("\"");
        buffer.append("maxlength=\"").append(maxlength).append("\"");
        buffer.append("name=\"").append(name).append(sufixoDoNome).append("\"");
        buffer.append("class=\"")
              .append(Util.isNullOrEmpty(style) ? "baseField" : style).append("\"");
        buffer.append("value=\"").append(timestamp).append("\"");
        buffer.append("/>");

        return buffer.toString();
    }

    /**
     * Tag HTML font color
     * @param cor - cor da fonte
     * @param texto - texto para a cor
     * @return
     */
    public static String fontColor(String cor, String texto) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<font color=\"").append(cor).append("\">").append(texto)
              .append("</font>");

        return buffer.toString();
    }

    /**
     * Tag HTML blink
     * @param texto - texto dentro da tag blink
     * @return
     */
    public static String blink(String texto) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<blink>").append(texto).append("</blink>");

        return buffer.toString();
    }

    /**
     * Tag HTML font size
     * @param tamanho - tamanho da fonte
     * @param texto - texto ao ser alterado
     * @return
     */
    public static String fontSize(int tamanho, String texto) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<font size=\"").append(tamanho).append("\">")
              .append(texto).append("</font>");

        return buffer.toString();
    }

    /**
     * Gera um componete de seleo unica do tipo combobox
     * @param nomeComponente - propriedade name
     * @param conteudo - Uma lista contendo arrays de String onde
     * String[]{"Label","valor"};
     * @param selected - posio da lista conteudo que vea estar slecionada.
     * @return
     */
    public static String comboBox(String nomeComponente, List conteudo,
        int selected) {
        return comboBox(nomeComponente, conteudo, selected, true);
    }

    public static String comboBox(String nomeComponente, List conteudo,
        int selected, boolean ativo) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<select name=\"" + nomeComponente + "\"");

        if (!ativo) {
            buffer.append(" disabled=\"disabled\" ");
        }

        buffer.append("/>");

        for (int i = 0; i < conteudo.size(); i++) {
            String[] strings = (String[]) conteudo.get(i);
            buffer.append("<option ");

            if (i == selected) {
                buffer.append("selected ");
            }

            buffer.append("value = \"" + strings[1] + "\">");
            buffer.append(strings[0]);
        }

        buffer.append("</select>");

        return buffer.toString();
    }

    public static String align(String campo, String align) {
        return "<div align=\"" + align + "\">" + campo + "</div>";
    }
}
