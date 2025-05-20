package Controller;

import DAO.Conexao;
import DAO.CurtidaDAO;
import DAO.HistoricoDAO;
import DAO.MusicaDAO;
import Model.Historico;
import Model.Musica;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import View.TelaMenu;
import javax.swing.DefaultListModel;
import Model.Usuario;
import java.util.List;
import java.util.ArrayList;

   

public class MenuController {
    private TelaMenu view;
    private String usuarioLogado;
    private Usuario usuario;
    private List<String> historicoBuscas = new ArrayList<>();
    
    public MenuController(TelaMenu view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
    }

    public void buscarMusica() {
    String termoBusca = view.getTxt_buscar().getText(); 
    String tipo = view.getOpcoesBusca().getSelectedItem().toString();

    Conexao conexao = new Conexao();
    try {
        Connection conn = conexao.getConnection();
        MusicaDAO dao = new MusicaDAO(conn);
        ResultSet res = dao.consultarMusica(tipo, termoBusca);

        StringBuilder resultado = new StringBuilder();
        boolean encontrou = false;

        while (res.next()) {
            String nome = res.getString("nome");
            String genero = res.getString("genero");
            String artista = res.getString("nome_artista");
            String entrada = "🎵 " + nome + " | " + genero + " | " + artista;
            
            HistoricoDAO historicoDAO = new HistoricoDAO(conn);
            Historico historico = new Historico(tipo, termoBusca);
            historicoDAO.salvar(historico, Integer.parseInt(usuario.getId()));

            // Adiciona ao histórico (máximo 10)
            if (historicoBuscas.size() >= 10) {
                historicoBuscas.remove(historicoBuscas.size() - 1);
            }
            historicoBuscas.add(0, entrada);
           

            // Monta mensagem para exibir
            resultado.append("Nome: ").append(nome)
                     .append(" | Gênero: ").append(genero)
                     .append(" | Artista: ").append(artista)
                     .append("\n");
            encontrou = true;
        }

        if (encontrou) {
            JOptionPane.showMessageDialog(view, resultado.toString(), "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Nenhuma música encontrada.", "Busca", JOptionPane.WARNING_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao buscar música.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void carregarHistorico() {
    Conexao conexao = new Conexao();
    try {
        Connection conn = conexao.getConnection();
        HistoricoDAO historicoDAO = new HistoricoDAO(conn);

        DefaultListModel<String> listaModel = new DefaultListModel<>();
        int usuarioId = Integer.parseInt(usuario.getId()); // PEGANDO O ID DO USUÁRIO LOGADO
        historicoDAO.consultarHistorico(listaModel, usuarioId);

        view.getList_historico().setModel(listaModel);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(view, "Erro ao carregar histórico!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void mostrarHistorico() {
    try {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        HistoricoDAO historicoDAO = new HistoricoDAO(conn);
        DefaultListModel<String> listaModel = new DefaultListModel<>();

        int usuarioId = Integer.parseInt(usuario.getId());
        historicoDAO.consultarHistorico(listaModel, usuarioId);

        view.getList_historico().setModel(listaModel);
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar histórico!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void mostrarTodasMusicas() {
    try {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        MusicaDAO musicaDAO = new MusicaDAO(conn);
        ResultSet res = musicaDAO.listarTodasMusicasComArtista();

        DefaultListModel<String> listaModel = new DefaultListModel<>();

        while (res.next()) {
            String nome = res.getString("nome");
            String genero = res.getString("genero");
            String artista = res.getString("nome_artista");
            String item = "🎶 " + nome + " | " + genero + " | " + artista;
            listaModel.addElement(item);
        }

        view.getList_historico().setModel(listaModel);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar músicas.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void curtirMusica() {
    String linhaSelecionada = view.getList_historico().getSelectedValue();

    if (linhaSelecionada == null || linhaSelecionada.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Selecione uma música para curtir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Extrai nome, gênero e artista (formato: 🎶 Nome | Gênero | Artista)
        String[] partes = linhaSelecionada.replace("🎶", "").trim().split("\\|");
        if (partes.length < 3) {
            JOptionPane.showMessageDialog(view, "Formato inválido da música.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeMusica = partes[0].trim();
        String generoMusica = partes[1].trim();
        String nomeArtista = partes[2].trim();

        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        MusicaDAO musicaDAO = new MusicaDAO(conn);
        ResultSet res = musicaDAO.buscarMusica(nomeMusica, generoMusica, nomeArtista);

        if (res.next()) {
            int musicaId = res.getInt("id_musica");
            int usuarioId = Integer.parseInt(usuario.getId());

            CurtidaDAO curtidaDAO = new CurtidaDAO(conn);
            boolean jaCurtido = curtidaDAO.verificarCurtida(usuarioId, musicaId);

            // Alterna entre curtir e descurtir
            curtidaDAO.curtirOuDescurtir(usuarioId, musicaId);

            // Registra no histórico
            HistoricoDAO historicoDAO = new HistoricoDAO(conn);
            String acao = jaCurtido ? "Descurtiu" : "Curtiu";
            Historico historico = new Historico(nomeMusica + " (" + acao + ")", nomeArtista);
            historicoDAO.salvar(historico, usuarioId);

            JOptionPane.showMessageDialog(view, jaCurtido ? "Música descurtida!" : "Música curtida!");

        } else {
            JOptionPane.showMessageDialog(view, "Música não encontrada no banco.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao curtir música.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    
    public void mostrarMusicasCurtidas() {
    try {
        Conexao conexao = new Conexao();
        Connection conn = conexao.getConnection();

        int usuarioId = Integer.parseInt(usuario.getId());

        CurtidaDAO curtidaDAO = new CurtidaDAO(conn);
        ResultSet res = curtidaDAO.consultarMusicasCurtidas(usuarioId);

        DefaultListModel<String> listaModel = new DefaultListModel<>();

        while (res.next()) {
            String nome = res.getString("nome");
            String genero = res.getString("genero");
            String artista = res.getString("nome_artista");

            String item = "❤️ " + nome + " | " + genero + " | " + artista;
            listaModel.addElement(item);
        }

        view.getList_historico().setModel(listaModel);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(view, "Erro ao carregar músicas curtidas.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    
    


}

    