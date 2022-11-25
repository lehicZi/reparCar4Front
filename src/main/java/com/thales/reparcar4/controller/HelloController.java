package com.thales.reparcar4.controller;

import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.converter.InputStreamIterableInputConverter;
import com.gluonhq.connect.converter.JsonInputConverter;
import com.gluonhq.connect.converter.JsonIterableInputConverter;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import com.thales.reparcar4.model.Individu;
import com.thales.reparcar4.utils.JsonUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    public TextArea text;
    public Button btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GluonObservableObject<Individu> ind = getIndividu(5);

        ind.initializedProperty().addListener((obs, ov, nv) -> {
            if (nv && ind.get() != null) {
                text.setText(ind.get().toString());
            }
        });


        btn.setOnMouseClicked(mouseEvent -> {
            //Individu cho = new Individu("jojomal", "00222", "jean", "jack", "mdp", "CACA");
            //System.out.println(cho);
            //addIndividu(cho);
        });


    }

    private GluonObservableObject<Individu> getIndividu(int id){

        RestClient client = RestClient.create()
                .method("GET")
                .host("http://localhost:8080/reparcar/api/individus/"+String.valueOf(id))
                .connectTimeout(10000)
                .readTimeout(1000);

        return DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }

    private void addIndividu(Individu individu){

        RestClient client = RestClient.create()
                .method("POST")
                .host("http://localhost:8080/reparcar/api/individus/")
                .connectTimeout(10000)
                .readTimeout(1000)
                .dataString(JsonUtils.getStringJson(individu))
                .contentType("application/json");

        GluonObservableObject<Individu> question = DataProvider.retrieveObject(client.createObjectDataReader(Individu.class));
    }
}