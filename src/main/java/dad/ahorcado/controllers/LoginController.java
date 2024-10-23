package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField nicknameTextField;

    @FXML
    private VBox root;

    private Stage stage;
    private RootController rootController;

    public LoginController(Stage stage, RootController rootController) {
        this.stage = stage;
        this.rootController = rootController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLogin() {
        String nickname = nicknameTextField.getText().trim();
        if (!nickname.isEmpty()) {
            rootController.getPuntuacionesControllerInstance().finalizarPartida(nickname, 0);
            stage.setTitle("AhorcadoApp");
            stage.setScene(new Scene(rootController.getRoot()));
            stage.show();
        }
    }

    public VBox getRoot() {
        return root;
    }
}