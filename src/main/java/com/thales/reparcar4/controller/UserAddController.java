package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.utils.AddUpdateSingleton;
import com.thales.reparcar4.utils.HttpRequests;
import com.thales.reparcar4.utils.JsonUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private Individu selectedUser;
    private Individu toAdd = new Individu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        initializeBox();
        initializeAddOrUpdate();
    }

    private void initializeButton() {
        btnRefresh.setOnMouseClicked(mouseEvent -> {
            reset();
        });

        btnAdd.setOnMouseClicked(mouseEvent1 -> {

            this.toAdd.setEmail(txtemail.getText());
            this.toAdd.setRole(roleBox.valueProperty().get().toString());
            this.toAdd.setNom(txtNom.getText());
            this.toAdd.setTelephone(txtTel.getText());
            this.toAdd.setPrenom(txtPrenom.getText());

            GluonObservableObject<Individu> addRequest = HttpRequests.addIndividu(this.toAdd);
            addRequest.setOnSucceeded(connectStateEvent -> {
                AddUpdateSingleton.getInstance().setUserUpdated(!AddUpdateSingleton.getInstance().isUserUpdated());
                ReparCarApplication.setScreen("GestionUtilisateur");
            });
            addRequest.setOnFailed(connectStateEvent -> System.out.println(connectStateEvent));
        });

        bntExit.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionUtilisateur");
        });

    }

    private void reset() {
        txtemail.clear();
        txtTel.clear();
        txtNom.clear();
        txtPrenom.clear();
        this.roleBox.getSelectionModel().selectFirst();
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

    private void initializeAddOrUpdate(){
        setAddOrUpdate();
        AddUpdateSingleton.getInstance().selectedUserProperty().addListener(observable -> {
            setAddOrUpdate();
        });
    }
    private void setAddOrUpdate(){

        if (AddUpdateSingleton.getInstance().selectedUserProperty().get() == null) {
            this.selectedUser = null;
            setAdd();
        } else {
            this.selectedUser = AddUpdateSingleton.getInstance().selectedUserProperty().get();
            setUpdate();
        }

    }

    private void setAdd(){
        reset();
        btnAdd.setText("Ajouter");
        this.toAdd.setId(0);
    }

    private void setUpdate(){
        this.txtemail.setText(this.selectedUser.getEmail());
        this.txtNom.setText(this.selectedUser.getNom());
        this.txtPrenom.setText(this.selectedUser.getPrenom());
        this.txtTel.setText(this.selectedUser.getTelephone());
        this.roleBox.getSelectionModel().select(this.selectedUser.getRole());
        this.toAdd.setId(selectedUser.getId());
        btnAdd.setText("Modifier");
    }

}


