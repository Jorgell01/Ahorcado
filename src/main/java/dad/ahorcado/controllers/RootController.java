package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private TabPane containerTabPane;

    @FXML
    private BorderPane root;

    @FXML
    private Slider volumeSlider;

    private String nombreJugador;
    private PuntuacionesController puntuacionesControllerInstance;
    private MediaPlayer mediaPlayer;
    private PartidaController partidaController;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootController.fxml"));
            loader.setController(this);
            loader.load();

            // Crear instancia de PuntuacionesController
            puntuacionesControllerInstance = new PuntuacionesController();

            // Configurar la música del juego pero no reproducirla aún
            String musicFile = getClass().getResource("/music/diabla.wav").toExternalForm();
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);

            // Listener para el Slider de volumen
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(newValue.doubleValue()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerTabPane.getTabs().clear();
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        if (puntuacionesControllerInstance != null) {
            puntuacionesControllerInstance.setJugadorActual(nombreJugador);
        }
        System.out.println("Nombre del jugador establecido: " + nombreJugador);

        crearPestanas();
    }

    private void crearPestanas() {
        Tab partidaTab = new Tab("Partida");
        partidaTab.setContent(new PartidaController(puntuacionesControllerInstance, nombreJugador).getRoot());

        Tab palabrasTab = new Tab("Palabras");
        palabrasTab.setContent(new PalabrasController().getRoot());

        Tab puntuacionesTab = new Tab("Puntuaciones");
        puntuacionesTab.setContent(puntuacionesControllerInstance.getRoot());

        containerTabPane.getTabs().addAll(partidaTab, palabrasTab, puntuacionesTab);
    }

    // Método para iniciar la música en el RootController
    public void iniciarMusica() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public PuntuacionesController getPuntuacionesControllerInstance() {
        return puntuacionesControllerInstance;
    }

    @FXML
    private void handleNuevaPartida() {
        System.out.println("Nueva partida iniciada");

        // Obtén la referencia de la pestaña donde está PartidaController
        Tab partidaTab = containerTabPane.getTabs().stream()
                .filter(tab -> "Partida".equals(tab.getText()))
                .findFirst()
                .orElse(null);

        if (partidaTab != null) {
            if (partidaController == null) {
                partidaController = new PartidaController(puntuacionesControllerInstance, nombreJugador);
                partidaTab.setContent(partidaController.getRoot());
            } else {
                partidaController.onActionReiniciar();
            }
        }
    }

    @FXML
    private void handleSocorro() {
        // Lógica para mostrar la ventana de ayuda
        Stage helpStage = new Stage();
        VBox helpRoot = new VBox();
        helpRoot.getChildren().add(new Label("Normas del juego de Ahorcado:\n\n"
                + "1. El objetivo es adivinar la palabra secreta letra por letra.\n"
                + "2. Tienes un número limitado de intentos para adivinar la palabra.\n"
                + "3. Cada vez que adivinas una letra incorrecta, pierdes un intento.\n"
                + "4. Si adivinas todas las letras de la palabra antes de que se acaben los intentos, ganas.\n"
                + "5. Si se te acaban los intentos antes de adivinar la palabra, pierdes.\n"
                + "6. Puedes iniciar una nueva partida en cualquier momento desde el menú 'Nueva Partida'."));
        Scene helpScene = new Scene(helpRoot, 500, 200);
        helpStage.setScene(helpScene);
        helpStage.setTitle("Socorro");
        helpStage.show();
    }
}