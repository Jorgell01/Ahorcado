package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartidaController {

    @FXML
    private Button buttonProbarLetra, buttonResolverPalabra;
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
    private List<Image> imagenesAhorcado;

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
        textFieldLetra.setOnKeyPressed(this::handleKeyPressed);
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
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (textFieldLetra.isFocused()) {
                onActionProcesarLetra();
            } else if (buttonResolverPalabra.isFocused()) {
                onActionResolverPalabra();
            }
        }
    }

    @FXML
    public void onActionProcesarLetra() {
        String letra = textFieldLetra.getText().trim();
        textFieldLetra.clear();

        if (letra.length() == 1 && letra.matches("[A-Za-z]")) {
            if (intentosFallidos.contains(letra.toLowerCase()) || intentosFallidos.contains(letra.toUpperCase())) {
                System.out.println("Esta letra ya ha sido intentada.");
                return;
            }

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
            puntuacionesController.finalizarPartida(nombreJugador, 100);
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
            mostrarAlertaVictoria();
        } else if (vidasRestantes == 0) {
            puntuacionesController.finalizarPartida(nombreJugador, -50);
            mostrarAlertaDerrota();
        }
    }

    private void mostrarAlertaVictoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡Felicidades! Has ganado el juego.");
        alert.showAndWait();
    }

    private void mostrarAlertaDerrota() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Derrota");
        alert.setHeaderText(null);
        alert.setContentText("Lo siento, has perdido el juego.");
        alert.showAndWait();
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