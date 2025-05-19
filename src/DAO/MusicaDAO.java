
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.Musica;
import java.sql.ResultSet;

public class MusicaDAO {
     private Connection conn;

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }
    
    
    
    public ResultSet consultarMusica(Musica musica) throws SQLException {
    String sql = "SELECT * FROM musica WHERE nome ILIKE ? OR genero ILIKE ?";
    PreparedStatement statement = conn.prepareStatement(sql);

    String nomeBusca = "%" + musica.getNome() + "%";
    String generoBusca = "%" + musica.getGenero() + "%";

    statement.setString(1, nomeBusca);
    statement.setString(2, generoBusca);

    return statement.executeQuery();
    }
    
    public int buscarIdPorNome(String nome) throws SQLException {
    String sql = "SELECT id_musica FROM musica WHERE nome = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        return rs.getInt("id_musica");
    }

    return -1; 
}
    
    public ResultSet buscarMusicaPorNomeEGenero(String nome, String genero) throws SQLException {
    String sql = "SELECT * FROM musica WHERE nome = ? AND genero = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    stmt.setString(2, genero);
    return stmt.executeQuery();
}
    
}
