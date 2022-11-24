package com.thales.reparcar4.controller;

import com.thales.reparcar4.ReparCarApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EnteteController implements Initializable {
    @FXML
    public Label lbTitre;
    @FXML
    public Button bntMenu;
    @FXML
    public Button bntdeCo;
    @FXML
    public Label lbUserPrenom;
    @FXML
    public Label lbUserNom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializetxt();
        initializeButtons();

    }

    private void initializetxt(){

        this.lbUserNom.setText(ReparCarApplication.getUser().getNom());
        this.lbUserPrenom.setText(ReparCarApplication.getUser().getPrenom());

    }

    private void initializeButtons(){

        this.bntdeCo.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("login-screen");
        });

        this.bntMenu.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("pageAccueil");
        });

    }
}
