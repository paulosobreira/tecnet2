package servicos;

import java.util.Iterator;
import java.util.List;

import struts.PesquisarProdutoActionForm;
import util.Util;
import vos.PedidosVO;
import vos.ProdutoCategoriaVO;
import vos.tabelas.Categoria;
import vos.tabelas.Produto;
import vos.tabelas.Promocao;


/**
 * @author Paulo Sobreira
 * Criado Em 16:22:56
 */
public class ServicoPesquisa {
    public List pesquisarProdutos() {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            return serviceDAO.pesquisarVO(new Produto());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarCategorias() {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            return serviceDAO.pesquisarVO(new Categoria());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarProdutoCategorias() {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            return serviceDAO.pesquisarProdutoCategorias();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarProdutos(PesquisarProdutoActionForm form) {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            List retorno = serviceDAO.pesquisarProdutoCategorias(form);
            verificarDesconto(retorno);

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void verificarDesconto(List retorno) throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();

        for (Iterator iter = retorno.iterator(); iter.hasNext();) {
            ProdutoCategoriaVO element = (ProdutoCategoriaVO) iter.next();
            Promocao promocao = new Promocao();
            promocao.setProduto(element.getProduto_id());
            promocao = (Promocao) Util.get0(serviceDAO.pesquisarVO(promocao));

            if (promocao != null) {
                element.setPercentual(promocao.getPercentual());
            }
        }
    }

    public Produto pesquisarProduto(Produto produto) {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            return (Produto) Util.get0(serviceDAO.pesquisarVO(produto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarPromocoes() {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            return serviceDAO.pesquisaPromocoes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarProdutosPromocao() {
        ServiceDAO serviceDAO;

        try {
            serviceDAO = new ServiceDAO();

            List retorno = serviceDAO.pesquisaPromocoes();

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List pesquisarPedidos() {
        ServiceDAO serviceDAO;
        
        try {
            serviceDAO = new ServiceDAO();
            ServicoCompra compra = new ServicoCompra();
            List retorno = serviceDAO.pesquisarPedidos();

            for (Iterator iter = retorno.iterator(); iter.hasNext();) {
                PedidosVO pedidosVO = (PedidosVO) iter.next();
                List lista_produtos =serviceDAO.pesquisarProdutoPedido(
                        pedidosVO.getId());
                pedidosVO.setListaProdutos(lista_produtos);
                compra.processarPreco(lista_produtos);
            }

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
