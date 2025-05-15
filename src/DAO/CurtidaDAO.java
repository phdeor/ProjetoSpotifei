
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;    
import java.sql.ResultSet;

public class CurtidaDAO {
    
    
    private Connection conn;

    public CurtidaDAO(Connection conn) {
        this.conn = conn;
    }

    public void curtirOuDescurtir(int usuarioId, int musicaId) throws SQLException {
    // Verifica se a curtida já existe
    String selectSql = "SELECT 1 FROM curtidas WHERE usuario_id = ? AND musica_id = ?";
    PreparedStatement selectStmt = conn.prepareStatement(selectSql);
    selectStmt.setInt(1, usuarioId);
    selectStmt.setInt(2, musicaId);
    ResultSet res = selectStmt.executeQuery();

    if (res.next()) {
        // Já curtiu: vamos descurtir
        String deleteSql = "DELETE FROM curtidas WHERE usuario_id = ? AND musica_id = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
        deleteStmt.setInt(1, usuarioId);
        deleteStmt.setInt(2, musicaId);
        deleteStmt.executeUpdate();
    } else {
        // Ainda não curtiu: vamos curtir
        String insertSql = "INSERT INTO curtidas (usuario_id, musica_id) VALUES (?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setInt(1, usuarioId);
        insertStmt.setInt(2, musicaId);
        insertStmt.executeUpdate();
    }
}
}

    

