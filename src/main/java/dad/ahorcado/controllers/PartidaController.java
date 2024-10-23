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
    }

    @FXML
    public void resolverPalabra() {

    }

    @FXML
    void procesarLetra() {
    }

    private void actualizarPalabra() {
    }

    public AnchorPane getRoot() {
        return root;
    }
}
