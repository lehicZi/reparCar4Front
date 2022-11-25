package com.thales.reparcar4.controller;
import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.model.Piece;
import com.thales.reparcar4.utils.HttpRequests;
import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.converter.FloatStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionStockEtape1Controller implements Initializable {
    @FXML
    public Button btnAddProd;
    @FXML
    public TableView tableColis;
    @FXML
    public Button btnAdd;
    @FXML
    public TextField txtPoidTotalRecu;
    @FXML
    public TextField txtPoidTotalAttendu;
    @FXML
    public Button btnNext;
    @FXML
    public TextField txtCode;

    @FXML
    public HBox enTete;
    @FXML
    public Label lbAddPiece;
    @FXML
    public Label lbAddPiece2;
    public TextField txtQuantite;


    private FloatProperty totalPoids = new SimpleFloatProperty();
    private ObservableList<Piece> listePieceColis;

    private List<Piece> allDbPieces = new ArrayList<>();

    private ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<Piece>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtons();
        initiateTable();
        setEntete();
        getDbPieces();
        initializeTxts();

    }


    private Optional<Piece> rechechePiecebyCode(String code){
       return this.allDbPieces.stream().filter(p -> p.getCode().equals(code)).findFirst();
    }



    private void initializeButtons(){

        this.btnNext.setVisible(false);

         this.btnAdd.setOnMouseClicked(mouseEvent -> {
             if (this.txtQuantite.getText() == null || this.txtQuantite.getText().trim().isEmpty()){

             }
             else {
                 checkAndAdd();
             }

        });


         this.btnAddProd.setOnMouseClicked(mouseEvent -> {
             ReparCarApplication.setScreen("pieceAdd");
         });

        this.btnNext.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("GestionStockEtape2");
        });

        this.totalPoids.addListener(observable -> {

            if (totalPoids.get() <= 1000f){
                if (totalPoids.get() * 1.1 >= Float.parseFloat(txtPoidTotalAttendu.getText()) &&
                        totalPoids.get() * 0.9 <= Float.parseFloat(txtPoidTotalAttendu.getText())){

                    this.btnNext.setVisible(true);
                }
                else {
                    this.btnNext.setVisible(false);
                }
            }
            else {
                if (totalPoids.get() * 1.05 >= Float.parseFloat(txtPoidTotalAttendu.getText()) &&
                        totalPoids.get() * 0.95 <= Float.parseFloat(txtPoidTotalAttendu.getText())){

                    this.btnNext.setVisible(true);
                }
                else {
                    this.btnNext.setVisible(false);
                }
            }
        });

    }

    private void checkAndAdd(){

        Optional<Piece> thatPiece = rechechePiecebyCode(txtCode.getText());

        if (thatPiece.isEmpty() ){
            lbAddPiece.setVisible(true);
            lbAddPiece2.setVisible(true);
        }

        else {
            thatPiece.get().setStock(Integer.parseInt(txtQuantite.getText()));
            this.listePieceColis.add(thatPiece.get());
            this.totalPoids.setValue(this.totalPoids.get() + thatPiece.get().getPoids() * thatPiece.get().getStock());
            lbAddPiece.setVisible(false);
            lbAddPiece2.setVisible(false);
        }
    }
    private void initiateTable(){

        TableColumn<Piece, String> colCode   = new TableColumn<Piece, String>("Code");
        TableColumn<Piece, String> colNom  = new TableColumn<Piece, String>("Catégorie");
        TableColumn<Integer, Integer> colQte = new TableColumn<Integer, Integer>("Quantité");
        TableColumn<Piece, String> colPoids = new TableColumn<Piece, String>("Poids");
        tableColis.getColumns().addAll(colCode, colNom, colQte,colPoids);

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colNom.setCellValueFactory(pieceStringCellDataFeatures -> {

            String catName = pieceStringCellDataFeatures.getValue().getCategorie().getNom();
            return new SimpleStringProperty(catName);
        });

        colQte.setCellValueFactory(new PropertyValueFactory<>("stock"));

        colPoids.setCellValueFactory(pieceFloatCellDataFeatures -> {
            String poidsTotal = String.valueOf(pieceFloatCellDataFeatures.getValue().getPoids() * pieceFloatCellDataFeatures.getValue().getStock());
            return new SimpleStringProperty(poidsTotal);
        });


        this.listePieceColis = FXCollections.observableArrayList(new ArrayList<Piece>());

        this.tableColis.setItems(this.listePieceColis);
    }

    private void initializeTxts(){

        this.txtPoidTotalRecu.textProperty().bind(this.totalPoids.asString());
    }

    private void getDbPieces(){

        GluonObservableList<Piece> gotList = HttpRequests.getAllPiece();

        gotList.setOnSucceeded(connectStateEvent -> {
            this.allDbPieces = gotList;
            System.out.println(allDbPieces);
        });
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(2);
        title.setText("Gestion des stocks 1");
    }

}
