package dad.ahorcado.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PartidaController implements Initializable {

    // view

    @FXML
    private Button buttonProbarLetra;

    @FXML
    private Button buttonResolverPalabra;

    @FXML
    private ImageView imageAhorcado;

    @FXML
    private Label labelLetrasIntentadas;

    @FXML
    private Label labelPalabra;

    @FXML
    private Label labelPuntuaciones;

    @FXML
    private Label labelVidasRestantes;

    @FXML
    private TextField textFieldLetra;

    @FXML
    private AnchorPane root;

    private PuntuacionesController puntuacionesController;

    private String palabraAdivinar = "JAVA";  // Ejemplo de palabra a adivinar

    private String palabraActual = "_ _ _ _";  // Representación de la palabra actual con letras descubiertas

    private int vidasRestantes = 6;  // Número de vidas iniciales del jugador

    public PartidaController(PuntuacionesController puntuacionesController) {
        this.puntuacionesController = puntuacionesController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPalabra.setText(palabraActual);
        labelVidasRestantes.setText("Vidas: " + vidasRestantes);
    }

    // Método para el botón de "Resolver"
    @FXML
    public void resolverPalabra(ActionEvent event) {
        String palabraIngresada = textFieldLetra.getText().toUpperCase();

        // Verificar si la palabra ingresada es correcta
        if (palabraIngresada.equals(palabraAdivinar)) {
            // El jugador acierta la palabra
            System.out.println("¡Adivinaste la palabra correctamente!");

            // Registrar la puntuación del jugador (ejemplo de 100 puntos si gana)
            String nombreJugador = "Jugador1";  // Puedes obtener dinámicamente el nombre del jugador
            int puntuacion = 100;
            puntuacionesController.finalizarPartida(nombreJugador, puntuacion);

        } else {
            // El jugador falla, se resta una vida
            vidasRestantes--;
            labelVidasRestantes.setText("Vidas: " + vidasRestantes);

            // Verificar si ha perdido todas las vidas
            if (vidasRestantes <= 0) {
                System.out.println("¡Perdiste todas tus vidas!");

                // El jugador no obtiene puntos
                puntuacionesController.finalizarPartida("Jugador1", 0);  // 0 puntos al perder
            }
        }
    }

    @FXML
    void procesarLetra(ActionEvent event) {
        String letra = textFieldLetra.getText().toUpperCase();
        if (letra.isEmpty()) {
            return; // Controlar que no se ingrese una letra vacía
        }

        // Verificar si la letra está en la palabra a adivinar
        if (palabraAdivinar.contains(letra)) {
            actualizarPalabra(letra);
        } else {
            // Si la letra no está en la palabra, reducir vidas
            vidasRestantes--;
            labelVidasRestantes.setText("Vidas: " + vidasRestantes);

            // Verificar si el jugador ha perdido
            if (vidasRestantes <= 0) {
                System.out.println("¡Perdiste todas tus vidas!");
                puntuacionesController.finalizarPartida("Jugador1", 0);  // 0 puntos si pierde
            }
        }
    }

    private void actualizarPalabra(String letra) {
        StringBuilder nuevaPalabra = new StringBuilder(palabraActual);
        for (int i = 0; i < palabraAdivinar.length(); i++) {
            if (palabraAdivinar.charAt(i) == letra.charAt(0)) {
                nuevaPalabra.setCharAt(i * 2, letra.charAt(0));  // Actualizar la palabra mostrada
            }
        }
        palabraActual = nuevaPalabra.toString();
        labelPalabra.setText(palabraActual);

        // Verificar si el jugador ha adivinado toda la palabra
        if (!palabraActual.contains("_")) {
            System.out.println("¡Ganaste la partida!");
            puntuacionesController.finalizarPartida("Jugador1", 100);  // Puntuación de 100 puntos
        }
    }

    public AnchorPane getRoot() {
        return root;
    }
}
