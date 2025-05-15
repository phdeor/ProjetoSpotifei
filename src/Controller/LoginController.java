package Controller;


import DAO.UsuarioDAO;
import DAO.Conexao;
import Model.Usuario;
import View.TelaLogin;
import View.TelaMenu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LoginController {
    private TelaLogin view;

    public LoginController(TelaLogin view) {
        this.view = view;
    }

    public void login() {
        String usuario = view.getTxt_usuario_login().getText();
        String senha = view.getTxt_senha_login().getText();
        
        if (usuario.isEmpty() || senha.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Usuário e senha não podem estar vazios!", "Aviso", JOptionPane.ERROR_MESSAGE);
        return;
    }
        

        Usuario u = new Usuario(null, usuario, senha);
        Conexao conexao = new Conexao();

        try {
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(u);

            if (res.next()) {
                JOptionPane.showMessageDialog(view, "Login efetuado com sucesso!");
                
                TelaMenu menu = new TelaMenu();
                
                menu.setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro de conexão com o banco!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    

