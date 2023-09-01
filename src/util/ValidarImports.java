package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * @author Sobreira
 * Criado em 16/10/2006
 */
public class ValidarImports {
    private static final String IMPORT = "import br.com.uniodontoce.negocio.";
    private static final String PACKAGE = "package br.com.uniodontoce.negocio.";

    public static void main(String[] args) throws Exception {
        String appDir = args[0];
        File webAppRoot = new File(appDir);
        loopFiles(webAppRoot);
    }

    /**
     * @param file
     * @throws Exception
     */
    private static void loopFiles(File file) throws Exception {
        if (file.getName().endsWith(".java")) {
            executaSubistituicao(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                loopFiles(files[i]);
            }
        }
    }

    private static void executaSubistituicao(File file)
        throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        if (!line.startsWith(PACKAGE)) {
            return;
        }

        String pack = line.split(PACKAGE)[1].split("\\.")[0];
        pack = pack.replaceAll(";", "");

        while (line != null) {
            if (line.startsWith(IMPORT)) {
                String importPack = line.split(IMPORT)[1].split("\\.")[0];

                if (!("geral".equals(importPack) || pack.equals(importPack)) &&
                        !file.getName().startsWith("Migracao") &&
                        !file.getName().startsWith("FabricaSubSistema")) {
                    throw new Exception("Violao de import em: \n" +
                        file.getAbsolutePath());
                }
            }

            line = reader.readLine();
        }
    }
}
