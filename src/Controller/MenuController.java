package Controller;

import DAO.Conexao;
import DAO.CurtidaDAO;
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
import Model.Usuario;

   

public class MenuController {
    private TelaMenu view;
    private String usuarioLogado;
    private Usuario usuario;
    
    public MenuController(TelaMenu view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
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

                
                if (listaModel.size() >= 10) {
                    listaModel.remove(listaModel.size() - 1); 
                }

                
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
    
    public void curtirMusica() {
    String linhaSelecionada = view.getList_historico().getSelectedValue();

    if (linhaSelecionada == null || linhaSelecionada.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Selecione uma música para curtir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Extrai o nome da música (assumindo o formato "🎵 Nome | Gênero")
        String[] partes = linhaSelecionada.split("\\|");
        String nomeMusica = partes[0].replace("🎵", "").trim();

        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        MusicaDAO musicaDAO = new MusicaDAO(conn);
        ResultSet res = musicaDAO.consultarMusica(new Musica(nomeMusica, ""));

        if (res.next()) {
            int musicaId = res.getInt("id_musica");

            
            int usuarioId = Integer.parseInt(usuario.getId());  

            CurtidaDAO curtidaDAO = new CurtidaDAO(conn);
            curtidaDAO.curtirOuDescurtir(usuarioId, musicaId);

            JOptionPane.showMessageDialog(view, "Curtida atualizada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(view, "Música não encontrada no banco.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao curtir música.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    


}

    