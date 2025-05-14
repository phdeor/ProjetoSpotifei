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
        
        DefaultListModel<String> listaModel = (DefaultListModel<String>) view.getList_historico().getModel();

        // Caso a lista já tenha músicas, não recriar o modelo
        if (listaModel == null) {
            listaModel = new DefaultListModel<>();
        }

        if (res.next()) {
            StringBuilder resultado = new StringBuilder("Músicas encontradas:\n");
            do {
                String nome = res.getString("nome");
                String genero = res.getString("genero");
                
                resultado.append("Nome: ").append(nome)
                         .append(" | Gênero: ").append(genero)
                         .append("\n");

                // Limitar a 10 itens
                if (listaModel.size() >= 10) {
                    listaModel.remove(listaModel.size() - 1); // Remove o item mais antigo
                }

                // Adicionar a nova música à lista
                listaModel.add(0,"🎵 " + nome + " | " + genero);
                
                // Salvar no histórico
                HistoricoDAO historicoDAO = new HistoricoDAO(conn);
                historicoDAO.salvar(new Historico(nome, genero));
            } while (res.next());

            // Atualiza a JList com o novo modelo
            view.getList_historico().setModel(listaModel);

            JOptionPane.showMessageDialog(view, resultado.toString(), "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma música encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view, "Erro ao buscar músicas!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void carregarHistorico() {
    Conexao conexao = new Conexao();
    try {
        Connection conn = conexao.getConnection();
        HistoricoDAO historicoDAO = new HistoricoDAO(conn);

        DefaultListModel<String> listaModel = new DefaultListModel<>();
        historicoDAO.consultarHistorico(listaModel);

        view.getList_historico().setModel(listaModel);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view, "Erro ao carregar histórico!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
}


