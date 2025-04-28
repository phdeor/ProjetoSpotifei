/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import View.TelaLogin;
import View.TelaCadastro;

public class LoginController {

    private TelaLogin loginView;

    public LoginController(TelaLogin view) {
        this.loginView = view;

        // Configurando o botão
        this.loginView.getBt_Cadastro().addActionListener(e -> abrirTelaCadastro());
    }

    private void abrirTelaCadastro() {
        loginView.dispose(); // Fecha a tela de login
        TelaCadastro cadastroView = new TelaCadastro();
        cadastroView.setVisible(true);
    }
}
    

