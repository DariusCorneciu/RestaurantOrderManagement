module com.example.restaurantordermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
   // requires com.example.restaurantordermanagement;
    requires javafx.graphics;
    requires com.example.restaurantordermanagement;
    // requires com.example.restaurantordermanagement;
    // requires com.example.restaurantordermanagement;
    // requires com.example.restaurantordermanagement;
    // requires com.example.restaurantordermanagement;
    opens com.example.restaurantordermanagement.utils to javafx.fxml, javafx.base;
    opens com.example.restaurantordermanagement to javafx.fxml;
    exports com.example.restaurantordermanagement;
    exports com.example.restaurantordermanagement.controllers;
    opens com.example.restaurantordermanagement.controllers to javafx.fxml;
}