package vos;

import java.io.Serializable;

public class ProdutoCategoriaVO implements Serializable{
    private long produto_id;
    private int qtde_produto;
    private String produto;
    private String Categoria;
    private java.lang.String nome;
    private java.lang.String descricao;
    private java.lang.String cor;
    private double peso;
    private double preco;
    private double preco_total;
    private long percentual;
    
    
    
    public long getPercentual() {
        return percentual;
    }

    public void setPercentual(long percentual) {
        this.percentual = percentual;
    }

    public double getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(double preco_total) {
        this.preco_total = preco_total;
    }

    public boolean equals(Object obj) {
        ProdutoCategoriaVO categoriaVO = (ProdutoCategoriaVO) obj;
        return categoriaVO.getProduto_id()==getProduto_id();
    }
    
    public int getQtde_produto() {
        return qtde_produto;
    }
    public void setQtde_produto(int qtde_produto) {
        this.qtde_produto = qtde_produto;
    }
    public long getProduto_id() {
        return produto_id;
    }
    public void setProduto_id(long produto_id) {
        this.produto_id = produto_id;
    }
    public java.lang.String getCor() {
        return cor;
    }
    public void setCor(java.lang.String cor) {
        this.cor = cor;
    }
    public java.lang.String getDescricao() {
        return descricao;
    }
    public void setDescricao(java.lang.String descricao) {
        this.descricao = descricao;
    }
    public java.lang.String getNome() {
        return nome;
    }
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getCategoria() {
        return Categoria;
    }
    public void setCategoria(String categoria) {
        Categoria = categoria;
    }
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    

}
