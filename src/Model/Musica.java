
package Model;


public class Musica {
    private int id;
    private String nome, genero, artista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }
    
    

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

    public Musica(int id, String nome, String genero, String artista) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.artista = artista;
        
    }
    
    @Override
    public String toString() {
        return "Musica{" + "nome=" + nome + ", genero=" + genero +'}';
    }
    
    
    
    
}
