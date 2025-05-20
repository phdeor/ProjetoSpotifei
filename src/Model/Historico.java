
package Model;


public class Historico {
    private String tipo;
    private String pesquisa;

    public Historico(String tipo, String pesquisa) {
        this.tipo = tipo;
        this.pesquisa = pesquisa;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPesquisa() {
        return pesquisa;
    }
}
