package com.thales.reparcar4.utils;

import com.thales.reparcar4.model.Individu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AddUpdateSingleton {

    private static AddUpdateSingleton INSTANCE;

    private static ObjectProperty<Individu> selectedUser = new SimpleObjectProperty<Individu>();
    private static BooleanProperty UserUpdated = new SimpleBooleanProperty();

    private AddUpdateSingleton(){}

    public static AddUpdateSingleton getInstance(){
        if (INSTANCE == null){
            INSTANCE = new AddUpdateSingleton();
        }
        return INSTANCE;
    }

    public Individu getSelectedUser() {
        return selectedUser.get();
    }

    public ObjectProperty<Individu> selectedUserProperty() {
        return selectedUser;
    }

    public void setSelectedUser(Individu selectedUser) {
        AddUpdateSingleton.selectedUser.set(selectedUser);
    }

    public boolean isUserUpdated() {
        return UserUpdated.get();
    }

    public BooleanProperty userUpdatedProperty() {
        return UserUpdated;
    }

    public void setUserUpdated(boolean userUpdated) {
        UserUpdated.set(userUpdated);
    }
}
