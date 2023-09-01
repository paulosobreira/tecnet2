package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;


/**
 * @author Sobreira
 * Created on 20/10/2004
 */
public class DatabaseMetaData extends MasterDAO {
    private String database;

    public DatabaseMetaData(String banco) throws Exception {
        super(banco);
    }

    /**
     * Supporting method for getting the catalog (schema) list.
     */
    public Vector getCatalogs() throws Exception {
        Vector catalogs = new Vector();
        Connection conn = null;

        try {
            conn = getConexao();

            java.sql.DatabaseMetaData dbmt = conn.getMetaData();
            ResultSet rs = null;

            try {
                rs = dbmt.getCatalogs();
            } catch (java.sql.SQLException e) {
                e.printStackTrace(System.out);
            }

            if (rs != null) {
                while (rs.next()) {
                    Vector catalog = new Vector();
                    catalog.addElement("catalog");
                    catalog.addElement(rs.getString(1));
                    catalogs.addElement(catalog);
                }

                if ((catalogs == null) || (catalogs.size() == 0)) {
                    rs = dbmt.getSchemas();

                    while (rs.next()) {
                        Vector catalog = new Vector();
                        catalog.addElement("schema");
                        catalog.addElement(rs.getString(1));
                        catalogs.addElement(catalog);
                    }
                }
            } else {
                Vector catalog = new Vector();
                catalog.addElement("");
                catalog.addElement("");
                catalogs.addElement(catalog);
            }
        } catch (Exception e) {
            throw e;
        }

        return catalogs;
    }

    /**
     * Supporting method for getting the table list.
     */
    public Vector getTables() throws Exception {
        Vector results = new Vector();
        Connection conn = null;

        try {
            conn = getConexao();

            String[] type = { "TABLE" };
            java.sql.DatabaseMetaData dbmt = conn.getMetaData();
            ResultSet rs = null;
            rs = dbmt.getTables(null, null, "%", type);

            while (rs.next()) {
                results.addElement(rs.getString(3));
            }
        } catch (Exception e) {
            throw e;
        }

        return results;
    }

    /**
     * Supporting method for getting the table data array.
     */
    public String[][] getTableData(String table) throws Exception {
        Connection conn = null;
        Vector columns = new Vector();

        try {
            conn = getConexao();

            java.sql.DatabaseMetaData dbmt = conn.getMetaData();
            ResultSet rs = null;
            rs = dbmt.getColumns(null, null, table, "%");

            while (rs.next()) {
                Vector column = new Vector();
                column.addElement(rs.getString("TYPE_NAME"));
                column.addElement(rs.getString("COLUMN_NAME"));
                column.addElement(new Integer(rs.getInt("COLUMN_SIZE")));
                column.addElement(new Integer(rs.getInt("DECIMAL_DIGITS")));
                column.addElement(new Integer(rs.getInt("NULLABLE")));
                column.addElement(new Short(rs.getShort("DATA_TYPE")));
                columns.addElement(column);
            }
        } catch (Exception e) {
            throw e;
        }

        String[][] field_data = new String[columns.size()][4];

        for (int i = 0; i < columns.size(); i++) {
            Vector column = (Vector) columns.elementAt(i);
            String type = (String) column.elementAt(0);
            String name = (String) column.elementAt(1);
            int length = ((Integer) column.elementAt(2)).intValue();
            int scale = ((Integer) column.elementAt(3)).intValue();
            int not_null = ((Integer) column.elementAt(4)).intValue();
            short data_type = ((Short) column.elementAt(5)).shortValue();

            if ((data_type == Types.CHAR) || (data_type == Types.VARCHAR) ||
                    (data_type == Types.LONGVARCHAR)) {
                type = "java.lang.String";
            } else if ((data_type == Types.INTEGER) ||
                    (data_type == Types.TINYINT) ||
                    (data_type == Types.SMALLINT)) {
                type = "int";
            } else if (data_type == Types.BIGINT) {
                type = "long";
            } else if (data_type == Types.DECIMAL) {
                if (scale == 0) {
                    if (length > 9) {
                        type = "double";
                    } else {
                        type = "int";
                    }
                } else {
                    type = "double";
                    length += scale;
                }
            } else if ((data_type == Types.FLOAT) ||
                    (data_type == Types.DOUBLE) || (data_type == Types.REAL)) {
                type = "double";
                length += scale;
            } else if (data_type == Types.NUMERIC) {
                if (scale == 0) {
                    if (length > 9) {
                        type = "double";
                    } else {
                        type = "int";
                    }
                } else {
                    type = "double";
                    length += scale;
                }
            } else if (data_type == Types.DATE) {
                type = "java.sql.Date";
                length = 12;
            } else if (data_type == Types.TIMESTAMP) {
                type = "java.sql.Timestamp";
                length = 22;
            } else if (data_type == Types.TIME) {
                type = "java.sql.Time";
                length = 12;
            } else if ((data_type == Types.LONGVARBINARY) ||
                    (data_type == Types.BINARY) ||
                    (data_type == Types.VARBINARY)) {
                type = "byte[]";
            } else if (data_type == Types.BIT) {
                type = "boolean";
            } else if (data_type == Types.ARRAY) {
                type = "java.sql.Array";
            } else if (data_type == Types.BLOB) {
                type = "java.sql.Blob";
            } else if (data_type == Types.CLOB) {
                type = "java.sql.Clob";
            } else if (data_type == Types.STRUCT) {
                type = "java.sql.Struct";
            } else if (data_type == Types.REF) {
                type = "java.sql.Ref";
            } else if (data_type == Types.JAVA_OBJECT) {
                type = "java.lang.Object";
            } else {
                type = "java.lang.String";
            }

            if (length == 0) {
                length = 10;
            }

            field_data[i][0] = type;
            field_data[i][1] = name.toLowerCase();
            field_data[i][2] = length + "";

            if (not_null == 0) {
                field_data[i][3] = "true";
            } else {
                field_data[i][3] = "false";
            }
        }

        return field_data;
    }

    public void setDatabase(String Database) {
        database = Database;
    }

    public String getDatabase() {
        return database;
    }
}
