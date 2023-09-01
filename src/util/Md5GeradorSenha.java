package util;

import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;


/**
 * @author Sobreira
 * Criado em 20/06/2005
 */
public class Md5GeradorSenha {
    public static void main(String[] args)
        throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JPasswordField field = new JPasswordField(20);
        JOptionPane.showMessageDialog(null, field, "Entre com a senha",
            JOptionPane.QUESTION_MESSAGE);

        String senha = new String(field.getPassword());
        System.out.println(Util.md5(senha));
        System.exit(0);
    }
}
