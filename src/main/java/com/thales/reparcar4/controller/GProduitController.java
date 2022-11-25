package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.ReparCarApplication;
import com.thales.reparcar4.model.Categorie;
import com.thales.reparcar4.model.Piece;
import com.thales.reparcar4.model.Vehicule;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.*;

public class GProduitController implements Initializable {

    @FXML
    public ChoiceBox cbChoix;
    @FXML
    public TextField txtCode;
    @FXML
    public TextField txtNom;
    @FXML
    public TextField txtMarque;
    @FXML
    public TextField txtCat;
    @FXML
    public TextField txtVeh;
    @FXML
    public Button bntFind;

    public Map<String, TableView> tableViews;
    @FXML
    public TextArea tAreaSeclection;
    @FXML
    public Button btnMod;
    @FXML
    public Button btnSup;
    @FXML
    public Button btnAdd;
    @FXML
    public HBox enTete;
    @FXML
    public BorderPane subBorder;

    private ObjectProperty<Vehicule> selectedVehicule = new SimpleObjectProperty<Vehicule>();
    private ObservableList<Vehicule> vehicules;

    private ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<Piece>();
    private ObservableList<Piece> pieces;

    private ObjectProperty<Categorie> selectedCategorie = new SimpleObjectProperty<Categorie>();
    private ObservableList<Categorie> categories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            initiateCB();
            initializeBox();
            setEntete();
            initializeButtons();

            initializeSelection();
            //initializeSearch();
    }

    private void initializeButtons() {
        this.btnAdd.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("userAdd");
        });

        this.btnMod.setOnMouseClicked(mouseEvent -> {
            ReparCarApplication.setScreen("userAdd");
        });

        this.btnSup.setOnMouseClicked(mouseEvent -> {

        });

    }

    private void initiateCB() {
        tableViews= new HashMap<>();
        tableViews.put("piece", initiateTablePiece());
        tableViews.put( "vehicule",initiateTableVehicule());
        tableViews.put("categorie", initiateTableCategorie());

        cbChoix.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newStr) -> {
            subBorder.setCenter(this.tableViews.get(newStr));
        });

    }

    private TableView initiateTableCategorie() {
        TableView<Categorie> tableViewCat = new TableView<>();

        TableColumn<Categorie, String> codeCol = new TableColumn<Categorie, String>("Code");
        TableColumn<Categorie, String> nomCol = new TableColumn<Categorie, String>("Nom");
        TableColumn<Categorie, String> decrCol = new TableColumn<Categorie, String>("Description");
        TableColumn<Categorie, String> dateDebCol = new TableColumn<Categorie, String>("Date de début");
        TableColumn<Categorie, String> dateFinCol = new TableColumn<Categorie, String>("Date de fin");

        tableViewCat.getColumns().addAll(codeCol, nomCol,decrCol, dateDebCol,dateFinCol);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        decrCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateDebCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        GluonObservableList<Categorie> gotList = getAllCategorie();
        gotList.setOnSucceeded(connectStateEvent -> {
            this.categories = FXCollections.observableArrayList(gotList);
            tableViewCat.setItems(this.categories);
        });

        tableViewCat.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedCategorie.setValue((Categorie) t1);
        });

        return tableViewCat;
    }

    private GluonObservableList<Categorie> getAllCategorie() {
        RestClient categorie = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/categorie")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(categorie.createListDataReader(Categorie.class));
    }

    private TableView initiateTableVehicule() {
        TableView<Vehicule> tableViewVeh = new TableView<>();
        TableColumn<Vehicule, String> codeCol = new TableColumn<Vehicule, String>("Code");
        TableColumn<Vehicule, String> decrCol = new TableColumn<Vehicule, String>("Description");
        TableColumn<Vehicule, String> marqueCol = new TableColumn<Vehicule, String>("Marque");
        TableColumn<Vehicule, String> modelCol = new TableColumn<Vehicule, String>("Modèle");
        TableColumn<Vehicule, Integer> anneeCol  = new TableColumn<Vehicule, Integer>("Année");
        TableColumn<Vehicule, Integer> moisCol  = new TableColumn<Vehicule, Integer>("Mois");
        TableColumn<Vehicule, Integer> criTCol  = new TableColumn<Vehicule, Integer>("Crit'Air");

        tableViewVeh.getColumns().addAll(codeCol, decrCol, marqueCol,modelCol,anneeCol,moisCol,criTCol);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        decrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        marqueCol.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("modele"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<>("annee"));
        moisCol.setCellValueFactory(new PropertyValueFactory<>("mois"));
        criTCol.setCellValueFactory(new PropertyValueFactory<>("euroCritere"));

        GluonObservableList<Vehicule> gotList = getAllVehicule();

        gotList.setOnSucceeded(connectStateEvent -> {
            this.vehicules = FXCollections.observableArrayList(gotList);
            tableViewVeh.setItems(this.vehicules);
        });

        tableViewVeh.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedVehicule.setValue((Vehicule) t1);
        });

        return tableViewVeh;
    }

    private GluonObservableList<Vehicule> getAllVehicule(){
        RestClient vehicule = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/vehicules")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(vehicule.createListDataReader(Vehicule.class));
    }

    private TableView initiateTablePiece() {
        TableView<Piece> tableViewPiece = new TableView<>();

        TableColumn<Piece, String> codeCol = new TableColumn<Piece, String>("code");
        TableColumn<Piece, String> decrCol = new TableColumn<Piece, String>("description");
        TableColumn<Piece, String> couleurCol = new TableColumn<Piece, String>("couleur");
        TableColumn<Piece, Integer> dispoCol = new TableColumn<Piece, Integer>("disponiblilité");
        TableColumn<Piece, Integer> poidCol = new TableColumn<Piece, Integer>("poid");
        TableColumn<Piece, Integer> stockCol = new TableColumn<Piece, Integer>("stock");
        TableColumn<Piece, Integer> prixACol  = new TableColumn<Piece, Integer>("prix d'achat");
        TableColumn<Piece, Integer> prixCol  = new TableColumn<Piece, Integer>("prix");
        TableColumn<Piece, Integer> tvaCol  = new TableColumn<Piece, Integer>("tva");
        TableColumn<Piece, String> codeCatCol  = new TableColumn<Piece, String>("categorie");

        tableViewPiece.getColumns().addAll(codeCol, decrCol, couleurCol,dispoCol,poidCol,stockCol,prixACol,prixCol,tvaCol,codeCatCol);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        decrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        couleurCol.setCellValueFactory(new PropertyValueFactory<>("couleur"));
        dispoCol.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        poidCol.setCellValueFactory(new PropertyValueFactory<>("poid"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prixACol.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tvaCol.setCellValueFactory(new PropertyValueFactory<>("tva"));
        codeCatCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        GluonObservableList<Piece> gotList = getAllPiece();

        gotList.setOnSucceeded(connectStateEvent -> {
            this.pieces = FXCollections.observableArrayList(gotList);
            tableViewPiece.setItems(this.pieces);
        });

        tableViewPiece.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            selectedPiece.setValue((Piece) t1);
        });

        return tableViewPiece;
    }

    private GluonObservableList<Piece> getAllPiece() {
        RestClient piece = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/pieces")
                .connectTimeout(10000)
                .readTimeout(1000);
        return DataProvider.retrieveList(piece.createListDataReader(Piece.class));
    }

    private void initializeBox(){
        List<String> choix = new ArrayList<>();
        choix.add("vehicule");
        choix.add("piece");
        choix.add("categorie");

        ObservableList<String> oChoix = FXCollections.observableArrayList(choix);

        this.cbChoix.setItems(oChoix);
        this.cbChoix.getSelectionModel().selectFirst();
    }

    private void setEntete(){
        Label title = (Label) enTete.getChildren().get(2);
        title.setText("Gestion des produits");
    }

    private void initializeSelection(){
        

    }
}
