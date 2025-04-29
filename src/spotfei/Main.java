
package spotfei;
import View.TelaLogin;
import Controller.LoginController;

public class Main {


    public static void main(String[] args) {
        TelaLogin loginView = new TelaLogin();
        LoginController controller = new LoginController(loginView);
        
        loginView.setVisible(true);
    }
    
    
}
