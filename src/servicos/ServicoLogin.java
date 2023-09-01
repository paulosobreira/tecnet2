package servicos;

import struts.CadastrarActionForm;
import struts.LoginActionForm;

import util.BeanUtil;
import util.Util;

import vos.tabelas.Role;
import vos.tabelas.Usuario;

import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;


/**
 * @author Paulo Sobreira
 * Criado Em 13:12:22
 */
public class ServicoLogin {
    public Usuario login(LoginActionForm form) {
        Usuario usuario = new Usuario();
        usuario.setLogin(form.getLogin());

        try {
            usuario.setSenha(Util.md5(form.getPassword()));

            ServiceDAO serviceDAO = new ServiceDAO();
            usuario = (Usuario) Util.get0(serviceDAO.pesquisarVO(usuario));
        } catch (Exception e) {
            e.printStackTrace();
            usuario = null;
        }

        return usuario;
    }

    public boolean cadastrar(CadastrarActionForm form)
        throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();
        Object tx = serviceDAO.iniciarTransacao();

        try {
            Usuario usuario = new Usuario();
            BeanUtil.copiarVO(form, usuario);
            usuario.setSenha(Util.md5(usuario.getSenha()));
            serviceDAO.inserirVO(tx, usuario);

            Role role = new Role();
            role.setLogin(usuario.getLogin());
            role.setRole("usuario");
            serviceDAO.inserirVO(tx, role);
            serviceDAO.concluirTransacao(tx);

            return true;
        } catch (Exception e) {
            serviceDAO.abortarTransacao(tx);
            e.printStackTrace();
        }

        return false;
    }

    public boolean verificaAdministrador(Usuario usuario)
        throws Exception {
        ServiceDAO serviceDAO = new ServiceDAO();
        Role role = new Role();
        role.setLogin(usuario.getLogin());
        role = (Role) Util.get0(serviceDAO.pesquisarVO(role));

        if ((role != null) && (!"usuario".equals(role.getRole()))) {
            return true;
        }

        return false;
    }
}
