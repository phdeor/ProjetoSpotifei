
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
    String sql = "SELECT id_playlist, nome_playlist FROM playlists WHERE id_usuario = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idUsuario);
    return stmt.executeQuery(); // Lembre-se: quem chamou deve fechar depois, se necessário
}
    

    
   
}