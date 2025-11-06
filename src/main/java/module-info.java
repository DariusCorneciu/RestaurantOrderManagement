module com.example.restaurantordermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.restaurantordermanagement to javafx.fxml;
    exports com.example.restaurantordermanagement;
}