package dad.ahorcado.models;

public class Jugador {

    private String nick;
    private int puntuacion;

    public Jugador(String nick, int puntuacion) {
        this.nick = nick;
        this.puntuacion = puntuacion;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
