package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Sobreira
 * Created on 20/10/2004
 */
public class TabelaVOGerador {
    private static final String VERSION = "1.9";
    private String source_path;
    private String base_package;
    private String class_path;
    private String libraries;
    private String owner;
    private String table;
    private String database;
    private Vector metaData;
    private String db_type;
    private String catalog;

    public static void main(String[] args) throws Exception {
        TabelaVOGerador gerador = new TabelaVOGerador();
        gerador.setBase_package("WEB-INF/src/vos.tabelas");
        String pacote = "vos.tabelas";
        gerador.setDatabase("xdb");
        DatabaseMetaData data = new DatabaseMetaData("");
        Vector vector = data.getTables();
        int numClasses = 0;
        gerador.apagarTabelasVelhas();
        for (Iterator iter = vector.iterator(); iter.hasNext();) {
            String table = (String) iter.next();
            gerador.write(table,pacote);
            numClasses++;
        }
        System.out.println("Terminado. "+numClasses+" Classes Criadas ");
    }

    public void apagarTabelasVelhas(){
        String new_package = base_package;
        String new_source_path = GeradorUtil.setSlash(new_package + "/");
        File dir = new File(new_source_path);
        File[] files = dir.listFiles();
        if (files==null){
            return;
        }
        int cont = 0;
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
            cont++;
        }
        System.out.println(cont+" arquivos apagados em "+new_source_path);

    }
    public void write(String table, String pacote) throws IOException, Exception {
        this.table = table;
        String new_package = base_package;
        String new_source_path = GeradorUtil.setSlash(new_package + "/");
        String new_table = GeradorUtil.setJavaName(table);
        String file = new_source_path + new_table + ".java";
        DatabaseMetaData DatabaseMetaData = new DatabaseMetaData("");
        DatabaseMetaData.setDatabase(database);
        String[][] fields = DatabaseMetaData.getTableData(table);
        String atributes = "";
        String methods = "";
        ArrayList atributos = new ArrayList();
        for (int i = 0; i < fields.length; i++) {
            atributos.add(new Atributos(fields[i][0], fields[i][1]));
            atributes += writeAtribute(fields[i][0], fields[i][1]);
            methods += writeGetter(fields[i][0], fields[i][1]);
            methods += writeSetter(fields[i][0], fields[i][1]);
        }

        File dir = new File((new File(file)).getParent());
        dir.mkdirs();
        BufferedWriter buf = getBuffer(file);
        buf.write(writePackage(pacote));

        //buf.write(writeImport());
        buf.write(writeComments());
        buf.write(writeClass());
        buf.write(atributes);
        buf.write("\n");
        buf.write(writeConstructor());
        buf.write(writeAllAtributesConstructor(atributos));
        buf.write(methods);
        buf.write(writeToString(GeradorUtil.setJavaName(table), atributos));
        buf.write(writeCloseClass());
        buf.close();
    }

    public static BufferedWriter getBuffer(String FULL_PATH)
        throws IOException {
        File file = new File(FULL_PATH);
        FileWriter writer = new FileWriter(file);
        BufferedWriter buffer = new BufferedWriter(writer);

        return buffer;
    }

    private String writePackage(String PACKAGE) {
        String txt = "";
        txt = "package " + PACKAGE + ";\n\n";

        return txt;
    }

    private String writeImport() {
        String txt = "";
        txt = "import java.lang.reflect.*;\n\n";

        return txt;
    }

    private String writeComments() throws Exception {
        StringBuffer txt = new StringBuffer();
        txt.append("/***Gerador de VO Sobreira " + table + "\n");
        txt.append(
            "    " + " Ver: " + VERSION + " -  Data: " + ""
            + FormatDate.getTimestamp() + " */\n"
        );

        return txt.toString();
    }

    private String writeClass() {
        String classeNome = GeradorUtil.setJavaName(table);
        System.out.println("Criando : "+classeNome+".java");
        return "public class " + classeNome + " "
        + "implements java.io.Serializable {\n";
    }

    private String writeConstructor() {
        StringBuffer txt = new StringBuffer();
        txt.append("  public " + GeradorUtil.setJavaName(table) + "() {\n");
        txt.append("  }\n\n");

        return txt.toString();
    }

    private String writeAllAtributesConstructor(List allAtributes) {
        StringBuffer txt = new StringBuffer();
        txt.append("  public " + GeradorUtil.setJavaName(table) + "(");
        StringBuffer constLine = new StringBuffer();
        for (int i = 0; i < allAtributes.size(); i++) {
            Atributos atributos = (Atributos) allAtributes.get(i);
            if (i < (allAtributes.size() - 1))
                constLine.append(
                    atributos.getTipo() + " " + atributos.getAtributo()
                    + ",\n\t\t"
                );
            else
                constLine.append(
                    atributos.getTipo() + " " + atributos.getAtributo()
                );
        }
        txt.append(constLine.toString());
        txt.append(") {\n");
        for (int i = 0; i < allAtributes.size(); i++) {
            Atributos atributos = (Atributos) allAtributes.get(i);
            txt.append(
                "		this."
                + GeradorUtil.setAttributeName(atributos.getAtributo())
                             .toLowerCase() + " = " + atributos.getAtributo()
                + ";"
            );
            txt.append("\n");
        }
        txt.append("  }\n\n");

        return txt.toString();
    }

    private String writeToString(String className, List allAtributes) {
        StringBuffer txt = new StringBuffer();
        txt.append("\tpublic String toString() {\n");
        txt.append("\t\tStringBuffer buffer = new StringBuffer();\n");
        txt.append("\t\tbuffer.append(\"" + className + "[\");\n");
        for (int i = 0; i < allAtributes.size(); i++) {
            Atributos atributos = (Atributos) allAtributes.get(i);
            txt.append(
                "\t\t" + "buffer.append(\"" + atributos.getAtributo()
                + " =\").append("
                + GeradorUtil.setAttributeName(atributos.getAtributo())
                             .toLowerCase() + ");\n"
            );
        }
        txt.append("\t\tbuffer.append(\"]\");\n");
        txt.append("\t\treturn buffer.toString();\n");
        txt.append("\t}\n");

        return txt.toString();
    }

    private String writeAtribute(String FIELD_TYPE, String FIELD_NAME) {
        return "  private " + FIELD_TYPE + " "
        + GeradorUtil.setAttributeName(FIELD_NAME).toLowerCase() + ";\n";
    }

    private String writeGetter(String FIELD_TYPE, String FIELD_NAME) {
        StringBuffer txt = new StringBuffer();
        txt.append(
            "  public " + FIELD_TYPE + " get"
            + GeradorUtil.setJavaName(GeradorUtil.setAttributeName(FIELD_NAME))
            + "() {\n"
        );
        txt.append(
            "    return "
            + GeradorUtil.setAttributeName(FIELD_NAME).toLowerCase() + ";\n"
        );
        txt.append("  }\n\n");

        return txt.toString();
    }

    private String writeSetter(String FIELD_TYPE, String FIELD_NAME) {
        StringBuffer txt = new StringBuffer();
        txt.append(
            "  public void set"
            + GeradorUtil.setJavaName(GeradorUtil.setAttributeName(FIELD_NAME))
            + "(" + FIELD_TYPE + " " + FIELD_NAME.toUpperCase() + ") {\n"
        );
        txt.append(
            "    " + GeradorUtil.setAttributeName(FIELD_NAME).toLowerCase()
            + " = " + FIELD_NAME.toUpperCase() + ";\n"
        );
        txt.append("  }\n\n");

        return txt.toString();
    }

    private String writeCloseClass() {
        return "}\n";
    }

    public String getBase_package() {
        return base_package;
    }

    public void setBase_package(String base_package) {
        this.base_package = base_package;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getClass_path() {
        return class_path;
    }

    public void setClass_path(String class_path) {
        this.class_path = class_path;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getLibraries() {
        return libraries;
    }

    public void setLibraries(String libraries) {
        this.libraries = libraries;
    }

    public Vector getMetaData() {
        return metaData;
    }

    public void setMetaData(Vector metaData) {
        this.metaData = metaData;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSource_path() {
        return source_path;
    }

    public void setSource_path(String source_path) {
        this.source_path = source_path;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    private class Atributos {
        private String tipo;
        private String atributo;

        /**
         * @param tipo
         * @param atributo
         */
        public Atributos(String tipo, String atributo) {
            super();
            this.tipo = tipo;
            this.atributo = atributo;
        }

        public String getAtributo() {
            return atributo;
        }

        public void setAtributo(String atributo) {
            this.atributo = atributo;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }
}
