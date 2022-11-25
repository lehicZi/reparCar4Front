package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.utils.HttpRequests;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObjectProperty<Individu> selectedUser = new SimpleObjectProperty<Individu>();

    private ObservableList<Individu> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateTable();
        setEntete();
        initializeButtons();
        initializeSelection();
        initializeSearch();
    }

    private void initiateTable(){

        TableColumn<Individu, Integer> userIdCol
                = new TableColumn<Individu, Integer>("Id");

        TableColumn<Individu, String> firstNameCol
                = new TableColumn<Individu, String>("Nom");

        TableColumn<Individu, String> lastNameCol
                = new TableColumn<Individu, String>("Prénom");

        TableColumn<Individu, String> emailCol
                = new TableColumn<Individu, String>("Email");

        TableColumn<Individu, String> phoneCol
                = new TableColumn<Individu, String>("Téléphone");

        TableColumn<Individu, String> roleCol
                = new TableColumn<Individu, String>("rôle");


        tableVieuwUser.getColumns().addAll(userIdCol, firstNameCol, lastNameCol,emailCol,phoneCol,roleCol);

        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        GluonObservableList<Individu> gotList = HttpRequests.getAllIndividus();

        gotList.setOnSucceeded(connectStateEvent -> {
            this.users = FXCollections.observableArrayList(gotList);
            this.tableVieuwUser.setItems(this.users);
        });


        this.tableVieuwUser.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedUser.setValue((Individu) t1);
        });

    }

    private void initializeButtons(){
        this.btnAdd.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("userAdd");
        });

        this.bntMod.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("userAdd");
        });

        this.bntSup.setOnMouseClicked(mouseEvent -> {

            try {
                HttpRequests.deleteIndividu(selectedUser.get().getId());
                this.users.remove(selectedUser.get());
            }
            catch (NullPointerException e){
                System.out.println("Please select a user before deleting it...");
            }

        });
    }

    private void initializeSelection(){

        this.selectedUser.addListener((observableValue, individu, t1) -> {

            StringBuilder st = new StringBuilder();
            st.append("☺ Id : ").append(t1.getId()).append("\n")
                    .append("☺ Prénom : ").append(t1.getPrenom()).append("\n")
                    .append("☺ Nom : ").append(t1.getNom()).append("\n")
                    .append("☺ Email : ").append(t1.getEmail()).append("\n")
                    .append("☺ Rôle : ").append(t1.getRole()).append("\n");

            this.tAreaSelectUser.setText(st.toString());
        });
    }

    private void initializeSearch(){

        this.bntRecherche.setOnMouseClicked(mouseEvent -> {

            GluonObservableList<Individu> searchedList = HttpRequests.getSearchedIndividus(txtNom.getText(), txtRole.getText());

            searchedList.setOnSucceeded(connectStateEvent -> {
                this.users = FXCollections.observableArrayList(searchedList);
                System.out.println(users);
                this.tableVieuwUser.setItems(this.users);
            });

        });

    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(2);
        title.setText("Gestion des utilisateurs");
    }

}
