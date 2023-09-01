package util;

import java.io.File;
import java.io.IOException;


/**
 * @author Sobreira
 * Criado em 20/01/2006
 */
public class Renomeia {
    private final static String RENOMEAR = "string a remover do nome";

    public static void main(String[] args) throws Exception {
        loopFiles(new File("diretorio inicial"));
    }

    private static void loopFiles(File file) throws Exception {
        if (file.getName().endsWith(".exteno")) {
            renomear(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                loopFiles(files[i]);
            }
        }
    }

    private static void renomear(File file) throws IOException {
        if (file.getName().startsWith(RENOMEAR)) {
            String oldName = file.getName();
            oldName = oldName.replaceAll(RENOMEAR, "").trim();
            System.out.println((file.getParent() + "\\" + oldName));
            file.renameTo(new File((file.getParent() + "\\" + oldName)));
        }
    }
}
