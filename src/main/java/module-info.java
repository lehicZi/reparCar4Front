module com.thales.reparcar4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires lombok;
    requires com.gluonhq.connect;
    requires com.fasterxml.jackson.databind;

    opens com.thales.reparcar4 to javafx.fxml;
    exports com.thales.reparcar4;
    exports com.thales.reparcar4.model;
    exports com.thales.reparcar4.controller;
    exports com.thales.reparcar4.utils;
    opens com.thales.reparcar4.controller to javafx.fxml;
}