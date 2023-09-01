package vos.hibernate;

// Generated 16/05/2007 16:47:40 by Hibernate Tools 3.2.0.beta8

/**
 * ProdutoPedido generated by hbm2java
 */
public class ProdutoPedido implements java.io.Serializable {

	// Fields    

	private ProdutoPedidoId id;

	private Produto produto;

	private Pedido pedido;

	private int quantidade;

	// Constructors

	/** default constructor */
	public ProdutoPedido() {
	}

	/** full constructor */
	public ProdutoPedido(ProdutoPedidoId id, Produto produto, Pedido pedido,
			int quantidade) {
		this.id = id;
		this.produto = produto;
		this.pedido = pedido;
		this.quantidade = quantidade;
	}

	// Property accessors
	public ProdutoPedidoId getId() {
		return this.id;
	}

	public void setId(ProdutoPedidoId id) {
		this.id = id;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
