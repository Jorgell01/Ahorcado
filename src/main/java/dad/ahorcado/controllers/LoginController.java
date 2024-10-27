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

            // Reproducir música del login
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
            rootController.setNombreJugador(nickname);
            System.out.println("Nombre del jugador en LoginController: " + nickname);

            detenerMusica(); // Detiene la música del Login antes de cambiar la escena

            // Cambiar la escena al juego principal y empezar la música del Root
            stage.setScene(new Scene(rootController.getRoot()));
            rootController.iniciarMusica(); // Iniciar la música en el RootController
            stage.show();
        } else {
            System.out.println("El nickname no puede estar vacío.");
        }
    }

    // Detener la música del Login
    private void detenerMusica() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public VBox getRoot() {
        return root;
    }
}
