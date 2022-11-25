package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableList;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Categorie;
import com.thales.reparcar4.model.Piece;
import com.thales.reparcar4.model.Vehicule;
import com.thales.reparcar4.utils.HttpRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class PieceAddController implements Initializable  {

    @FXML
    public TextField txtCode;
    @FXML
    public TextField txtDesc;
    @FXML
    public TextField txtPrixAchat;
    @FXML
    public TextField txtPrixHT;
    @FXML
    public TextField txtTVA;
    @FXML
    public ComboBox cbVehicule;
    @FXML
    public Button btnAddVeh;
    @FXML
    public TextArea tAreaListVeh;
    @FXML
    public Button bntClear;
    @FXML
    public Button bntAdd;
    @FXML
    public Button bntQuit;
    @FXML
    public TextField txtPoid;
    @FXML
    public ComboBox cbCategorie;
    @FXML
    public ComboBox cbCouleur;
    private List<Vehicule> listVehPiece = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        initializeBox();
    }

    private void initializeButton() {
        bntClear.setOnMouseClicked(mouseEvent -> {
            reset();
        });

        bntAdd.setOnMouseClicked(mouseEvent1 -> {
            Piece p = new Piece();
            p.setCode(txtCode.getText());
            p.setCategorie((Categorie) cbCategorie.valueProperty().getValue());
            p.setCouleur(cbCouleur.valueProperty().getValue().toString());
            p.setDescription(txtDesc.getText());
            p.setStock(0);
            p.setDisponible(0);
            p.setPrix(Float.parseFloat(txtPrixAchat.getText()));
            p.setPrixAchat(Float.parseFloat(txtPrixAchat.getText()));
            p.setTva(Float.parseFloat(txtTVA.getText()));
            p.setVehicules(listVehPiece);

            HttpRequests.addPiece(p);


        });

        bntQuit.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionStockEtape1");
        });

        btnAddVeh.setOnMouseClicked(mouseEvent -> {
           //recupere vehicule selectionné
            Vehicule toAdd = (Vehicule) cbVehicule.getValue();

            // ajoute le vehicule a la liste de ceux de la piece
            listVehPiece.add(toAdd);

            //affiche la liste dans le textArea
            StringBuilder st = new StringBuilder();
            String seperator = "";
            for (Vehicule v : listVehPiece){
                st.append(seperator).append("☺ ").append(v.getCode()).append(" : ").append(v.getMarque()).append(" ").append(v.getModele());
                seperator="\n";
            }
            this.tAreaListVeh.setText(st.toString());
        });

    }

    private void reset() {
        txtCode.clear();
        txtDesc.clear();
        txtPrixAchat.clear();
        txtPrixHT.clear();
        txtTVA.clear();
        txtPoid.clear();
        tAreaListVeh.clear();
        cbCategorie.getSelectionModel().select(0);
        cbVehicule.getSelectionModel().select(0);
    }

    private void initializeBox(){
        List<String> couleur = new ArrayList<>();
        couleur.add("NOIRE");
        couleur.add("BLANCHE");
        couleur.add("ROUGE");
        couleur.add("BLEU");
        couleur.add("JAUNE");
        couleur.add("GRISE");
        couleur.add("VERT");

        ObservableList<String> oCol = FXCollections.observableArrayList(couleur);

        this.cbCouleur.setItems(oCol);
        this.cbCouleur.getSelectionModel().selectFirst();

        Callback<ListView<Categorie>, ListCell<Categorie>> categoriCellFactory = new Callback<ListView<Categorie>, ListCell<Categorie>>() {

            @Override
            public ListCell<Categorie> call(ListView<Categorie> l) {
                return new ListCell<Categorie>() {

                    @Override
                    protected void updateItem(Categorie item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getCode() + " : " + item.getNom());
                        }
                    }
                } ;
            }
        };

        this.cbCategorie.setButtonCell(categoriCellFactory.call(null));
        this.cbCategorie.setCellFactory(categoriCellFactory);

        GluonObservableList<Categorie> categories = HttpRequests.getAllCategories();

        categories.setOnSucceeded(connectStateEvent -> {
            this.cbCategorie.setItems(categories);
            this.cbCategorie.getSelectionModel().selectFirst();
        });

        Callback<ListView<Vehicule>, ListCell<Vehicule>> vehiculeCellFactory = new Callback<ListView<Vehicule>, ListCell<Vehicule>>() {

            @Override
            public ListCell<Vehicule> call(ListView<Vehicule> l) {
                return new ListCell<Vehicule>() {

                    @Override
                    protected void updateItem(Vehicule item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getCode() + " : " + item.getMarque() + " " + item.getModele());
                        }
                    }
                } ;
            }
        };

        this.cbVehicule.setButtonCell(vehiculeCellFactory.call(null));
        this.cbVehicule.setCellFactory(vehiculeCellFactory);

        GluonObservableList<Vehicule> vehicules = HttpRequests.getAllVehicules();

        vehicules.setOnSucceeded(connectStateEvent -> {
            this.cbVehicule.setItems(vehicules);
            this.cbVehicule.getSelectionModel().selectFirst();
        });


    }

}
