
package Model;


public class Curtida {
    private int usuarioId;
    private int musicaId;

    public Curtida(int usuarioId, int musicaId) {
        this.usuarioId = usuarioId;
        this.musicaId = musicaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getMusicaId() {
        return musicaId;
    }
}
    
