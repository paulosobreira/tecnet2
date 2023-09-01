package vos.tabelas;

/***Gerador de VO Sobreira PRODUTO_PEDIDO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Produto_pedido implements java.io.Serializable {
  private long pedido;
  private long produto; 
  private int quantidade;

  public Produto_pedido() {
  }

  public Produto_pedido(long pedido,
		long produto,
		int quantidade) {
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
  }

  public long getPedido() {
    return pedido;
  }

  public void setPedido(long PEDIDO) {
    pedido = PEDIDO;
  }

  public long getProduto() {
    return produto;
  }

  public void setProduto(long PRODUTO) {
    produto = PRODUTO;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int QUANTIDADE) {
    quantidade = QUANTIDADE;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Produto_pedido[");
		buffer.append("pedido =").append(pedido);
		buffer.append("produto =").append(produto);
		buffer.append("quantidade =").append(quantidade);
		buffer.append("]");
		return buffer.toString();
	}
}
