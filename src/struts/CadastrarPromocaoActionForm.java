package struts;

import org.apache.struts.validator.ValidatorForm;

import vos.tabelas.Produto;
import vos.tabelas.Produto_categoria;
import vos.tabelas.Promocao;


/***Gerador de VO Sobreira USUARIO
     Ver: 1.9 -  Data: 31/03/2007 21:08:15 */
public class CadastrarPromocaoActionForm extends ValidatorForm
    implements java.io.Serializable {
    private Promocao promocao = new Promocao();

    public int getPercentual() {
        return promocao.getPercentual();
    }

    public long getProduto() {
        return promocao.getProduto();
    }

    public void setPercentual(int PERCENTUAL) {
        promocao.setPercentual(PERCENTUAL);
    }

    public void setProduto(long PRODUTO) {
        promocao.setProduto(PRODUTO);
    }
    
    
}
