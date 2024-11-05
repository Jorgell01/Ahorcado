package dad.ahorcado.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dad.ahorcado.models.Jugador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PuntuacionesController implements Initializable {

    @FXML
    private Label puntuacionesLabel;
    @FXML
    private HBox puntuacionesHBox;
    @FXML
    private ListView<String> puntuacionesListView;
    @FXML
    private AnchorPane root;

    private ObservableList<String> puntuaciones;
    private List<Jugador> jugadores;  // Lista de jugadores con sus puntuaciones
    private final String FILE_PATH = "puntuaciones.json";  // Ruta del archivo JSON
    private String nombreJugador;  // Nombre del jugador actual

    public PuntuacionesController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jugadores = cargarPuntuaciones();
        puntuaciones = FXCollections.observableArrayList();
        puntuacionesListView.setItems(puntuaciones);
        actualizarPuntuaciones();  // Actualiza la vista con los datos del JSON
    }

    public void setJugadorActual(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void guardarPuntuaciones() {
        File jsonFile = new File(FILE_PATH);
        System.out.println("Intentando guardar en: " + jsonFile.getAbsolutePath());

        if (!jsonFile.exists()) {
            try {
                if (jsonFile.createNewFile()) {
                    System.out.println("Archivo puntuaciones.json creado en: " + jsonFile.getAbsolutePath());
                } else {
                    System.out.println("Error al crear puntuaciones.json en: " + jsonFile.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("Error al crear el archivo puntuaciones.json: " + e.getMessage());
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(jsonFile)) {
            gson.toJson(jugadores, writer);
            System.out.println("Puntuaciones guardadas correctamente en puntuaciones.json");
        } catch (IOException e) {
            System.err.println("Error al guardar puntuaciones en puntuaciones.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Jugador> cargarPuntuaciones() {
        List<Jugador> jugadoresCargados = new ArrayList<>();
        File jsonFile = new File(FILE_PATH);
        if (jsonFile.exists()) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(jsonFile)) {
                Jugador[] jugadoresArray = gson.fromJson(reader, Jugador[].class);
                if (jugadoresArray != null) {
                    jugadoresCargados.addAll(Arrays.asList(jugadoresArray));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jugadoresCargados;
    }

    public void actualizarPuntuaciones() {
        puntuaciones.clear();
        for (Jugador jugador : jugadores) {
            puntuaciones.add(jugador.getNick() + " - " + jugador.getPuntuacion() + " puntos");
        }
    }

    public void finalizarPartida(String nombreJugador, int puntuacion) {
        System.out.println("Nombre del jugador recibido en finalizarPartida: " + nombreJugador);
        System.out.println("Puntuación recibida en finalizarPartida: " + puntuacion);

        if (nombreJugador == null || nombreJugador.isEmpty()) {
            System.out.println("Error: El nombre del jugador no puede estar vacío.");
            return;
        }

        boolean jugadorExiste = false;
        for (Jugador jugador : jugadores) {
            if (jugador.getNick().equalsIgnoreCase(nombreJugador)) {
                jugadorExiste = true;
                jugador.setPuntuacion(jugador.getPuntuacion() + puntuacion);  // Sumar puntos si el jugador ya existe
                break;
            }
        }

        if (!jugadorExiste) {
            Jugador nuevoJugador = new Jugador(nombreJugador, puntuacion);
            jugadores.add(nuevoJugador);  // Añadir jugador si no existía
        }

        guardarPuntuaciones();
        actualizarPuntuaciones();  // Actualizar la lista de puntuaciones
    }

    public int obtenerPuntuacionJugador(String nombreJugador) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNick() != null && jugador.getNick().equalsIgnoreCase(nombreJugador)) {
                return jugador.getPuntuacion();
            }
        }
        return 0;  // Retorna 0 si el jugador no tiene puntuación previa
    }

    public AnchorPane getRoot() {
        return root;
    }
}