package com.thales.reparcar4.controller;

import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.exception.NotSuchRoleException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        setButtonsVisibility();
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

    }
}
