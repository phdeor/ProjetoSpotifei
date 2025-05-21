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
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        ResultSet res = playlistDAO.consultarPlaylists(Integer.parseInt(usuario.getId()));

        DefaultListModel<String> modelPlaylist = (DefaultListModel<String>) view.getList_playlist().getModel();
        modelPlaylist.clear();

        while (res.next()) {
            String nome = res.getString("nome_playlist");
            int id = res.getInt("id_playlist");
            modelPlaylist.addElement(id + " - " + nome);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar playlists!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void mostrarMusicasDaPlaylist(int idPlaylist) {
    try {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        ResultSet res = playlistDAO.buscarMusicasDaPlaylist(idPlaylist);

        DefaultListModel<String> musicasModel = new DefaultListModel<>();
        while (res.next()) {
            int id = res.getInt("id_musica");
            String nome = res.getString("nome");
            musicasModel.addElement(id + " - " + nome);
        }

        view.getList_musicas().setModel(musicasModel);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar músicas da playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
    
    public void carregarMusicasDaPlaylistSelecionada() {
    String selecionado = view.getList_playlist().getSelectedValue();
    
    if (selecionado == null) {
        JOptionPane.showMessageDialog(view, "Selecione uma playlist para carregar as músicas.");
        return;
    }

    try {
        int idPlaylist = Integer.parseInt(selecionado.split(" - ")[0]);

        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        ResultSet res = playlistDAO.buscarMusicasDaPlaylist(idPlaylist);

        DefaultListModel<String> musicasModel = new DefaultListModel<>();
        while (res.next()) {
            int id = res.getInt("id_musica");
            String nome = res.getString("nome");
            musicasModel.addElement(id + " - " + nome);
        }

        view.getList_musicas().setModel(musicasModel);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar músicas da playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "ID da playlist inválido.");
    }
}

    
    
    
    public void editarPlaylist() {
        String selecionado = view.getList_playlist().getSelectedValue();
        if (selecionado == null) {
            JOptionPane.showMessageDialog(view, "Selecione uma playlist para editar.");
            return;
        }

        int idPlaylist = Integer.parseInt(selecionado.split(" - ")[0]);
        String novoNome = JOptionPane.showInputDialog(view, "Digite o novo nome da playlist:");

        if (novoNome == null || novoNome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nome inválido.");
            return;
        }

        try {
            Connection conn = new Conexao().getConnection();
            PlaylistDAO playlistDAO = new PlaylistDAO(conn);
            playlistDAO.editarPlaylist(idPlaylist, novoNome.trim());

            JOptionPane.showMessageDialog(view, "Playlist editada com sucesso!");
            mostrarPlaylists();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Erro ao editar playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
       
    public void excluirPlaylist() {
    String selecionado = view.getList_playlist().getSelectedValue();
    if (selecionado == null) {
        JOptionPane.showMessageDialog(view, "Selecione uma playlist para excluir.");
        return;
    }

    int confirmacao = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja excluir esta playlist?", "Confirmação", JOptionPane.YES_NO_OPTION);
    if (confirmacao != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int idPlaylist = Integer.parseInt(selecionado.split(" - ")[0]);

        Connection conn = new Conexao().getConnection();
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        playlistDAO.excluirPlaylist(idPlaylist);

        JOptionPane.showMessageDialog(view, "Playlist excluída com sucesso!");
        mostrarPlaylists();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao excluir playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Formato inválido de ID da playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
 
    public void adicionarMusica() {
    String selecionado = view.getList_playlist().getSelectedValue();
    if (selecionado == null) {
        JOptionPane.showMessageDialog(view, "Selecione uma playlist para adicionar música.");
        return;
    }

    int idPlaylist = Integer.parseInt(selecionado.split(" - ")[0]);

    String idMusicaStr = JOptionPane.showInputDialog(view, "Digite o ID da música para adicionar:");
    if (idMusicaStr == null || idMusicaStr.trim().isEmpty()) return;

    try {
        int idMusica = Integer.parseInt(idMusicaStr);

        
        Connection conn = new Conexao().getConnection();
        
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        
        playlistDAO.adicionarMusicaNaPlaylist(idPlaylist, idMusica);

        JOptionPane.showMessageDialog(view, "Música adicionada com sucesso!");
        mostrarMusicasDaPlaylist(idPlaylist);  

       

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "ID inválido.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao adicionar música.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void removerMusica() {
    String playlistSelecionada = view.getList_playlist().getSelectedValue();
    String musicaSelecionada = view.getList_musicas().getSelectedValue();

    if (playlistSelecionada == null || musicaSelecionada == null) {
        JOptionPane.showMessageDialog(view, "Selecione uma playlist e uma música para remover.");
        return;
    }

    try {
        int idPlaylist = Integer.parseInt(playlistSelecionada.split(" - ")[0]);
        int idMusica = Integer.parseInt(musicaSelecionada.split(" - ")[0]);

        Connection conn = new Conexao().getConnection();
        PlaylistDAO playlistDAO = new PlaylistDAO(conn);
        playlistDAO.removerMusicaDaPlaylist(idPlaylist, idMusica);

        JOptionPane.showMessageDialog(view, "Música removida da playlist com sucesso!");
        mostrarMusicasDaPlaylist(idPlaylist);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Erro ao interpretar IDs. Verifique o formato.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao remover música da playlist.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    
    
    
    
}