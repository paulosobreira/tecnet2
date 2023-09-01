package vos.tabelas;

/***Gerador de VO Sobreira ROLE
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class Role implements java.io.Serializable {
  private java.lang.String login;
  private java.lang.String role;

  public Role() {
  }

  public Role(java.lang.String login,
		java.lang.String role) {
		this.login = login;
		this.role = role;
  }

  public java.lang.String getLogin() {
    return login;
  }

  public void setLogin(java.lang.String LOGIN) {
    login = LOGIN;
  }

  public java.lang.String getRole() {
    return role;
  }

  public void setRole(java.lang.String ROLE) {
    role = ROLE;
  }

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Role[");
		buffer.append("login =").append(login);
		buffer.append("role =").append(role);
		buffer.append("]");
		return buffer.toString();
	}
}
