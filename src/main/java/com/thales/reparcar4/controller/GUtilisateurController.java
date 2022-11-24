package com.thales.reparcar4.controller;

import com.thales.reparcar4.model.Individu;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GUtilisateurController implements Initializable {
    public TextField txtNom;
    public TextField txtRole;
    public Button bntRecherche;
    public TableView tableVieuwUser;
    public TextArea tAreaSelectUser;
    public Button bntMod;
    public Button bntSup;
    public Button btnAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateTable();

    }

    private void initiateTable(){

        TableColumn<Individu, Integer> userIdCol //
                = new TableColumn<Individu, Integer>("Id");

        TableColumn<Individu, String> firstNameCol //
                = new TableColumn<Individu, String>("Nom");

        TableColumn<Individu, String> lastNameCol //
                = new TableColumn<Individu, String>("Prénom");

        TableColumn<Individu, String> emailCol//
                = new TableColumn<Individu, String>("Email");

        TableColumn<Individu, String> phoneCol//
                = new TableColumn<Individu, String>("Téléphone");

        TableColumn<Individu, String> roleCol//
                = new TableColumn<Individu, String>("rôle");


        tableVieuwUser.getColumns().addAll(userIdCol, firstNameCol, lastNameCol,emailCol,phoneCol,roleCol );

    }
}
