package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.Receipt;
import com.example.restaurantordermanagement.utils.AppContext;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class InvoceController {
    @FXML
    private HBox hboxInvoice;
    @FXML
    private ListView<String> topProductsList;

    @FXML
    public void initialize(){
       refresh();
    }

    private void addInvoice(Receipt invoice){
        VBox card = new VBox();
        card.setSpacing(10.0);
        card.setStyle("-fx-border-color: #bbbbbb; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-color: #f8f8f8; -fx-background-radius: 4;");
        card.setPadding(new Insets(20));
        Label title = new Label("Invoice #"+invoice.getReceiptCode());
        title.setStyle("-fx-font-weight: bold;");
        card.getChildren().add(title);
        card.getChildren().add(new Label("Total: "+invoice.getTotalPrice() ));
        card.getChildren().add(new Label("On date: "+invoice.getDate()));

      //  Button clockoutButton = new Button("ClockOut");
       // clockoutButton.setOnAction(e -> handleClockOut(clocking,card));
        //card.getChildren().add(clockoutButton);

        hboxInvoice.getChildren().add(card);

    }

    public void refresh(){
        hboxInvoice.getChildren().clear();
        List<Receipt> receipts =  AppContext.getReceiptService().allReceipts();
        for(Receipt r:receipts){
            addInvoice(r);
        }
        loadTopProducts();
    }

    private void loadTopProducts() {
        int[][] matrix = AppContext.getProductService().buildProductMatrix();

        topProductsList.getItems().clear();

        for (int[] row : matrix) {
            int productId = row[0];
            int qty = row[1];

            String productName = AppContext.getProductService()
                    .getProductById(productId)
                    .getProductName();

            topProductsList.getItems().add(
                    productName + " — x" + qty
            );
        }
    }
}
