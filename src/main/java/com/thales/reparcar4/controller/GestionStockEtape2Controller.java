package com.thales.reparcar4.controller;
import com.gluonhq.connect.GluonObservableList;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.model.Piece;
import com.thales.reparcar4.utils.HttpRequests;
import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestionStockEtape2Controller implements Initializable {

    @FXML
    public TextField txtCode;
    @FXML
    public HBox enTete;
    @FXML
    public TableView piecesTable;
    @FXML
    public Label lbNomArticle;
    @FXML
    public Button btnMod;
    @FXML
    public TextField txtQte;

    private ObservableList<Piece> pieces;

    private ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<Piece>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEntete();
        initializeTable();
        initializeSelection();
        initializeButtons();

    }

    private void initializeButtons(){

        btnMod.setOnMouseClicked(mouseEvent -> {
            this.selectedPiece.get().setStock(Integer.parseInt(txtQte.getText()));
            HttpRequests.addPiece(this.selectedPiece.get());
        });

    }

    private void initializeTable(){

        TableColumn<Piece, String> pieceCodeCol
                = new TableColumn<Piece, String>("Code");

        TableColumn<Piece, String> colorCol
                = new TableColumn<Piece, String>("Couleur");

        TableColumn<Piece, String> dispoCol
                = new TableColumn<Piece, String>("Stock actuel");

        TableColumn<Piece, Integer> priceCol
                = new TableColumn<Piece, Integer>("Prix");



        piecesTable.getColumns().addAll(pieceCodeCol, colorCol, dispoCol,priceCol);

        pieceCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("couleur"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("prix"));

        GluonObservableList<Piece> gotList = HttpRequests.getAllPiece();

        gotList.setOnSucceeded(connectStateEvent -> {
            this.pieces = FXCollections.observableArrayList(gotList);
            this.piecesTable.setItems(this.pieces);
        });


        this.piecesTable.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedPiece.setValue((Piece) t1);
        });

    }


    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(2);
        title.setText("Gestion des stocks 2");
    }

    private void initializeSelection(){

        selectedPiece.addListener(observable -> {
            this.lbNomArticle.setText(selectedPiece.get().getCode());
        });
    }

}
