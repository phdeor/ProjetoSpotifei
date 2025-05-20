
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

    public void salvar(Historico historico, int idUsuario) throws SQLException {
    String sql = "INSERT INTO historico (pesquisa, tipo, id_usuario) VALUES (?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, historico.getPesquisa());
    stmt.setString(2, historico.getTipo());
    stmt.setInt(3, idUsuario);
    stmt.executeUpdate();
}
    
    public void consultarHistorico(DefaultListModel<String> listaModel, int idUsuario) throws SQLException {
        String sql = "SELECT pesquisa, tipo FROM historico WHERE id_usuario = ? ORDER BY id DESC LIMIT 10";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String pesquisa = rs.getString("pesquisa");
            String tipo = rs.getString("tipo");
            listaModel.addElement("🔍 " + tipo + ": " + pesquisa);
        }
    }
}
    
    
    
    

    
    

