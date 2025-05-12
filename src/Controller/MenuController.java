package Controller;

import DAO.Conexao;
import DAO.HistoricoDAO;
import DAO.MusicaDAO;
import Model.Historico;
import Model.Musica;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import View.TelaMenu;
import javax.swing.DefaultListModel;



    


public class MenuController {
    private TelaMenu view;

    public MenuController(TelaMenu view) {
        this.view = view;
    }

    public void buscarMusica() {
        String termoBusca = view.getTxt_buscar().getText(); 
        Musica musica = new Musica(termoBusca, termoBusca);

        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ResultSet res = dao.consultarMusica(musica);
            
            DefaultListModel<String> listaModel = new DefaultListModel<>();

            if (res.next()) {
                StringBuilder resultado = new StringBuilder("Músicas encontradas:\n");
                do {
                    
                    String nome = res.getString("nome");
                    String genero = res.getString("genero");
                
                    resultado.append("Nome: ").append(res.getString("nome"))
                             .append(" | Gênero: ").append(res.getString("genero"))
                             .append("\n");
                    
                    listaModel.addElement("🎵 " + nome + " | " + genero);
                     
                    HistoricoDAO historicoDAO = new HistoricoDAO(conn);
                    historicoDAO.salvar(new Historico(nome, genero));
                    
                } while (res.next());
                
                view.getList_historico().setModel(listaModel);

                JOptionPane.showMessageDialog(view, resultado.toString(), "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Nenhuma música encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar músicas!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
