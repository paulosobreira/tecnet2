package vos.hibernate;

// Generated 16/05/2007 16:47:40 by Hibernate Tools 3.2.0.beta8

/**
 * Promocao generated by hbm2java
 */
public class Promocao implements java.io.Serializable {

	// Fields    

	private long produto;

	private Produto produto_1;

	private int percentual;

	// Constructors

	/** default constructor */
	public Promocao() {
	}

	/** full constructor */
	public Promocao(long produto, Produto produto_1, int percentual) {
		this.produto = produto;
		this.produto_1 = produto_1;
		this.percentual = percentual;
	}

	// Property accessors
	public long getProduto() {
		return this.produto;
	}

	public void setProduto(long produto) {
		this.produto = produto;
	}

	public Produto getProduto_1() {
		return this.produto_1;
	}

	public void setProduto_1(Produto produto_1) {
		this.produto_1 = produto_1;
	}

	public int getPercentual() {
		return this.percentual;
	}

	public void setPercentual(int percentual) {
		this.percentual = percentual;
	}

}
