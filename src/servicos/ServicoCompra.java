package servicos;

import struts.EfetuarPedidoActionForm;

import util.BeanUtil;
import util.Util;

import vos.ProdutoCategoriaVO;

import vos.tabelas.Pedido;
import vos.tabelas.Produto_pedido;
import vos.tabelas.Promocao;
import vos.tabelas.Usuario;

import java.lang.reflect.InvocationTargetException;

import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;


/**
 * @author Paulo Sobreira
 * Criado Em 10:51:53
 */
public class ServicoCompra {
    public Double CalculaPesoTotal(List lista_produtos) {
        double peso = 0;

        for (Iterator iter = lista_produtos.iterator(); iter.hasNext();) {
            ProdutoCategoriaVO categoriaVO = (ProdutoCategoriaVO) iter.next();
            peso += (categoriaVO.getPeso() * categoriaVO.getQtde_produto());
        }

        return new Double(peso);
    }

    public Double CalculaPrecoTotal(List lista_produtos) {
        double preco = 0;

        for (Iterator iter = lista_produtos.iterator(); iter.hasNext();) {
            ProdutoCategoriaVO categoriaVO = (ProdutoCategoriaVO) iter.next();
            double precoDesc = categoriaVO.getPreco() -
                ((categoriaVO.getPreco() * categoriaVO.getPercentual()) / 100);
            preco += (precoDesc * categoriaVO.getQtde_produto());
            ;
        }

        return new Double(preco);
    }

    public boolean efetuarPedido(EfetuarPedidoActionForm form,
        List lista_produtos, Usuario usuario) throws SQLException, Exception {
        if (lista_produtos.isEmpty()) {
            return false;
        }

        Pedido pedido = new Pedido();
        ServiceDAO serviceDAO = new ServiceDAO();
        Object tx = serviceDAO.iniciarTransacao();

        try {
            BeanUtil.copiarVO(form, pedido);
            pedido.setCliente(usuario.getLogin());

            long iDMaxPedido = serviceDAO.obterIdMaxPedido(tx) + 1;
            pedido.setId(iDMaxPedido);
            serviceDAO.inserirVO(tx, pedido);

            for (Iterator iter = lista_produtos.iterator(); iter.hasNext();) {
                ProdutoCategoriaVO element = (ProdutoCategoriaVO) iter.next();
                Produto_pedido produto_pedido = new Produto_pedido();
                produto_pedido.setPedido(iDMaxPedido);
                produto_pedido.setProduto(element.getProduto_id());
                produto_pedido.setQuantidade(element.getQtde_produto());
                serviceDAO.inserirVO(tx, produto_pedido);
            }

            serviceDAO.concluirTransacao(tx);

            return true;
        } catch (Exception e) {
            serviceDAO.abortarTransacao(tx);
            e.printStackTrace();
        }

        return false;
    }

    public void processarPreco(List retorno) throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();

        for (Iterator iter = retorno.iterator(); iter.hasNext();) {
            ProdutoCategoriaVO element = (ProdutoCategoriaVO) iter.next();
            Promocao promocao = new Promocao();
            promocao.setProduto(element.getProduto_id());
            promocao = (Promocao) Util.get0(serviceDAO.pesquisarVO(promocao));

            if (promocao != null) {
                element.setPercentual(promocao.getPercentual());
            }

            double precoDesc = element.getPreco() -
                ((element.getPreco() * element.getPercentual()) / 100);
            element.setPreco_total(precoDesc * element.getQtde_produto());
        }
    }
}
