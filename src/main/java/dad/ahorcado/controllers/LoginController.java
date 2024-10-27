package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField nicknameTextField;

    private Stage stage;
    private RootController rootController;

    @FXML
    private VBox root;

    private MediaPlayer mediaPlayer;

    public LoginController(Stage stage, RootController rootController) {
        this.stage = stage;
        this.rootController = rootController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginController.fxml"));
            loader.setController(this);
            loader.load();

            // Cargar y reproducir la música de fondo desde el classpath
            String musicFile = getClass().getResource("/music/You-have-no-enemies.wav").toExternalForm();
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
            mediaPlayer.play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLogin() {
        String nickname = nicknameTextField.getText().trim();
        if (!nickname.isEmpty()) {
            rootController.setNombreJugador(nickname);  // Pasamos el nombre del jugador a RootController
            System.out.println("Nombre del jugador en LoginController: " + nickname);

            // Cambiar la escena al juego principal
            stage.setScene(new Scene(rootController.getRoot()));
            stage.show();
        } else {
            System.out.println("El nickname no puede estar vacío.");
        }
    }

    public VBox getRoot() {
        return root;
    }
}
