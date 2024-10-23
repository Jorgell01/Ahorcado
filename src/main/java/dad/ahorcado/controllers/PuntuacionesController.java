package dad.ahorcado.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dad.ahorcado.models.Jugador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private HBox puntuacionesHBox;

    @FXML
    private ListView<String> puntuacionesListView;

    @FXML
    private AnchorPane root;

    private List<Jugador> jugadores;

    private final String FILE_PATH = "puntuaciones.json";

    public PuntuacionesController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize (URL url, ResourceBundle resourceBundle) {
        // Iniciar la lista de los gaymers
        jugadores = cargarPuntuaciones();

        // Actualizar el listview
        actualizarPuntuaciones();
    }


    // Guardar las puntuaciones en el JSON
    public void guardarPuntuaciones(Jugador jugador) {
        File jsonFile = new File(FILE_PATH);

        // Ver si se ha creado el json y donde
        System.out.println("El archivo JSON se creará en: " + jsonFile.getAbsolutePath());

        List<Jugador> jugadores = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        if (jsonFile.exists()) {
            try(FileReader reader = new FileReader(jsonFile)) {
                Jugador [] jugadoresArray = gson.fromJson(reader, Jugador[].class);
                if (jugadoresArray != null) {
                    jugadores.addAll(Arrays.asList(jugadoresArray));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (com.google.gson.JsonSyntaxException e) {
                System.out.println("El archivo tiene un formato incorrecto");
            }
        }

        jugadores.add(jugador);

        try (FileWriter writer = new FileWriter(jsonFile)) {
            gson.toJson(jugadores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Puntuaciones guardadas correctamente en puntuaciones.json");

    }

    // Cargar las puntuaciones desde JSON
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

    // Actualizar puntuaciones
    public void actualizarPuntuaciones() {
        List<String> listaPuntuaciones = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            listaPuntuaciones.add(jugador.getNick() + "-" + jugador.getPuntuacion() + " puntos");
        }
        puntuacionesListView.getItems().setAll(listaPuntuaciones);
    }

    // Metodo para guardar las puntuaciones al acabar una partida
    public void finalizarPartida(String nombreJugador, int puntuacion) {

        boolean jugadorExiste = false;
        for (Jugador jugador : jugadores) {
            if (jugador.getNick().equalsIgnoreCase(nombreJugador)) {
                jugadorExiste = true;
                break; // Si lo encuentro me salgo del bucle
            }
        }

        if (!jugadorExiste) {
        Jugador nuevojugador = new Jugador(nombreJugador, puntuacion);
        jugadores.add(nuevojugador);
        guardarPuntuaciones(nuevojugador);
        actualizarPuntuaciones();
        } else {
            System.out.println("El jugador " + nombreJugador + " ya existe.");
        }
    }

    public AnchorPane getRoot() {
        return root;
    }
}
