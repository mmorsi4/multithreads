package example;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RegisterPage {
    private final Central mainApp;

    public RegisterPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage) {
        GridPane layout = new GridPane(); 
        layout.setStyle("-fx-background-color: black;");  
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20));

        Text usernameLabel = new Text("Username:");
        usernameLabel.setFill(Color.WHITE);  
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");

        Text passwordLabel = new Text("Password:");
        passwordLabel.setFill(Color.WHITE);  
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");

        Text confirmPasswordLabel = new Text("Confirm Password:");
        confirmPasswordLabel.setFill(Color.WHITE);  
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");

        Text genderLabel = new Text("Gender:");
        genderLabel.setFill(Color.WHITE);

        ToggleGroup genderGroup = new ToggleGroup();

        RadioButton maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(genderGroup);
        maleRadio.setStyle("-fx-text-fill: white;");
        maleRadio.setSelected(true); // Set default selection to Male

        RadioButton femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(genderGroup);
        femaleRadio.setStyle("-fx-text-fill: white;");

        HBox genderBox = new HBox(10, maleRadio, femaleRadio);
        genderBox.setAlignment(Pos.CENTER_LEFT);

        Text dobLabel = new Text("Date of Birth (dd-MM-yyyy):");
        dobLabel.setFill(Color.WHITE);  
        TextField dobField = new TextField();
        dobField.setPromptText("Enter your birthdate");
        dobField.setMaxWidth(300);
        dobField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");

        Text addressLabel = new Text("Address:");
        addressLabel.setFill(Color.WHITE);  
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");
        addressField.setPrefWidth(300);
        addressField.setPrefHeight(60);  // Same height as the TextArea
        addressField.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white; -fx-border-color: #97006e;");

        Text validationWarning = new Text();
        validationWarning.setFill(Color.RED);

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        signUpButton.setOnAction(e -> {
            validationWarning.setText("");  

            if (usernameField.getText().isEmpty()) {
                validationWarning.setText("Username is required!");
                return;
            }

            if (passwordField.getText().isEmpty()) {
                validationWarning.setText("Password is required!");
                return;
            }
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                validationWarning.setText("Passwords do not match!");
                return;
            }

            if (genderGroup.getSelectedToggle() == null) {
                validationWarning.setText("Gender is required!");
                return;
            }

            try {
                LocalDate dob = LocalDate.parse(dobField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (Exception ex) {
                validationWarning.setText("Invalid Date of Birth format (dd-MM-yyyy)!");
                return;
            }

            if (addressField.getText().isEmpty()) {
                validationWarning.setText("Address is required!");
                return;
            }

            mainApp.showCategoryPage();
        });

        Button loginButton = new Button("Already have an account? Log in instead!");
        loginButton.setCursor(Cursor.HAND);
        loginButton.setStyle("-fx-background-color: transparent; -fx-font-size: 12px; -fx-text-fill: #006fff; -fx-border-color: transparent;");

        loginButton.setOnAction(e -> {
            mainApp.showLoginPage();
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: black;");
        vbox.getChildren().addAll(layout, loginButton);

        layout.add(usernameLabel, 0, 0);
        layout.add(usernameField, 1, 0);
        layout.add(passwordLabel, 0, 1);
        layout.add(passwordField, 1, 1);
        layout.add(confirmPasswordLabel, 0, 2);
        layout.add(confirmPasswordField, 1, 2);
        layout.add(genderLabel, 0, 3);
        layout.add(genderBox, 1, 3);
        layout.add(dobLabel, 0, 4);
        layout.add(dobField, 1, 4);
        layout.add(addressLabel, 0, 5);
        layout.add(addressField, 1, 5);
        layout.add(signUpButton, 1, 6);
        layout.add(validationWarning, 1, 7);

        return new Scene(vbox, 1366, 768);
    }
}
