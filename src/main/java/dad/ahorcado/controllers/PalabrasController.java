package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PalabrasController {

    @FXML
    private Button aniadirButton;

    @FXML
    private VBox containerButton;

    @FXML
    private ListView<?> listPalabras;

    @FXML
    private Button quitarButton;

    @FXML
    private AnchorPane root;

    public PalabrasController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabrasController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Initialize (URL url, ResourceBundle resourceBundle){

    }

    public AnchorPane getRoot() {
        return root;
    }
}
