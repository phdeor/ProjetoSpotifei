
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
    
    
    
    public ResultSet consultarMusica(String tipo, String termo) throws SQLException {
    String sql;
    String busca = '%' + termo + '%';    
  
    if (tipo.equalsIgnoreCase("musica")) {
        sql = "SELECT m.nome, m.genero, a.nome as nome_artista FROM musicas m left join artistas a  on m.id_artista = a.id_artista WHERE m.nome LIKE ? ";
    } else if (tipo.equalsIgnoreCase("artista")) {
        sql = "SELECT m.nome, m.genero, a.nome as nome_artista  FROM musicas m left join artistas a  on m.id_artista = a.id_artista WHERE m.id_artista in (SELECT id_artista FROM artistas WHERE nome LIKE ? ) ";
    } else if (tipo.equalsIgnoreCase("genero")) {
        sql = "SELECT m.nome, m.genero, a.nome as nome_artista  FROM musicas m left join artistas a  on m.id_artista = a.id_artista WHERE m.genero LIKE ? ";
    } else {
        sql = "SELECT m.nome, m.genero, a.nome as nome_artista  FROM musicas m left join artistas a  on m.id_artista = a.id_artista WHERE m.nome LIKE ? OR m.genero LIKE ? OR m.id_artista in (SELECT id_artista FROM artistas WHERE nome LIKE ? )";
    }
    PreparedStatement statement = conn.prepareStatement(sql);


    if (tipo.equalsIgnoreCase("tudo")){
        statement.setString(1, busca);
        statement.setString(2, busca);
        statement.setString(3, busca);
    } else {
        statement.setString(1, busca);
    }

    return statement.executeQuery();
    }
    
    
    
    public ResultSet buscarMusica(String nome, String genero, String artista) throws SQLException {
    String sql = """
        SELECT m.* FROM musicas m
        JOIN artistas a ON m.id_artista = a.id_artista
        WHERE m.nome = ? AND m.genero = ? AND a.nome = ?
    """;
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    stmt.setString(2, genero);
    stmt.setString(3, artista);
    return stmt.executeQuery();
}
    
    public ResultSet listarTodasMusicasComArtista() throws SQLException {
    String sql = """
        SELECT m.nome, m.genero, a.nome AS nome_artista 
        FROM musicas m 
        JOIN artistas a ON m.id_artista = a.id_artista
    """;
    PreparedStatement stmt = conn.prepareStatement(sql);
    return stmt.executeQuery();
}
    
    
    
}
