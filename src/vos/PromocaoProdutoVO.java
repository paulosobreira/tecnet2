package vos;

import vos.tabelas.Produto;
import vos.tabelas.Promocao;


/**
 * @author Paulo Sobreira
 * Criado Em 16:24:05
 */
public class PromocaoProdutoVO {
    private Promocao promocao = new Promocao();
    private Produto produto = new Produto(); 

    public String getCor() {
        return produto.getCor();
    }

    public String getDescricao() {
        return produto.getDescricao();
    }

    public long getId() {
        return produto.getId();
    }

    public String getNome() {
        return produto.getNome();
    }

    public double getPeso() {
        return produto.getPeso();
    }

    public double getPreco() {
        return produto.getPreco();
    }

    public void setCor(String COR) {
        produto.setCor(COR);
    }

    public void setDescricao(String DESCRICAO) {
        produto.setDescricao(DESCRICAO);
    }

    public void setId(long ID) {
        produto.setId(ID);
    }

    public void setNome(String NOME) {
        produto.setNome(NOME);
    }

    public void setPeso(double PESO) {
        produto.setPeso(PESO);
    }

    public void setPreco(double PRECO) {
        produto.setPreco(PRECO);
    }

    public int getPercentual() {
        return promocao.getPercentual();
    }

    public long getProduto() {
        return promocao.getProduto();
    }

    public void setPercentual(int PERCENTUAL) {
        promocao.setPercentual(PERCENTUAL);
    }

    public void setProduto(long PRODUTO) {
        promocao.setProduto(PRODUTO);
    }
}
