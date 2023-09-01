package struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import servicos.ServicoPesquisa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Paulo Sobreira
 * Criado Em 16:37:04
 */
public class PesquisarProdutoAction extends DispatchAction {
    public ActionForward pesquisar_pedido(ActionMapping mapping,
        ActionForm form, HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();

        req.setAttribute("pedidos", pesquisa.pesquisarPedidos());

        return (mapping.findForward("pedidos"));
    }

    public ActionForward pesquisar_tudo(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();

        req.setAttribute("produtos", pesquisa.pesquisarProdutos());

        return (mapping.findForward("cadastro"));
    }

    public ActionForward carregar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();

        req.setAttribute("categorias", pesquisa.pesquisarCategorias());

        return (mapping.findForward("success"));
    }

    public ActionForward pesquisar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();
        req.setAttribute("categorias", pesquisa.pesquisarCategorias());
        req.setAttribute("produtos",
            pesquisa.pesquisarProdutos((PesquisarProdutoActionForm) form));

        return (mapping.findForward("success"));
    }

    public ActionForward promocao(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoPesquisa pesquisa = new ServicoPesquisa();
        req.setAttribute("produtos", pesquisa.pesquisarProdutosPromocao());

        return (mapping.findForward("promocoes"));
    }
}
