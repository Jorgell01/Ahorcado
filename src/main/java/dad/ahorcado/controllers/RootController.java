package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class RootController {

    // View
    @FXML
    private TabPane containerTabPane;

    @FXML
    private Tab palabrasController;

    @FXML
    private Tab partidaController;

    @FXML
    private Tab puntuacionesController;

    @FXML
    private BorderPane root;

    private MediaPlayer mediaPlayer;

    private PuntuacionesController puntuacionesControllerInstance;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootController.fxml"));
            loader.setController(this);
            loader.load();

            // Inicializa la instancia del PuntuacionesController
            puntuacionesControllerInstance = new PuntuacionesController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void reproducirMusica() {
        try {
            // Cargar y reproducir la música de fondo desde el classpath
            String musicFile = getClass().getResource("/music/diabla.wav").toExternalForm();
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repite indefinidamente
            mediaPlayer.setVolume(0.3); // Ajustar el volumen
            mediaPlayer.play();

        } catch (NullPointerException e) {
            System.out.println("No se pudo cargar el archivo de música.");
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public PuntuacionesController getPuntuacionesControllerInstance() {
        return puntuacionesControllerInstance;
    }
}
