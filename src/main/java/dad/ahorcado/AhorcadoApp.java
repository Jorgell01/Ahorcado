package dad.ahorcado;

import dad.ahorcado.controllers.LoginController;
import dad.ahorcado.controllers.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AhorcadoApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RootController rootController = new RootController();

        // Mostrar primero la pantalla de Login
        LoginController loginController = new LoginController(primaryStage, rootController);
        primaryStage.setTitle("Ahorcado - Login");

        primaryStage.setScene(new Scene(loginController.getRoot()));
        primaryStage.show();
    }
}
