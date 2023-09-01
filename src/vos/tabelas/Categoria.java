package vos.tabelas;

/***Gerador de VO Sobreira CATEGORIA
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Categoria implements java.io.Serializable {
  private long id;
  private java.lang.String nome;
  private java.lang.String descricao;

  public Categoria() {
  }

  public Categoria(long id,
		java.lang.String nome,
		java.lang.String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
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

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Categoria[");
		buffer.append("id =").append(id);
		buffer.append("nome =").append(nome);
		buffer.append("descricao =").append(descricao);
		buffer.append("]");
		return buffer.toString();
	}
}
