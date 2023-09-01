package struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import servicos.ServicoLogin;

/**
 * @author Paulo Sobreira
 * Criado Em 16:37:04
 */
public class CadastrarAction extends Action {
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest arg2, HttpServletResponse arg3)
            throws Exception {
            ServicoLogin service = new ServicoLogin();

            boolean registrado = false;

            registrado = service.cadastrar((CadastrarActionForm) form);

            if (registrado) {
                return (mapping.findForward("success"));
            } else {
                return (mapping.findForward("failure"));
            }
        }

}
