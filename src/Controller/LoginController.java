/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import View.TelaLogin;
import View.TelaCadastro;
import View.TelaMenu;


public class LoginController {

    public TelaLogin loginView;

    public LoginController(TelaLogin view) {
        this.loginView = view;

        // Configurando o botão
        this.loginView.getBt_Cadastro().addActionListener(e -> abrirTelaCadastro());
        this.loginView.getBt_Menu().addActionListener(e -> abrirTelaMenu());
    }

    public void abrirTelaCadastro() {
        loginView.dispose(); // Fecha a tela de login
        TelaCadastro cadastroView = new TelaCadastro();
        new CadastroController(cadastroView);
        cadastroView.setVisible(true);
    }
    
     public void abrirTelaMenu() {
        loginView.dispose(); // Fecha a tela de login
        TelaMenu menuView = new TelaMenu();
        menuView.setVisible(true);
    }
}
    

