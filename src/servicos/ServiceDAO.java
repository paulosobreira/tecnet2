package servicos;

import struts.PesquisarProdutoActionForm;

import util.MasterDAO;
import util.Util;

import vos.PedidosVO;
import vos.ProdutoCategoriaVO;
import vos.PromocaoProdutoVO;

import vos.tabelas.Pedido;
import vos.tabelas.Promocao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Paulo Sobreira
 * Criado Em 18:33:47
 */
public class ServiceDAO extends MasterDAO {
    public ServiceDAO(String banco) throws Exception {
        super(banco);
    }

    public ServiceDAO() throws Exception {
        super("");
    }

    public void inserirVO(Object VO) throws Exception {
        Connection connection = getConexao();

        try {
            super.inserirVO(connection, VO);
        } finally {
            connection.close();
        }
    }

    public int excluirVO(Object VO) throws Exception {
        Connection connection = getConexao();

        try {
            return super.excluirVO(connection, VO);
        } finally {
            connection.close();
        }
    }

    public void inserirVO(Object VO, String[] exc) throws Exception {
        Connection connection = getConexao();

        try {
            super.inserirVO(connection, VO, exc);
        } finally {
            connection.close();
        }
    }

    public List pesquisarVO(Object vo) throws Exception {
        Connection connection = getConexao();

        try {
            return super.pesquisarVO(connection, vo);
        } finally {
            connection.close();
        }
    }

    public List pesquisarProdutoCategorias() throws Exception {
        Connection connection = getConexao();

        try {
            String sql = "select produto.nome, categoria.nome from" +
                " produto,categoria,produto_categoria where " +
                " categoria.id =produto_categoria.categoria and " +
                " produto.id =produto_categoria.produto  ";
            ResultSet set = connection.createStatement().executeQuery(sql);
            List retorno = new ArrayList();

            while (set.next()) {
                ProdutoCategoriaVO produtoCategoriaVO = new ProdutoCategoriaVO();
                produtoCategoriaVO.setCategoria(set.getString(1));
                produtoCategoriaVO.setProduto(set.getString(2));
                retorno.add(produtoCategoriaVO);
            }

            return retorno;
        } finally {
            connection.close();
        }
    }

    public List pesquisarProdutoCategorias(PesquisarProdutoActionForm form)
        throws Exception {
        Connection connection = getConexao();

        try {
            String sql = "select produto.nome,produto.descricao," +
                "  categoria.nome,cor,peso,preco,produto.id " +
                " from produto,categoria,produto_categoria where " +
                " categoria.id =produto_categoria.categoria and " +
                " produto.id =produto_categoria.produto  ";

            if (!Util.isNullOrEmpty(form.getCor())) {
                sql += (" and cor like " + aspasLike(form.getCor()));
            }

            if (!Util.isNullOrEmpty(form.getNome())) {
                sql += (" and produto.nome like " + aspasLike(form.getNome()));
            }

            if (!Util.isNullOrEmpty(form.getDescricao())) {
                sql += (" and produto.descricao like " +
                aspasLike(form.getDescricao()));
            }

            if (form.getPeso() != 0) {
                sql += (" and peso = " + form.getPeso());
            }

            if (form.getPreco() != 0) {
                sql += (" and preco = " + form.getPreco());
            }

            if (form.getCategoria_id() != 0) {
                sql += (" and categoria.id = " + form.getCategoria_id());
            }

            ResultSet set = connection.createStatement().executeQuery(sql);
            List retorno = new ArrayList();

            while (set.next()) {
                ProdutoCategoriaVO produtoCategoriaVO = new ProdutoCategoriaVO();
                produtoCategoriaVO.setNome(set.getString(1));
                produtoCategoriaVO.setProduto(set.getString(2));
                produtoCategoriaVO.setDescricao(set.getString(3));
                produtoCategoriaVO.setCor(set.getString(4));
                produtoCategoriaVO.setPeso(set.getDouble(5));
                produtoCategoriaVO.setPreco(set.getDouble(6));
                produtoCategoriaVO.setProduto_id(set.getLong(7));
                retorno.add(produtoCategoriaVO);
            }

            return retorno;
        } finally {
            connection.close();
        }
    }

    public List pesquisarProdutoPedido(long prodId)
        throws Exception {
        Connection connection = getConexao();

        try {
            String sql = "select produto.nome,produto.descricao," +
                "  categoria.nome,cor,peso,preco,produto.id,produto_pedido.quantidade " +
                " from produto,categoria,produto_categoria,produto_pedido " +
                "where " +
                " categoria.id =produto_categoria.categoria and " +
                " produto.id =produto_categoria.produto  and " +
                " produto.id = produto_pedido.produto and " +
                " produto_pedido.pedido = " + prodId;

            ResultSet set = connection.createStatement().executeQuery(sql);
            List retorno = new ArrayList();

            while (set.next()) {
                ProdutoCategoriaVO produtoCategoriaVO = new ProdutoCategoriaVO();
                produtoCategoriaVO.setNome(set.getString(1));
                produtoCategoriaVO.setProduto(set.getString(2));
                produtoCategoriaVO.setDescricao(set.getString(3));
                produtoCategoriaVO.setCor(set.getString(4));
                produtoCategoriaVO.setPeso(set.getDouble(5));
                produtoCategoriaVO.setPreco(set.getDouble(6));
                produtoCategoriaVO.setProduto_id(set.getLong(7));
                produtoCategoriaVO.setQtde_produto(set.getInt(8));
                retorno.add(produtoCategoriaVO);
            }

            return retorno;
        } finally {
            connection.close();
        }
    }

    public long obterIdMaxPedido(Object tx) throws Exception {
        Connection connection = validarTransacao(tx);
        String sql = "select max(id) from pedido ";
        ResultSet set = connection.createStatement().executeQuery(sql);

        if (set.next()) {
            return set.getLong(1);
        }

        return 0;
    }

    public void inserirVO(Object tx, Object vo) throws SQLException, Exception {
        super.inserirVO(validarTransacao(tx), vo);
    }

    public List pesquisaPromocoes() throws Exception {
        Connection connection = getConexao();

        try {
            String where = " and id = produto ";

            String tabelas = " promocao,produto ";

            return pesquisarVO(connection, new PromocaoProdutoVO(), where,
                tabelas);
        } finally {
            liberaConeccao(connection);
        }
    }

    public List pesquisarPedidos() throws Exception {
        Connection connection = getConexao();

        try {
            String where = " and cliente = login ";

            String tabelas = " pedido,usuario ";

            return pesquisarVO(connection, new PedidosVO(), where, tabelas,
                new String[] { "listaProdutos" });
        } finally {
            liberaConeccao(connection);
        }
    }
}
