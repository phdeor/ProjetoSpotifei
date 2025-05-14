
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.Historico;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;


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
    
    public void consultarHistorico(DefaultListModel<String> listaModel) throws SQLException {
    String sql = "SELECT nome, genero FROM historico ORDER BY id DESC LIMIT 10";
    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        String nome = rs.getString("nome");
        String genero = rs.getString("genero");

        // Adiciona o item formatado na lista (você pode personalizar a string aqui)
        listaModel.addElement("🎵 " + nome + " | " + genero);
    }
}
    
    
    
    

    
    
}
