package struts;

import org.apache.struts.validator.ValidatorForm;

import vos.tabelas.Pedido;

public class EfetuarPedidoActionForm extends ValidatorForm implements java.io.Serializable {
    private Pedido pedido = new Pedido();

    public boolean equals(Object obj) {
        return pedido.equals(obj);
    }

    public String getCep() {
        return pedido.getCep();
    }

    public String getCidade() {
        return pedido.getCidade();
    }

    public String getCliente() {
        return pedido.getCliente();
    }

    public String getEstado() {
        return pedido.getEstado();
    }

    public long getId() {
        return pedido.getId();
    }

    public int getNumero() {
        return pedido.getNumero();
    }

    public String getRua() {
        return pedido.getRua();
    }

    public int hashCode() {
        return pedido.hashCode();
    }

    public void setCep(String CEP) {
        pedido.setCep(CEP);
    }

    public void setCidade(String CIDADE) {
        pedido.setCidade(CIDADE);
    }

    public void setCliente(String CLIENTE) {
        pedido.setCliente(CLIENTE);
    }

    public void setEstado(String ESTADO) {
        pedido.setEstado(ESTADO);
    }

    public void setId(long ID) {
        pedido.setId(ID);
    }

    public void setNumero(int NUMERO) {
        pedido.setNumero(NUMERO);
    }

    public void setRua(String RUA) {
        pedido.setRua(RUA);
    }

    public String toString() {
        return pedido.toString();
    }
}
