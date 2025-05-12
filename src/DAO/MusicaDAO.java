
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
}
