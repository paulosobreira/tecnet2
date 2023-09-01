package struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

/***Gerador de VO Sobreira USUARIO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class CadastrarActionForm extends ValidatorForm implements java.io.Serializable {
  private java.lang.String login;
  private java.lang.String senha;
  private java.lang.String nome;
  private java.lang.String cartao;
  private java.lang.String email;
  private java.lang.String telefone;

  public CadastrarActionForm() {
  }

  public CadastrarActionForm(java.lang.String login,
		java.lang.String senha,
		java.lang.String nome,
		java.lang.String cartao,
		java.lang.String email,
		java.lang.String telefone) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.cartao = cartao;
		this.email = email;
		this.telefone = telefone;
  }

  public java.lang.String getLogin() {
    return login;
  }

  public void setLogin(java.lang.String LOGIN) {
    login = LOGIN;
  }

  public java.lang.String getSenha() {
    return senha;
  }

  public void setSenha(java.lang.String SENHA) {
    senha = SENHA;
  }

  public java.lang.String getNome() {
    return nome;
  }

  public void setNome(java.lang.String NOME) {
    nome = NOME;
  }

  public java.lang.String getCartao() {
    return cartao;
  }

  public void setCartao(java.lang.String CARTAO) {
    cartao = CARTAO;
  }

  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String EMAIL) {
    email = EMAIL;
  }

  public java.lang.String getTelefone() {
    return telefone;
  }

  public void setTelefone(java.lang.String TELEFONE) {
    telefone = TELEFONE;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Usuario[");
		buffer.append("login =").append(login);
		buffer.append("senha =").append(senha);
		buffer.append("nome =").append(nome);
		buffer.append("cartao =").append(cartao);
		buffer.append("email =").append(email);
		buffer.append("telefone =").append(telefone);
		buffer.append("]");
		return buffer.toString();
	}
}
