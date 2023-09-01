package vos;

import vos.tabelas.Pedido;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Paulo Sobreira
 * Criado Em 21:16:23
 */
public class PedidosVO {
    private String nome;
    private Pedido pedido = new Pedido();
    private List listaProdutos = new ArrayList();

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

    public List getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
