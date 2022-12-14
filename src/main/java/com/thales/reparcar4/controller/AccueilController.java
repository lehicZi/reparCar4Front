package com.thales.reparcar4.controller;

import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.exception.NotSuchRoleException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    @FXML
    public Button btnUser;
    @FXML
    public Button btnProd;
    @FXML
    public Button btnGStock;
    @FXML
    public Button btnVStock;
    @FXML
    public Button btnExit;
    @FXML
    public Label lbUser;
    @FXML
    public Button btnDeco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initializeButtonsVisibility();
        initializeTxt();
    }

    private void initializeButtonsVisibility(){
        setButtonsVisibility();
        ReparCarApplication.userProperty().addListener(observable -> {
            btnVStock.setVisible(false);
            btnProd.setVisible(false);
            btnUser.setVisible(false);
            btnGStock.setVisible(false);
            setButtonsVisibility();
        });
    }

    private void setButtonsVisibility(){

        switch (ReparCarApplication.getUser().getRole()){

            case "ADMINISTRATIF":{
                btnVStock.setVisible(true);
                btnProd.setVisible(true);
                btnUser.setVisible(true);
                btnGStock.setVisible(true);
                break;
            }
            case "GESTIONNAIRE":{
                btnUser.setVisible(true);
                break;
            }
            case "MAGASINIER":{
                btnGStock.setVisible(true);
                btnProd.setVisible(true);
                break;
            }
            default: {
                throw new NotSuchRoleException(ReparCarApplication.getUser().toString() + " has an illegal role");
            }
        }

    }

    private void initializeButtons(){

        this.btnGStock.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionStockEtape1");
        });

        this.btnUser.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionUtilisateur");
        });

        this.btnProd.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionProduit");
        });

        this.btnVStock.setOnMouseClicked(mouseEvent -> {
            //ReparCarApplication.setScreen("GestionStockEtape1");
        });


        this.btnExit.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });

        this.btnDeco.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("login-screen");
        });

    }

    private void initializeTxt(){

        this.lbUser.setText("*** " + ReparCarApplication.getUser().getPrenom() + " " + ReparCarApplication.getUser().getNom() + " ***");

        ReparCarApplication.userProperty().addListener(observable -> {
            this.lbUser.setText("*** " + ReparCarApplication.getUser().getPrenom() + " " + ReparCarApplication.getUser().getNom() + " ***");
        });

    }
}
