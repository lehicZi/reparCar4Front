package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GUtilisateurController implements Initializable {
    @FXML
    public TextField txtNom;
    @FXML
    public TextField txtRole;
    @FXML
    public Button bntRecherche;
    @FXML
    public TableView tableVieuwUser;
    @FXML
    public TextArea tAreaSelectUser;
    @FXML
    public Button bntMod;
    @FXML
    public Button bntSup;
    @FXML
    public Button btnAdd;
    @FXML
    public HBox enTete;

    private ObjectProperty<Individu> selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateTable();
        setEntete();
        initializeButtons();

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


        tableVieuwUser.getColumns().addAll(userIdCol, firstNameCol, lastNameCol,emailCol,phoneCol,roleCol);

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        this.tableVieuwUser.setItems(getAllIndividus());

        this.tableVieuwUser.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            System.out.println(t1);
            selectedUser.setValue((Individu) t1);
        });

    }

    private void initializeButtons(){
        this.btnAdd.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("userAdd");
        });
    }

    private void initializeSelection(){

        this.selectedUser.addListener((observableValue, individu, t1) -> {

            this.tAreaSelectUser.setText("Id : "  + t1.getId() + "\n" );
        });
    }


    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(2);
        title.setText("Gestion des utilisateurs");
    }

    private GluonObservableList<Individu> getAllIndividus(){

        RestClient client = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/individus")
                .connectTimeout(10000)
                .readTimeout(1000);

        return DataProvider.retrieveList(client.createListDataReader(Individu.class));
    }
}
