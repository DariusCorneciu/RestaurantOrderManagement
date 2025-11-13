module com.example.restaurantordermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    requires javafx.graphics;


    opens com.example.restaurantordermanagement.utils to javafx.fxml, javafx.base;
    opens com.example.restaurantordermanagement to javafx.fxml;
    exports com.example.restaurantordermanagement;
    exports com.example.restaurantordermanagement.controllers;
    opens com.example.restaurantordermanagement.controllers to javafx.fxml;
}