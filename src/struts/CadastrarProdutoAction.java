package struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import servicos.ServicoCadastro;
import servicos.ServicoPesquisa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Paulo Sobreira
 * Criado Em 16:37:04
 */
public class CadastrarProdutoAction extends DispatchAction {

    public ActionForward salvar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoCadastro servicoCadastro = new ServicoCadastro();

        req.setAttribute("produtos",
            servicoCadastro.cadastrarProduto((CadastrarProdutoActionForm) form));

        return (mapping.findForward("success"));
    }
}
