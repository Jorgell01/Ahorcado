package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartidaController {

    @FXML
    private Button buttonProbarLetra, buttonResolverPalabra, buttonReiniciar;
    @FXML
    private Label labelPalabra, labelLetrasIntentadas, labelVidasRestantes, labelPuntuaciones;
    @FXML
    private TextField textFieldLetra;
    @FXML
    private ImageView imageAhorcado;
    @FXML
    private AnchorPane root;

    private final PuntuacionesController puntuacionesController;
    private final String nombreJugador;

    private String palabraActual;
    private String palabraOculta;
    private int vidasRestantes;
    private String intentosFallidos;
    private List<Image> imagenesAhorcado; // Lista de imágenes para cada etapa del ahorcado

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
        cargarImagenes();
        iniciarPartida();
    }

    private void cargarImagenes() {
        imagenesAhorcado = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            String imagePath = getClass().getResource("/images/" + i + ".png").toExternalForm();
            imagenesAhorcado.add(new Image(imagePath));
        }
    }

    private void iniciarPartida() {
        PalabrasController palabrasController = new PalabrasController();
        palabraActual = palabrasController.obtenerPalabraAleatoria();
        palabraOculta = palabraActual.replaceAll("[A-Za-z]", "_ ");
        vidasRestantes = 9;
        intentosFallidos = "";
        actualizarInterfaz();
        actualizarImagenAhorcado();
    }

    private void actualizarInterfaz() {
        labelPalabra.setText(palabraOculta);
        labelVidasRestantes.setText("Vidas restantes: " + vidasRestantes);
        labelLetrasIntentadas.setText(intentosFallidos);
        textFieldLetra.clear();
    }

    private void actualizarImagenAhorcado() {
        int imagenIndex = 9 - vidasRestantes;
        if (imagenIndex >= 0 && imagenIndex < imagenesAhorcado.size()) {
            imageAhorcado.setImage(imagenesAhorcado.get(imagenIndex));
        }
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
                actualizarImagenAhorcado();
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
            puntuacionesController.finalizarPartida(nombreJugador, 100); // Ganaste
        } else {
            vidasRestantes--;
            actualizarInterfaz();
            actualizarImagenAhorcado();
            comprobarFinDeJuego();
        }
    }

    private void comprobarFinDeJuego() {
        if (palabraOculta.replace(" ", "").equals(palabraActual)) {
            puntuacionesController.finalizarPartida(nombreJugador, 100);
        } else if (vidasRestantes == 0) {
            puntuacionesController.finalizarPartida(nombreJugador, -50);
        }
    }

    @FXML
    public void onActionReiniciar() {
        iniciarPartida();
        intentosFallidos = "";
        labelLetrasIntentadas.setText("");
    }

    public AnchorPane getRoot() {
        return root;
    }
}
