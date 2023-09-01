package vos.tabelas;

/***Gerador de VO Sobreira PRODUTO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Produto implements java.io.Serializable {
  private long id;
  private java.lang.String nome;
  private java.lang.String descricao;
  private java.lang.String cor;
  private double peso;
  private double preco; 

  public Produto() { 
  }

  public Produto(long id,
		java.lang.String nome,
		java.lang.String descricao,
		java.lang.String cor,
		double peso,
		double preco) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.cor = cor;
		this.peso = peso;
		this.preco = preco;
  }

  public long getId() {
    return id;
  }

  public void setId(long ID) {
    id = ID;
  }

  public java.lang.String getNome() {
    return nome;
  }

  public void setNome(java.lang.String NOME) {
    nome = NOME;
  }

  public java.lang.String getDescricao() {
    return descricao;
  }

  public void setDescricao(java.lang.String DESCRICAO) {
    descricao = DESCRICAO;
  }

  public java.lang.String getCor() {
    return cor;
  }

  public void setCor(java.lang.String COR) {
    cor = COR;
  }

  public double getPeso() {
    return peso;
  }

  public void setPeso(double PESO) {
    peso = PESO;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double PRECO) {
    preco = PRECO;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Produto[");
		buffer.append("id =").append(id);
		buffer.append("nome =").append(nome);
		buffer.append("descricao =").append(descricao);
		buffer.append("cor =").append(cor);
		buffer.append("peso =").append(peso);
		buffer.append("preco =").append(preco);
		buffer.append("]");
		return buffer.toString();
	}
}
