package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JOptionPane;


/**
 * @author Sobreira
 * Criado em 10/04/2006
 */
public class Deploy {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        System.out.println("Arquivo de propriedades em:");

        File file = new File(args[0]);
        System.out.println(file.getAbsolutePath());

        FileInputStream fis = new FileInputStream(file);

        props.load(fis);

        int tipo =
            ("".equals(props.getProperty("deployCompleto"))?0:1);
//            JOptionPane.showConfirmDialog(null, "Deploy Simples?",
//                "Tipo de deploy", JOptionPane.YES_NO_OPTION);
        
        
        if (0 == tipo) {
            props.setProperty("MODODEBUG", "S");
            props.setProperty("deployCompleto", "");
        } else {
            props.setProperty("MODODEBUG", "N");
            props.setProperty("deployCompleto", "deployCompleto");
        }

        props.setProperty("SINGLE_CONNECTION", "N");
        props.setProperty("DESABILTAR_AUDITORIA", "N");

        FileOutputStream stream = new FileOutputStream(file);

        Enumeration enumeration = props.keys();

        while (enumeration.hasMoreElements()) {
            String element = (String) enumeration.nextElement();
            System.out.println(element + " - " + props.getProperty(element));
        }
        if (0 == tipo) {
            System.out.println("DEPLOY SIMPLES");
        } else {
            System.out.println("DEPLOY COMPLETO");
        }

        props.store(stream, null);
    }
}
