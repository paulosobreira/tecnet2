package struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import servicos.ServicoCompra;
import servicos.ServicoLogin;
import vos.tabelas.Usuario;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author Paulo Sobreira
 * Criado Em 16:37:04
 */
public class EfetuarPedidoAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        ServicoCompra compra = new ServicoCompra();

        HttpSession session = req.getSession();
        boolean registrado = false;
        List lista_produtos = (List) session.getAttribute("lista_produtos");
        Usuario usuario =  (Usuario) session.getAttribute("Usuario");
        registrado = compra.efetuarPedido((EfetuarPedidoActionForm) form,
                lista_produtos,usuario);

        if (registrado) {
            return (mapping.findForward("success"));
        } else {
            req.setAttribute("erro", "Erro salvando Pedido.");
            return (mapping.findForward("same"));
        }
    }
}
