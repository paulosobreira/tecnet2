package struts;

import org.apache.struts.action.Action;
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
public class CadastrarPromocaoAction extends DispatchAction {
    public ActionForward carregar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();

        req.setAttribute("produtos", pesquisa.pesquisarProdutos());
        req.setAttribute("promocoes", pesquisa.pesquisarPromocoes());

        return (mapping.findForward("success"));
    }

    public ActionForward salvar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoCadastro servicoCadastro = new ServicoCadastro();
        ServicoPesquisa pesquisa = new ServicoPesquisa();
        req.setAttribute("promocoes",
            servicoCadastro.cadastrarPromocao(
                (CadastrarPromocaoActionForm) form));
        req.setAttribute("produtos", pesquisa.pesquisarProdutos());

        return (mapping.findForward("success"));
    }

    public ActionForward remover(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoCadastro servicoCadastro = new ServicoCadastro();
        ServicoPesquisa pesquisa = new ServicoPesquisa();
        req.setAttribute("promocoes",
            servicoCadastro.removerPromocao(req.getParameter("produtoId")));
        req.setAttribute("produtos", pesquisa.pesquisarProdutos());

        return (mapping.findForward("success"));
    }
    

}
