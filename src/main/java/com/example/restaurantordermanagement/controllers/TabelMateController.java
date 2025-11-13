package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.models.Table;
import com.example.restaurantordermanagement.service.ClockingService;
import com.example.restaurantordermanagement.service.EmployeeService;
import com.example.restaurantordermanagement.service.TableService;
import com.example.restaurantordermanagement.utils.AppContext;
import com.example.restaurantordermanagement.utils.ElementNotFoundException;
import com.example.restaurantordermanagement.utils.EmptyStringException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class TabelMateController {

    @FXML
    private Pane tablePane;

    private TableService tableService;
    private EmployeeService employeeService;
   private ClockingService clockingService;

    @FXML
    public void initialize() {
        clockingService = AppContext.getClockingService();
        employeeService = AppContext.getEmployeeService();
       tableService = AppContext.getTableService();
        List<Table> tables = tableService.getAllTable();
        for(Table t:tables){
            addMasaButton(t.getName(),t.getxPercent(),t.getyPercent());
        }
    }

    private void addMasaButton(String label, double xPercent, double yPercent) {
        Button tableButton = new Button(label);

        tableButton.setStyle(" -fx-background-color: limegreen");

        tableButton.setOnAction(e->handleClicknotReserved(tableButton));

        tableButton.layoutXProperty().bind(tablePane.widthProperty().multiply(xPercent));
        tableButton.layoutYProperty().bind(tablePane.heightProperty().multiply(yPercent));

        tableButton.setPrefSize(80, 40);
        tablePane.getChildren().add(tableButton);
    }

    private void handleClickReserved(Button tableButton){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Table: " + tableButton.getText());
        dialog.setHeaderText("Finish order!");
        ButtonType checkCode = new ButtonType("Check Code");
        ButtonType cancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(checkCode, cancel);
        dialog.showAndWait().ifPresent(type -> {


            if (type == checkCode) {
                tableButton.setStyle("-fx-background-color:limegreen");
                tableButton.setOnAction(e->handleClicknotReserved(tableButton));

            } else if (type == cancel) {
               }
        });
    }

    private void handleClicknotReserved(Button tableButton) {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Table: " + tableButton.getText());
            dialog.setHeaderText("Employee authentification required!");

            VBox box =new VBox(10);
            Label fieldName = new Label("Employee Code");
            Label errorText = new Label("");
            errorText.setStyle("-fx-text-fill: red");

            TextField employeeCode = new TextField();
            employeeCode.setPromptText("Enter the code");
            box.getChildren().addAll(errorText,fieldName,employeeCode);
            dialog.getDialogPane().setContent(box);




            ButtonType checkCode = new ButtonType("Check Code");
            ButtonType cancel = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(checkCode, cancel);

            Button check =(Button) dialog.getDialogPane().lookupButton(checkCode);

            check.addEventFilter(ActionEvent.ACTION,event->{
                try{
                    String employeeIdString = employeeCode.getText();
                    if(employeeIdString.isBlank() || employeeIdString == null){
                        throw new EmptyStringException("Employee Code is empty!");
                    }
                    int employeeId = Integer.parseInt(employeeIdString);
                    employeeService.findEmployeeById(employeeId);
                    if(!clockingService.alreadyClockedIn(employeeId)){
                        throw new ElementNotFoundException("Employee not clocked in!");
                    }

                }catch (EmptyStringException e){
                    errorText.setText(e.getMessage());
                    event.consume();
                }catch (NumberFormatException e){
                    errorText.setText("Employee Code must be a number!");
                    event.consume();
                } catch (ElementNotFoundException e) {
                    errorText.setText(e.getMessage());
                    event.consume();
                } catch (RuntimeException e) {
                    errorText.setText("Error, check in the console!");
                    event.consume();
                    e.printStackTrace();
                }
            });



             dialog.showAndWait().ifPresent(type -> {
        String codeText = employeeCode.getText();

        if (type == checkCode) {
            tableButton.setStyle("-fx-background-color:red");
            tableButton.setOnAction(e->handleClickReserved(tableButton));
            System.out.println("Add order for " + tableButton.getText() + " by employee " + codeText);

        } else if (type == cancel) {
            System.out.println("View order for " + tableButton.getText() + " by employee " + codeText);
        }
    });
        }

    }

