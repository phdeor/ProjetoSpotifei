
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
        String verificarSql = "SELECT * FROM curtidas WHERE id_usuario = ? AND id_musica = ?";
        PreparedStatement stmtVerificar = conn.prepareStatement(verificarSql);
        stmtVerificar.setInt(1, idUsuario);
        stmtVerificar.setInt(2, idMusica);
        ResultSet res = stmtVerificar.executeQuery();

        if (res.next()) {
            // Já curtiu — então vamos remover (descurtir)
            String removerSql = "DELETE FROM curtidas WHERE id_usuario = ? AND id_musica = ?";
            PreparedStatement stmtRemover = conn.prepareStatement(removerSql);
            stmtRemover.setInt(1, idUsuario);
            stmtRemover.setInt(2, idMusica);
            stmtRemover.executeUpdate();
            stmtRemover.close();
        } else {
            // Ainda não curtiu — então vamos adicionar
            String inserirSql = "INSERT INTO curtidas (id_usuario, id_musica) VALUES (?, ?)";
            PreparedStatement stmtInserir = conn.prepareStatement(inserirSql);
            stmtInserir.setInt(1, idUsuario);
            stmtInserir.setInt(2, idMusica);
            stmtInserir.executeUpdate();
            stmtInserir.close();
        }

        res.close();
        stmtVerificar.close();
    }
    
    public ResultSet consultarMusicasCurtidas(int idUsuario) throws SQLException {
    String sql = "SELECT m.nome, m.genero, a.nome as nome_artista " +
                 "FROM musicas m " +
                 "INNER JOIN curtidas c ON m.id_musica = c.id_musica " +
                 "LEFT JOIN artistas a ON m.id_artista = a.id_artista " +
                 "WHERE c.id_usuario = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, idUsuario);
    return stmt.executeQuery();
}

    public boolean verificarCurtida(int idUsuario, int idMusica) throws SQLException {
        String sql = "SELECT 1 FROM curtidas WHERE id_usuario = ? AND id_musica = ?";
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

    

