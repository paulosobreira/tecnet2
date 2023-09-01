package struts;

import org.apache.commons.collections.map.HashedMap;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import servicos.ServicoCompra;
import servicos.ServicoPesquisa;

import util.BeanUtil;
import util.Util;

import vos.ProdutoCategoriaVO;

import vos.tabelas.Produto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author Paulo Sobreira
 * Criado Em 13:03:09
 */
public class CarrinhoAction extends DispatchAction {
    public ActionForward adicionar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        HttpSession session = req.getSession();
        List lista_produtos = (List) session.getAttribute("lista_produtos");

        if (lista_produtos == null) {
            lista_produtos = new ArrayList();
            session.setAttribute("lista_produtos", lista_produtos);
        }

        int produtoId = Util.zeroOuInt(req.getParameter("produtoId"));

        if (produtoId != 0) {
            Produto produto = new Produto();
            produto.setId(produtoId);

            ServicoPesquisa servicoPesquisa = new ServicoPesquisa();
            produto = servicoPesquisa.pesquisarProduto(produto);

            ProdutoCategoriaVO prodVO = new ProdutoCategoriaVO();
            BeanUtil.copiarVO(produto, prodVO);
            prodVO.setProduto_id(produto.getId());
            prodVO.setQtde_produto(1);
            lista_produtos.add(prodVO);
        }

        ServicoCompra compra = new ServicoCompra();
        compra.processarPreco(lista_produtos);
        req.setAttribute("pesoTotal", compra.CalculaPesoTotal(lista_produtos));
        req.setAttribute("precoTotal", compra.CalculaPrecoTotal(lista_produtos));

        return (mapping.findForward("success"));
    }

    public ActionForward atualizar(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        HttpSession session = req.getSession();
        List lista_produtos = (List) session.getAttribute("lista_produtos");

        if (lista_produtos == null) {
            lista_produtos = new ArrayList();
            session.setAttribute("lista_produtos", lista_produtos);
        }

        Map map = new HashedMap();
        Util.formataParametros(req.getParameterMap(), map);

        for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();

            if (key.startsWith("prod_")) {
                int id = Util.zeroOuInt(key.split("_")[1]);

                for (Iterator iterator = lista_produtos.iterator();
                        iterator.hasNext();) {
                    ProdutoCategoriaVO prodVO = (ProdutoCategoriaVO) iterator.next();

                    if (prodVO.getProduto_id() == id) {
                        prodVO.setQtde_produto(Util.zeroOuInt(map.get(key)));

                        if (prodVO.getQtde_produto() < 1) {
                            prodVO.setQtde_produto(1);
                        }

                        prodVO.setPreco_total(prodVO.getPreco() * prodVO.getQtde_produto());
                    }
                }
            }
        }

        ServicoCompra compra = new ServicoCompra();
        req.setAttribute("pesoTotal", compra.CalculaPesoTotal(lista_produtos));
        req.setAttribute("precoTotal", compra.CalculaPrecoTotal(lista_produtos));

        return (mapping.findForward("success"));
    }

    public ActionForward remover(ActionMapping mapping, ActionForm form,
        HttpServletRequest req, HttpServletResponse arg3)
        throws Exception {
        HttpSession session = req.getSession();
        List lista_produtos = (List) session.getAttribute("lista_produtos");

        if (lista_produtos == null) {
            lista_produtos = new ArrayList();
            session.setAttribute("lista_produtos", lista_produtos);
        }

        int produtoId = Util.zeroOuInt(req.getParameter("produtoId"));

        if (produtoId != 0) {
            ProdutoCategoriaVO produtoCategoriaVO = new ProdutoCategoriaVO();
            produtoCategoriaVO.setProduto_id(produtoId);
            lista_produtos.remove(produtoCategoriaVO);
        }

        ServicoCompra compra = new ServicoCompra();
        req.setAttribute("pesoTotal", compra.CalculaPesoTotal(lista_produtos));
        req.setAttribute("precoTotal", compra.CalculaPrecoTotal(lista_produtos));

        return (mapping.findForward("success"));
    }
}
