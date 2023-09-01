package servicos;

import struts.CadastrarCategoriaActionForm;
import struts.CadastrarProdutoActionForm;
import struts.CadastrarProdutoCategoriaActionForm;
import struts.CadastrarPromocaoActionForm;

import util.BeanUtil;
import util.Util;

import vos.tabelas.Categoria;
import vos.tabelas.Produto;
import vos.tabelas.Produto_categoria;
import vos.tabelas.Promocao;

import java.lang.reflect.InvocationTargetException;

import java.util.List;


/**
 * @author Paulo Sobreira
 * Criado Em 11:57:40
 */
public class ServicoCadastro {
    public List cadastrarCategoria(CadastrarCategoriaActionForm form) {
        Categoria categoria = new Categoria();

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            BeanUtil.copiarVO(form, categoria);
            serviceDAO.inserirVO(categoria, new String[] { "id" });

            return serviceDAO.pesquisarVO(new Categoria());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List cadastrarProduto(CadastrarProdutoActionForm form) {
        Produto produto = new Produto();

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            BeanUtil.copiarVO(form, produto);
            serviceDAO.inserirVO(produto, new String[] { "id" });

            return serviceDAO.pesquisarVO(new Produto());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List cadastrarProdutoCategoria(
        CadastrarProdutoCategoriaActionForm form) {
        Produto_categoria produto_categoria = new Produto_categoria();

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            BeanUtil.copiarVO(form, produto_categoria);
            serviceDAO.inserirVO(produto_categoria, new String[] { "id" });

            return serviceDAO.pesquisarProdutoCategorias();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List cadastrarPromocao(CadastrarPromocaoActionForm form)
        throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();
        Promocao promocao = new Promocao();
        BeanUtil.copiarVO(form, promocao);
        serviceDAO.inserirVO(promocao);

        return serviceDAO.pesquisaPromocoes();
    }

    public List removerPromocao(String parameter) throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();

        int prodid = Util.zeroOuInt(parameter);

        if (prodid != 0) {
            Promocao promocao = new Promocao();
            promocao.setProduto(prodid);
            serviceDAO.excluirVO(promocao);
        }

        return serviceDAO.pesquisaPromocoes();
    }
}
