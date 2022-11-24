package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.model.Login;
import com.thales.reparcar4.utils.JsonUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public PasswordField txtMdp;
    @FXML
    public TextField txtEmail;
    @FXML
    public Label lbErreur;
    @FXML
    public Button btnConnexion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
    }

    private void initializeButton(){

        btnConnexion.setOnMouseClicked(mouseEvent -> {

            GluonObservableObject<Individu> PotentialConnected = tryLogin(new Login(txtEmail.getText(), txtMdp.getText()));

            PotentialConnected.setOnSucceeded(connectStateEvent -> {
                ReparCarApplication.setUser(PotentialConnected.get());
                ReparCarApplication.setScreen("pageAccueil");
            });

            PotentialConnected.setOnFailed(connectStateEvent -> {
                lbErreur.setVisible(true);
            });

        });
    }

    private GluonObservableObject<Individu> tryLogin(Login login){

        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/reparcar/api/individus/connect")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(login))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }
}