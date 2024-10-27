package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PartidaController {

    @FXML
    private Button buttonProbarLetra, buttonResolverPalabra, buttonReiniciar;
    @FXML
    private Label labelPalabra, labelLetrasIntentadas, labelVidasRestantes, labelPuntuaciones;
    @FXML
    private TextField textFieldLetra;
    @FXML
    private AnchorPane root;

    private final PuntuacionesController puntuacionesController;
    private final String nombreJugador;

    private String palabraActual;
    private String palabraOculta;
    private int vidasRestantes;
    private String intentosFallidos;

    public PartidaController(PuntuacionesController puntuacionesController, String nombreJugador) {
        this.puntuacionesController = puntuacionesController;
        this.nombreJugador = nombreJugador;
        puntuacionesController.setJugadorActual(nombreJugador);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        iniciarPartida();
        buttonReiniciar.setOnAction(e -> onActionReiniciar());
    }

    private void iniciarPartida() {
        PalabrasController palabrasController = new PalabrasController();
        palabraActual = palabrasController.obtenerPalabraAleatoria();
        palabraOculta = palabraActual.replaceAll("[A-Za-z]", "_ ");
        vidasRestantes = 9;
        intentosFallidos = "";
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        labelPalabra.setText(palabraOculta);
        labelVidasRestantes.setText("Vidas restantes: " + vidasRestantes);
        labelLetrasIntentadas.setText(intentosFallidos);
        textFieldLetra.clear();
    }

    @FXML
    public void onActionProcesarLetra() {
        String letra = textFieldLetra.getText().trim();
        textFieldLetra.clear();

        if (letra.length() == 1 && letra.matches("[A-Za-z]")) {
            if (palabraActual.toLowerCase().contains(letra.toLowerCase())) {
                for (int i = 0; i < palabraActual.length(); i++) {
                    if (palabraActual.toLowerCase().charAt(i) == letra.toLowerCase().charAt(0)) {
                        palabraOculta = palabraOculta.substring(0, i * 2) + palabraActual.charAt(i) + palabraOculta.substring(i * 2 + 1);
                    }
                }
                actualizarInterfaz();
            } else {
                vidasRestantes--;
                intentosFallidos += letra + " ";
                actualizarInterfaz();
            }
        } else {
            System.out.println("Introduce solo una letra.");
        }

        comprobarFinDeJuego();
    }

    @FXML
    public void onActionResolverPalabra() {
        String palabraIntentada = textFieldLetra.getText().trim();
        textFieldLetra.clear();

        if (palabraActual.equalsIgnoreCase(palabraIntentada)) {
            System.out.println("¡Has ganado!");
            puntuacionesController.finalizarPartida(nombreJugador, 100); // Incluimos el nombre del jugador
            labelPuntuaciones.setText("Puntos: " + puntuacionesController.obtenerPuntuacionJugador(nombreJugador));
        } else {
            System.out.println("Palabra incorrecta.");
            vidasRestantes--;
            actualizarInterfaz();
            comprobarFinDeJuego();
        }
    }

    private void comprobarFinDeJuego() {
        if (palabraOculta.replace(" ", "").equals(palabraActual)) {
            System.out.println("¡Has ganado!");
            if (nombreJugador != null && !nombreJugador.isEmpty()) {
                puntuacionesController.finalizarPartida(nombreJugador, 100); // Pasamos el nombre del jugador y la puntuación
                labelPuntuaciones.setText("Puntos: " + puntuacionesController.obtenerPuntuacionJugador(nombreJugador));
            } else {
                System.out.println("Error: El nombre del jugador no puede estar vacío.");
            }
        } else if (vidasRestantes == 0) {
            System.out.println("Has perdido.");
            if (nombreJugador != null && !nombreJugador.isEmpty()) {
                puntuacionesController.finalizarPartida(nombreJugador, -50); // Pasamos el nombre del jugador y la puntuación
                labelPuntuaciones.setText("Puntos: " + puntuacionesController.obtenerPuntuacionJugador(nombreJugador));
            } else {
                System.out.println("Error: El nombre del jugador no puede estar vacío.");
            }
        }
    }

    @FXML
    public void onActionReiniciar() {
        iniciarPartida();
    }

    public AnchorPane getRoot() {
        return root;
    }
}
