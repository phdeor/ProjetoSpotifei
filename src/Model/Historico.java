
package Model;


public class Historico {
    private String nome;
    private String genero;

    public Historico(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }
}
