package struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import servicos.ServicoLogin;

import vos.tabelas.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author Paulo Sobreira
 * Criado Em 13:03:09
 */
public class LoginAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoLogin service = new ServicoLogin();
        LoginActionForm actionForm = (LoginActionForm) form;
        HttpSession session = req.getSession();
        Usuario usuario = service.login(actionForm);

        if (usuario != null) {
            session.setAttribute("Usuario", usuario);
            req.setAttribute("def", "userDefinition");
            
            if (service.verificaAdministrador(usuario)){
                req.setAttribute("def", "adminDefinition");    
            }
            
            return (mapping.findForward("success"));
        } else {
            return (mapping.findForward("failure"));
        }
    }
}
