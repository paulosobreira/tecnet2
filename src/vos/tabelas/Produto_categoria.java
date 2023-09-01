package vos.tabelas;

/***Gerador de VO Sobreira PRODUTO_CATEGORIA
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Produto_categoria implements java.io.Serializable {
  private long produto;
  private long categoria;

  public Produto_categoria() {
  }

  public Produto_categoria(long produto,
		long categoria) {
		this.produto = produto;
		this.categoria = categoria;
  }

  public long getProduto() {
    return produto;
  }

  public void setProduto(long PRODUTO) {
    produto = PRODUTO;
  }

  public long getCategoria() {
    return categoria;
  }

  public void setCategoria(long CATEGORIA) {
    categoria = CATEGORIA;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Produto_categoria[");
		buffer.append("produto =").append(produto);
		buffer.append("categoria =").append(categoria);
		buffer.append("]");
		return buffer.toString();
	}
}
