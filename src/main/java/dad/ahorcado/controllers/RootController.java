package dad.ahorcado.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private TabPane containerTabPane;

    @FXML
    private BorderPane root;

    private String nombreJugador;
    private PuntuacionesController puntuacionesControllerInstance;
    private MediaPlayer mediaPlayer;

    public RootController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootController.fxml"));
            loader.setController(this);
            loader.load();

            // Inicializamos la instancia de PuntuacionesController aquí
            puntuacionesControllerInstance = new PuntuacionesController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerTabPane.getTabs().clear();
    }

    // Método para recibir el nombre del jugador
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        if (puntuacionesControllerInstance != null) {
            puntuacionesControllerInstance.setJugadorActual(nombreJugador);
        }
        System.out.println("Nombre del jugador establecido: " + nombreJugador);

        crearPestanas(); // Llamar a crearPestanas después de establecer el nombre
    }

    // Método para crear pestañas cuando el nombre del jugador ya está establecido
    private void crearPestanas() {
        // Crear las pestañas y pasar el nombre del jugador al PartidaController
        Tab partidaTab = new Tab("Partida");
        partidaTab.setContent(new PartidaController(puntuacionesControllerInstance, nombreJugador).getRoot());

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
