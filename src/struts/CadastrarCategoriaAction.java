package struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import servicos.ServicoCadastro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Paulo Sobreira
 * Criado Em 16:37:04
 */
public class CadastrarCategoriaAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoCadastro servicoCadastro = new ServicoCadastro();

        req.setAttribute("categorias",
            servicoCadastro.cadastrarCategoria(
                (CadastrarCategoriaActionForm) form));

        return (mapping.findForward("success"));
    }
}
