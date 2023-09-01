package struts;

import org.apache.struts.validator.ValidatorForm;

import vos.tabelas.Produto;
import vos.tabelas.Produto_categoria;


/***Gerador de VO Sobreira USUARIO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class CadastrarProdutoCategoriaActionForm extends ValidatorForm
    implements java.io.Serializable {
    private Produto_categoria produto_categoria = new Produto_categoria();

    public boolean equals(Object obj) {
        return produto_categoria.equals(obj);
    }

    public long getCategoria() {
        return produto_categoria.getCategoria();
    }

    public long getProduto() {
        return produto_categoria.getProduto();
    }

    public int hashCode() {
        return produto_categoria.hashCode();
    }

    public void setCategoria(long CATEGORIA) {
        produto_categoria.setCategoria(CATEGORIA);
    }

    public void setProduto(long PRODUTO) {
        produto_categoria.setProduto(PRODUTO);
    }

    public String toString() {
        return produto_categoria.toString();
    }
}
