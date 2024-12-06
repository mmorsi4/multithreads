package com.example;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

public class LoginPage {
    private final Central mainApp;

    public LoginPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){
        VBox layout = new VBox(); // Vertical layout with spacing
        layout.setStyle("-fx-background: black;");
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Logo Image
        Image logoImage = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(300);
        logoView.setPreserveRatio(true);

        // Username Field
        Text usernameLabel = new Text("Username");
        usernameLabel.setFill(Color.WHITE);  // Set the username label color to white
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");
        usernameField.setMaxWidth(300);

        // Password Field
        Text passwordLabel = new Text("Password");
        passwordLabel.setFill(Color.WHITE);  // Set the password label color to white
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");
        passwordField.setMaxWidth(300);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        Button signupButton = new Button("Don't have an account? Create one instead!");
        loginButton.setCursor(Cursor.HAND);
        signupButton.setCursor(Cursor.HAND);
        signupButton.setStyle("-fx-background-color: transparent; -fx-font-size: 12px; -fx-text-fill: #006fff; -fx-border-color: transparent;");
        

        VBox.setMargin(logoView, new Insets(0, 0, 40, 0)); // Margin for the logo
        VBox.setMargin(usernameField, new Insets(0, 0, 5, 0)); // Margin for username field
        VBox.setMargin(passwordField, new Insets(0, 0, 5, 0)); // Margin for password field
        VBox.setMargin(loginButton, new Insets(25, 0, 10, 0)); // Margin for login button
        VBox.setMargin(usernameLabel, new Insets(0, 0, 5, 0));
        VBox.setMargin(passwordLabel, new Insets(10, 0, 5, 0));

        Text warning = new Text("Invalid Credentials!");
        warning.setFill(Color.BLACK);

        VBox.setMargin(warning, new Insets(5, 0, 10, 0));

        // Login Button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.equals("admin") && password.equals("1234")) { // CHECK IN DATABASE
                mainApp.showCategoryPage();
            } else { // ERROR
                warning.setFill(Color.RED);
            }
        });

        signupButton.setOnAction(e -> {
            mainApp.showRegisterPage();
        });

        // Add components to layout
        layout.getChildren().addAll(logoView, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, warning, signupButton);
        return new Scene(layout, 1366, 768);
    }
}
