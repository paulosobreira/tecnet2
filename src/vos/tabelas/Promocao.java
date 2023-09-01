package vos.tabelas;

/***Gerador de VO Sobreira PROMOCAO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Promocao implements java.io.Serializable {
  private long produto; 
  private int percentual;

  public Promocao() {
  }

  public Promocao(long produto,
		int percentual) {
		this.produto = produto;
		this.percentual = percentual;
  }

  public long getProduto() {
    return produto;
  }

  public void setProduto(long PRODUTO) {
    produto = PRODUTO;
  }

  public int getPercentual() {
    return percentual;
  }

  public void setPercentual(int PERCENTUAL) {
    percentual = PERCENTUAL;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Promocao[");
		buffer.append("produto =").append(produto);
		buffer.append("percentual =").append(percentual);
		buffer.append("]");
		return buffer.toString();
	}
}
