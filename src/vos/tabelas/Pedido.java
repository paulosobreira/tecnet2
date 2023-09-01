package vos.tabelas;

/***Gerador de VO Sobreira PEDIDO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Pedido implements java.io.Serializable {
  private long id;
  private java.lang.String cliente;
  private java.lang.String rua;
  private int numero;
  private java.lang.String cidade;
  private java.lang.String estado;
  private java.lang.String cep;

  public Pedido() {
  }

  public Pedido(long id,
		java.lang.String cliente,
		java.lang.String rua,
		int numero,
		java.lang.String cidade,
		java.lang.String estado,
		java.lang.String cep) {
		this.id = id;
		this.cliente = cliente;
		this.rua = rua;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
  }

  public long getId() {
    return id;
  }

  public void setId(long ID) {
    id = ID;
  }

  public java.lang.String getCliente() {
    return cliente;
  }

  public void setCliente(java.lang.String CLIENTE) {
    cliente = CLIENTE;
  }

  public java.lang.String getRua() {
    return rua;
  }

  public void setRua(java.lang.String RUA) {
    rua = RUA;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int NUMERO) {
    numero = NUMERO;
  }

  public java.lang.String getCidade() {
    return cidade;
  }

  public void setCidade(java.lang.String CIDADE) {
    cidade = CIDADE;
  }

  public java.lang.String getEstado() {
    return estado;
  }

  public void setEstado(java.lang.String ESTADO) {
    estado = ESTADO;
  }

  public java.lang.String getCep() {
    return cep;
  }

  public void setCep(java.lang.String CEP) {
    cep = CEP;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Pedido[");
		buffer.append("id =").append(id);
		buffer.append("cliente =").append(cliente);
		buffer.append("rua =").append(rua);
		buffer.append("numero =").append(numero);
		buffer.append("cidade =").append(cidade);
		buffer.append("estado =").append(estado);
		buffer.append("cep =").append(cep);
		buffer.append("]");
		return buffer.toString();
	}
}
