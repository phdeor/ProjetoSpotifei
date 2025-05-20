
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
    
    public int buscarIdPorNome(String nome) throws SQLException {
    String sql = "SELECT id_musica FROM musicas WHERE nome = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        return rs.getInt("id_musica");
    }

    return -1; 
}
    
    public ResultSet buscarMusicaPorNomeEGenero(String nome, String genero) throws SQLException {
    String sql = "SELECT * FROM musicas WHERE nome = ? AND genero = ?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, nome);
    stmt.setString(2, genero);
    return stmt.executeQuery();
}
    
}
