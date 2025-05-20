package Controller;

import DAO.Conexao;
import DAO.MusicaDAO;
import DAO.PlaylistDAO;
import Model.Usuario;
import View.TelaPlaylist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class PlaylistController {
    private TelaPlaylist view;
    private Usuario usuario;

    public PlaylistController(TelaPlaylist view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
    }

    public void criarPlaylist() {
    String nome = JOptionPane.showInputDialog(view, "Digite o nome da nova playlist:");

    if (nome == null || nome.trim().isEmpty()) {
        JOptionPane.showMessageDialog(view, "Nome da playlist inválido.");
        return;
    }

    try {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        playlistDAO.criarPlaylist(nome.trim(), Integer.parseInt(usuario.getId()));

        JOptionPane.showMessageDialog(view, "Playlist criada com sucesso!");
        mostrarPlaylists();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao criar playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void mostrarPlaylists() {
    try {
        // Cria conexão com o banco
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        // Cria DAO e busca playlists do usuário
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        ResultSet res = playlistDAO.consultarPlaylists(Integer.parseInt(usuario.getId()));

        // Cria modelo da lista
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        while (res.next()) {
            String nome = res.getString("nome_playlist");
            int id = res.getInt("id_playlist");
            listaModel.addElement(id + " - " + nome); // Ex: "3 - Sertanejo"
        }

        // Seta o modelo na JList
        view.getList_playlist().setModel(listaModel);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar playlists!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
}