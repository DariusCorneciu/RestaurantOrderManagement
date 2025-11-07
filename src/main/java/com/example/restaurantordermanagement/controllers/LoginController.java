package com.example.restaurantordermanagement.controllers;

import com.example.restaurantordermanagement.TableMateApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {

    @FXML
    private HBox component;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginProblems;


    @FXML
    protected void goBackFromButtonCick(){
        redirectToViewName("login-page.fxml","Login");
    }

    @FXML
    protected void onTabelMateButtonClick() {
        redirectToViewName("tabel-mate.fxml", "General");
    }



    @FXML
    protected void onAdminButtonClick(){
        boolean visibility = loginButton.isVisible();
        loginButton.setVisible(!visibility);
        usernameField.setVisible(!visibility);
        passwordField.setVisible(!visibility);
    }

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if(username.equals("Admin") && password.equals("admin123")){
            redirectToViewName("admin-panel.fxml","Admin Panel");
        }else{
            loginProblems.setText("Username or Password is incorect!");
        }
    }


    private void redirectToViewName(String viewName,String title) {
        try {
            FXMLLoader loader = new FXMLLoader(TableMateApplication.class
                    .getResource(viewName));
            Scene scene = new Scene(loader.load());

            Stage currentStage = (Stage) component.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("TableMate -"+title);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
