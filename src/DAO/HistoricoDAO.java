
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.Historico;


public class HistoricoDAO {
     private Connection conn;

    public HistoricoDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Historico historico) throws SQLException {
        String sql = "INSERT INTO historico (nome, genero) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, historico.getNome());
        stmt.setString(2, historico.getGenero());
        stmt.executeUpdate();
    }
    
    
    
    

    
    
}
