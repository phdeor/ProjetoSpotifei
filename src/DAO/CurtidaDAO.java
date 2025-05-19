
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

    public void curtirOuDescurtir(int idUsuario, int idMusica) throws SQLException {
    String verificarSql = "SELECT * FROM curtida WHERE id_usuario = ? AND id_musica = ?";
    PreparedStatement stmtVerificar = conn.prepareStatement(verificarSql);
    stmtVerificar.setInt(1, idUsuario);
    stmtVerificar.setInt(2, idMusica);
    ResultSet res = stmtVerificar.executeQuery();

    if (res.next()) {
        // Já curtiu — então vamos remover (descurtir)
        String removerSql = "DELETE FROM curtida WHERE id_usuario = ? AND id_musica = ?";
        PreparedStatement stmtRemover = conn.prepareStatement(removerSql);
        stmtRemover.setInt(1, idUsuario);
        stmtRemover.setInt(2, idMusica);
        stmtRemover.executeUpdate();
    } else {
        // Ainda não curtiu — então vamos adicionar
        String inserirSql = "INSERT INTO curtida (id_usuario, id_musica) VALUES (?, ?)";
        PreparedStatement stmtInserir = conn.prepareStatement(inserirSql);
        stmtInserir.setInt(1, idUsuario);
        stmtInserir.setInt(2, idMusica);
        stmtInserir.executeUpdate();
    }
}
    
    public ResultSet consultarMusicasCurtidas(int idUsuario) throws SQLException {
    String sql = "SELECT m.nome, m.genero FROM musica m " +
                 "INNER JOIN curtida c ON m.id_musica = c.id_musica " +
                 "WHERE c.id_usuario = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idUsuario);
    return stmt.executeQuery();
}
    public boolean verificarCurtida(int idUsuario, int idMusica) throws SQLException {
    String sql = "SELECT 1 FROM curtida WHERE id_usuario = ? AND id_musica = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idUsuario);
    stmt.setInt(2, idMusica);
    ResultSet rs = stmt.executeQuery();
    boolean existe = rs.next();
    rs.close();
    stmt.close();
    return existe;
}
    
    
}

    

