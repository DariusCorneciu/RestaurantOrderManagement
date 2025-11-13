package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.Clocking;
import com.example.restaurantordermanagement.service.ClockingService;
import com.example.restaurantordermanagement.utils.AlreadyExistsException;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import com.example.restaurantordermanagement.utils.InvalidDateException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InvalidObjectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClockingController {

    @FXML
    private TextField employeeCodeField;

    @FXML
    private Label errorLabel;
    @FXML
    private HBox historyBox;
    @FXML
    private ScrollPane historyScroll;

    private ClockingService clockingService;


    @FXML
    public void initialize() {
        clockingService = AppContext.getClockingService();
        List<Clocking> clockingList = clockingService.getAllClockings();
        for (Clocking c : clockingList) {
            addHistoryEntry(c);
        }

    }

    @FXML
    protected void clockinClicked() {

        String employeeIdString = employeeCodeField.getText();
        if (employeeIdString.isBlank() || employeeIdString == null) {
            errorLabel.setText("Employee code does not exists");
            return;
        }
        try {
            int employeeId = Integer.parseInt(employeeIdString);
            System.out.println(employeeId);
            Clocking clocking = new Clocking(-1, employeeId, LocalDateTime.now(), null);
            clockingService.create(clocking);
            addHistoryEntry(clocking);
        } catch (NumberFormatException e) {
            errorLabel.setText("Employee code need to be a number!");
        } catch (ElementNotFoundException e) {
            errorLabel.setText(e.getMessage());
        } catch (AlreadyExistsException e) {
            errorLabel.setText(e.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Other error check in console!");
            e.printStackTrace();
        }
    }

    private void addHistoryEntry(Clocking clocking) {

        String formatted = clocking.getClockin()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        VBox card = new VBox();
        card.setSpacing(10.0);
        card.setStyle("-fx-border-color: #bbbbbb; -fx-border-width: 1; -fx-border-radius: 4; -fx-background-color: #f8f8f8; -fx-background-radius: 4;");
        card.setPadding(new Insets(20));
        Label title = new Label("Currently ClockedIn");
        title.setStyle("-fx-font-weight: bold;");
        card.getChildren().add(title);
        card.getChildren().add(new Label("Name : " + clocking.getEmployee().getFullName()));
        card.getChildren().add(new Label("Clocked in at: " + formatted));

        Button clockoutButton = new Button("ClockOut");
        clockoutButton.setOnAction(e -> handleClockOut(clocking,card));
        card.getChildren().add(clockoutButton);

        historyBox.getChildren().add(card);


    }

    public void handleClockOut(Clocking clocking,VBox card) {

        try {
            Clocking updatedClocking = new Clocking(clocking);
            updatedClocking.setClockout(LocalDateTime.now());
            clockingService.update(updatedClocking);
            historyBox.getChildren().remove(card);
        } catch (InvalidDateException e) {
            errorLabel.setText(e.getMessage());

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

