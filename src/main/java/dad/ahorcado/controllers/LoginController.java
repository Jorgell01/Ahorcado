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
            String musicFile = getClass().getResource("/music/You-have-no-enemies.wav").toExternalForm(); // Esto cargará el archivo desde el classpath correctamente
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repite indefinidamente
            mediaPlayer.setVolume(0.3); // Asegurar que haya volumen
            mediaPlayer.play();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLogin() {
        String nickname = nicknameTextField.getText().trim();
        if (!nickname.isEmpty()) {
            rootController.getPuntuacionesControllerInstance().finalizarPartida(nickname, 0);

            // Detener la música de la ventana de login antes de cambiar a la ventana principal
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            // Cambiar a la ventana principal (RootController)
            stage.setTitle("AhorcadoApp");
            stage.setScene(new Scene(rootController.getRoot()));
            stage.show();

            // Reproducir la música en el RootController (si no está ya reproduciéndose)
            rootController.reproducirMusica();
        }
    }

    public VBox getRoot() {
        return root;
    }
}
