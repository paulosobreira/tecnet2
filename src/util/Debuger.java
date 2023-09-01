package util;

import org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement;



/**
 * @author Sobreira
 * Created on 25/08/2004
 *
 *
 */
public class Debuger {
    public static void debugar(String s, Class source) {
        saidaPadrao(s);
    }

    public static void debugar(Object o, Class source) {
        /**
         *Debugar as consultas no pool do apache
         */
        String saida = o.toString();

        try {
            if (o instanceof DelegatingPreparedStatement) {
                DelegatingPreparedStatement delegatingPreparedStatement =
                    (DelegatingPreparedStatement) o;
                Object delegate = delegatingPreparedStatement.getDelegate();

                if (delegate != null) {
                    saida = delegate.toString();
                } else {
                    saida = delegatingPreparedStatement.toString();
                }
            } else {
                saida = o.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = e.getMessage();
        }

        saidaPadrao(saida + "\n");
    }

    public static void debugar(String string) {
        saidaPadrao(string);
    }

    private static void saidaPadrao(String string) {
        System.out.println(string);
        
    }
}
