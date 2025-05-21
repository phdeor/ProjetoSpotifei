
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO {
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void criarPlaylist(String nome, int usuarioId) throws SQLException {
        String sql = "INSERT INTO playlists (nome_playlist, id_usuario) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setInt(2, usuarioId);
        stmt.executeUpdate();
        stmt.close();
    }
    
    public ResultSet consultarPlaylists(int idUsuario) throws SQLException {
    String sql = "SELECT id AS id_playlist, nome_playlist FROM playlists WHERE id_usuario = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idUsuario);
    return stmt.executeQuery(); 
}
    
    public void editarPlaylist(int idPlaylist, String novoNome) throws SQLException {
    String sql = "UPDATE playlists SET nome_playlist = ? WHERE id = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, novoNome);
    stmt.setInt(2, idPlaylist);
    stmt.executeUpdate();
    stmt.close();
}
    public ResultSet buscarMusicasDaPlaylist(int idPlaylist) throws SQLException {
    String sql = """
        SELECT m.id_musica, m.nome
        FROM musicas m
        INNER JOIN musicas_playlists mp ON m.id_musica = mp.id_musica
        WHERE mp.id_playlist = ?
    """;
    
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idPlaylist);
    return stmt.executeQuery();
}
    
    public void excluirPlaylist(int idPlaylist) throws SQLException {
    
    String sqlExcluirMusicas = "DELETE FROM musicas_playlists WHERE id_playlist = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sqlExcluirMusicas)) {
        stmt.setInt(1, idPlaylist);
        stmt.executeUpdate();
    }

    
    String sqlExcluirPlaylist = "DELETE FROM playlists WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sqlExcluirPlaylist)) {
        stmt.setInt(1, idPlaylist);
        stmt.executeUpdate();
    }
}
    
    public void adicionarMusicaNaPlaylist(int idPlaylist, int idMusica) throws SQLException {
    String sql = "INSERT INTO musicas_playlists (id_playlist, id_musica) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPlaylist);
        stmt.setInt(2, idMusica);
        stmt.executeUpdate();
    }
}
    
    public void removerMusicaDaPlaylist(int idPlaylist, int idMusica) throws SQLException {
    String sql = "DELETE FROM musicas_playlists WHERE id_playlist = ? AND id_musica = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idPlaylist);
        stmt.setInt(2, idMusica);
        stmt.executeUpdate();
    }
}



    
   
}