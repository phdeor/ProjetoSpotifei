
package Model;


public class Musica {
    private String nome, genero;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Musica() {
    }

    public Musica(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
    }
    
    @Override
    public String toString() {
        return "Musica{" + "nome=" + nome + ", genero=" + genero +'}';
    }
    
    
    
    
}
