
package Controller;
import View.TelaLogin;
import View.TelaCadastro;
import View.TelaMenu;


public class CadastroController {
    
    public TelaCadastro cadastroView;

    public CadastroController(TelaCadastro view) {
        this.cadastroView = view;

        
        this.cadastroView.getBt_Login().addActionListener(e -> abrirTelaLogin());
        
    }

    public void abrirTelaLogin() {
        cadastroView.dispose();
        TelaLogin loginView = new TelaLogin();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
    
    
}
