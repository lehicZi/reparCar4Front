package com.thales.reparcar4.utils;

import com.thales.reparcar4.model.Individu;
import javafx.beans.property.ObjectProperty;

public class AddUpdateSingleton {

    private static AddUpdateSingleton INSTANCE;

    private static ObjectProperty<Individu> selectedUser;

    private AddUpdateSingleton(){}

    public static AddUpdateSingleton getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AddUpdateSingleton();
        }
        return INSTANCE;
    }

    public static Individu getSelectedUser() {
        return selectedUser.get();
    }

    public static ObjectProperty<Individu> selectedUserProperty() {
        return selectedUser;
    }

    public static void setSelectedUser(Individu selectedUser) {
        AddUpdateSingleton.selectedUser.set(selectedUser);
    }
}
