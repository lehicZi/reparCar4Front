package com.thales.reparcar4;

import com.gluonhq.connect.GluonObservableList;
import com.thales.reparcar4.model.Categorie;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.utils.HttpRequests;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    public static int APPWIDTH = 910;
    public static int APPLENGHT = 500;
    private static ObjectProperty<Individu> user = new SimpleObjectProperty<Individu>();

    @Override
    public void start(Stage stage) throws IOException {
        initialInsert();

        root = (AnchorPane) FXMLLoader.load(getClass().getResource("root.fxml"));
        screens.put("login-screen",(BorderPane) FXMLLoader.load(getClass().getResource("login-screen.fxml")));

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

    public static Individu getUser() {
        return user.get();
    }

    public static ObjectProperty<Individu> userProperty() {
        return user;
    }

    public static void setUser(Individu newUser) {
        user.set(newUser);
    }

    private void initialInsert(){
        GluonObservableList<Individu> individus = HttpRequests.getAllIndividus();

        individus.setOnSucceeded(connectStateEvent -> {
            if (individus.isEmpty()){
                HttpRequests.initialInsert();
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}