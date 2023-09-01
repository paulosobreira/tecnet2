package struts;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Paulo Sobreira
 * Criado Em 13:08:51
 */
public class LoginActionForm extends ValidatorForm {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
