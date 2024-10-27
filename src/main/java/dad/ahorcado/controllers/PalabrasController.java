package dad.ahorcado.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PalabrasController implements Initializable {

    @FXML
    private ListView<String> listPalabras;
    @FXML
    private TextField textFieldNuevaPalabra;
    @FXML
    private Button aniadirButton;
    @FXML
    private Button quitarButton;
    @FXML
    private AnchorPane root;

    private ObservableList<String> palabras;
    private final String FILE_PATH = "palabras.json"; // Archivo para guardar las palabras

    public PalabrasController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabrasController.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        palabras = FXCollections.observableArrayList(cargarPalabras()); // Cargar palabras desde JSON al iniciar
        listPalabras.setItems(palabras);

        // Botones para añadir y quitar palabras de la lista
        aniadirButton.setOnAction(e -> aniadirPalabra());
        quitarButton.setOnAction(e -> quitarPalabra());
    }

    private void aniadirPalabra() {
        String nuevaPalabra = textFieldNuevaPalabra.getText().trim();
        if (!nuevaPalabra.isEmpty() && !palabras.contains(nuevaPalabra)) {
            palabras.add(nuevaPalabra);
            textFieldNuevaPalabra.clear();
            guardarPalabras(); // Guardar cambios
        }
    }

    private void quitarPalabra() {
        String palabraSeleccionada = listPalabras.getSelectionModel().getSelectedItem();
        if (palabraSeleccionada != null) {
            palabras.remove(palabraSeleccionada);
            guardarPalabras(); // Guardar cambios
        }
    }

    private List<String> cargarPalabras() {
        File jsonFile = new File(FILE_PATH);
        if (jsonFile.exists()) {
            try (FileReader reader = new FileReader(jsonFile)) {
                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                return new Gson().fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(); // Devuelve lista vacía si no hay archivo
    }

    private void guardarPalabras() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(palabras, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String obtenerPalabraAleatoria() {
        if (palabras.isEmpty()) {
            return "default"; // Valor por defecto si no hay palabras
        }
        int randomIndex = (int) (Math.random() * palabras.size());
        return palabras.get(randomIndex);
    }

    public AnchorPane getRoot() {
        return root;
    }
}
