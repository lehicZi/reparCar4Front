package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.utils.JsonUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserAddController implements Initializable {


    @FXML
    public TextField txtNom;
    @FXML
    public TextField txtemail;
    @FXML
    public TextField txtTel;
    @FXML
    public TextField txtPrenom;
    @FXML
    public Button btnRefresh;
    @FXML
    public Button btnAdd;
    @FXML
    public Button bntExit;
    @FXML
    public ComboBox roleBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        initializeBox();
    }

    private void initializeButton() {
        btnRefresh.setOnMouseClicked(mouseEvent -> {
            txtemail.clear();
            txtTel.clear();
            txtNom.clear();
            txtPrenom.clear();
        });

        btnAdd.setOnMouseClicked(mouseEvent1 -> {


            GluonObservableObject<Individu> PotentialConnected =
                    addIndividu(new Individu(txtemail.getText(),txtTel.getText(),txtNom.getText(),
                            txtPrenom.getText(),"RECAP.2022!", roleBox.valueProperty().get().toString()));

        });

        bntExit.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionUtilisateur");
        });

    }

    private void initializeBox(){
        List<String> roles = new ArrayList<>();
        roles.add("MAGASINIER");
        roles.add("GESTIONNAIRE");
        roles.add("ADMINISTRATIF");

        ObservableList<String> oRoles = FXCollections.observableArrayList(roles);

        this.roleBox.setItems(oRoles);
        this.roleBox.getSelectionModel().selectFirst();
    }

    private GluonObservableObject<Individu> addIndividu(Individu individu){

        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/reparcar/api/individus/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(individu))
                .contentType("application/json");

        return DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }

    private void setAddOrUpdate(){
        List<String> roles = new ArrayList<>();
        roles.add("ADMINISTRATIF");
        roles.add("GESTIONNAIRE");
        roles.add("MAGASINIER");

        ObservableList<String> oRoles = FXCollections.observableArrayList(roles);

        this.roleBox.setItems(oRoles);
        this.roleBox.getSelectionModel().selectFirst();
    }

}


