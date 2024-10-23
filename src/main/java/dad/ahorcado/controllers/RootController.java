package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    //View

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

    private PuntuacionesController puntuacionesControllerInstance;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerTabPane.getTabs().clear();
        puntuacionesControllerInstance = new PuntuacionesController();

        Tab partidaTab = new Tab("Partida");
        partidaTab.setContent(new PartidaController(puntuacionesControllerInstance).getRoot());

        Tab palabrasTab = new Tab("Palabras");
        palabrasTab.setContent(new PalabrasController().getRoot());

        Tab puntuacionesTab = new Tab("Puntuaciones");
        puntuacionesTab.setContent(puntuacionesControllerInstance.getRoot());

        containerTabPane.getTabs().addAll(partidaTab, palabrasTab, puntuacionesTab);
    }

    public BorderPane getRoot() {
        return root;
    }

    public PuntuacionesController getPuntuacionesControllerInstance() {
        return puntuacionesControllerInstance;
    }
}
