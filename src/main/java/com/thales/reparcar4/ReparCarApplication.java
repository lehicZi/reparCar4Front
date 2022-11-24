package com.thales.reparcar4;

import com.thales.reparcar4.model.Individu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReparCarApplication extends Application {

    public static AnchorPane root;
    public static Map<String, Node> screens = new HashMap<>();
    private static String currentScreen = "login-screen";
    public static int APPWIDTH = 600;
    public static int APPLENGHT = 400;

    private static Individu connectedUser;

    @Override
    public void start(Stage stage) throws IOException {

        root = (AnchorPane) FXMLLoader.load(getClass().getResource("root.fxml"));
        screens.put("login-screen",(BorderPane) FXMLLoader.load(getClass().getResource("login-screen.fxml")));

        /*
        screens.put("hello",(VBox) FXMLLoader.load(getClass().getResource("hello-view.fxml")));

        screens.put("pageAccueil",(BorderPane) FXMLLoader.load(getClass().getResource("pageAccueil.fxml")));
        screens.put("GestionUtilisateur",(BorderPane) FXMLLoader.load(getClass().getResource("GestionUtilisateur.fxml")));
        screens.put("GestionProduit",(BorderPane) FXMLLoader.load(getClass().getResource("GestionProduit.fxml")));
        screens.put("GestionStockEtape1",(BorderPane) FXMLLoader.load(getClass().getResource("GestionStockEtape1.fxml")));

         */

        root.getChildren().add(screens.get(currentScreen));

        Scene scene = new Scene(root, APPWIDTH, APPLENGHT);
        stage.setTitle("ReparCar");
        stage.setScene(scene);
        stage.show();
    }

    public static void setScreen(String screen){

        if (!screens.keySet().contains(screen)) {
            try {
                screens.put(screen, (BorderPane) FXMLLoader.load(ReparCarApplication.class.getResource(screen + ".fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        root.getChildren().remove(screens.get(currentScreen));
        root.getChildren().add(screens.get(screen));
        currentScreen = screen;
    }

    public static void setUser(Individu user){
        connectedUser = user;
    }

    public static Individu getUser(){
        return connectedUser;
    }

    public static void main(String[] args) {
        launch();
    }
}