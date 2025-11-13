package com.example.restaurantordermanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class TabelMateController {

    @FXML
    private Pane tablePane;



    @FXML
    public void initialize() {
        addMasaButton("Masa 1", 0.215, 0.285);
        addMasaButton("Masa 2", 0.278, 0.505);
        addMasaButton("Masa 3", 0.433, 0.095);
        addMasaButton("Masa 4", 0.63, 0.285);
        addMasaButton("Masa 5", 0.586, 0.505);
    }

    private void addMasaButton(String label, double xPercent, double yPercent) {
        Button b = new Button(label);


        b.layoutXProperty().bind(tablePane.widthProperty().multiply(xPercent));
        b.layoutYProperty().bind(tablePane.heightProperty().multiply(yPercent));

        b.setPrefSize(80, 40);
        tablePane.getChildren().add(b);
    }
}
