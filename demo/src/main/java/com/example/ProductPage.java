package com.example;

import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class ProductPage {

    private final Central mainApp;

    public ProductPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){

        // USE THESE LINES FOR EVERY NEW SCROLLABLE PAGE
        BorderPane bp = new BorderPane();
        ScrollPane sp = new ScrollPane(bp);
        sp.setFitToWidth(true);

        bp.setStyle("-fx-background-color: black;");

        // FORCE REFRESH AFTER PAGE TRANSITION
        //Platform.runLater(stage::sizeToScene); 

        // nav

        // nav

        Image logo = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setPreserveRatio(true);

        Button cartButton = new Button("CART");
        cartButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-text-fill: white; -fx-border-color: transparent;");
        Button productButton = new Button("PRODUCTS");
        productButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-text-fill: white; -fx-border-color: transparent;");
        Button categoryButton = new Button("CATEGORIES");
        categoryButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-text-fill: white; -fx-border-color: transparent;");

        HBox controlBox = new HBox(25);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(productButton, categoryButton, cartButton);

        HBox navbar = new HBox(50);
        navbar.getChildren().addAll(logoView, controlBox);
        navbar.setStyle("-fx-background-color: rgba(20, 20, 20, 1)");
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 15, 15, 15));

        bp.setTop(navbar);

        productButton.setCursor(Cursor.HAND);
        productButton.setOnMouseClicked(event -> {
            mainApp.showProductPage();
        });

        categoryButton.setCursor(Cursor.HAND);
        categoryButton.setOnMouseClicked(event -> {
            mainApp.showCategoryPage();
        });

        cartButton.setCursor(Cursor.HAND);
        cartButton.setOnMouseClicked(event -> {
            System.out.println("Clicked on m!"); // Replace with desired action
        });

        // content

        VBox mainLayout = new VBox(50);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox controlBar = new HBox(25);
        controlBar.setPadding(new Insets(15, 15, 15, 15));

        // Create the search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for products...");
        searchBar.setMaxWidth(400);

        // Create category filter dropdown
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Electronics", "Clothing", "Home", "Sports");
        categoryComboBox.setValue("All");

        // Create sort by dropdown
        ComboBox<String> sortByComboBox = new ComboBox<>();
        sortByComboBox.getItems().addAll("Price (Low to High)", "Price (High to Low)", "Name (A-Z)", "Name (Z-A)");
        sortByComboBox.setValue("Price (Low to High)");

        GridPane productGrid = new GridPane();
        productGrid.setHgap(50);  // Horizontal gap between columns
        productGrid.setVgap(50);  // Vertical gap between rows
        productGrid.setAlignment(Pos.CENTER);
        // Add products to the grid (Example)
        addProductToGrid(productGrid, "Product 1", "10.99", "/assets/m.png", 0, 0);
        addProductToGrid(productGrid, "Product 2", "15.99", "/assets/m.png", 1, 0);
        addProductToGrid(productGrid, "Product 3", "20.99", "/assets/m.png", 0, 1);
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png", 1, 1);
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png", 2, 0);

        controlBar.getChildren().addAll(searchBar, categoryComboBox, sortByComboBox);
        mainLayout.getChildren().addAll(controlBar, productGrid);
        bp.setCenter(mainLayout);

        return new Scene(sp, 1366, 768);

        // Rectangle roundrec = new Rectangle(200, 200);
        // roundrec.setFill(Color.TRANSPARENT);
        // roundrec.setStroke(Color.GRAY);
        // roundrec.setArcHeight(50);
        // roundrec.setArcWidth(50);
        // bp.setCenter(roundrec);

    }

    private void addProductToGrid(GridPane grid, String productName, String productPrice, String imagePath, int col, int row) {
        Image productImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(200);
        productImageView.setFitHeight(200);

        Label nameLabel = new Label(productName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label priceLabel = new Label(productPrice + "EGP");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #30588C; -fx-text-fill: white;");

        VBox productBox = new VBox(10, productImageView, nameLabel, priceLabel, addToCartButton);
        productBox.setAlignment(Pos.CENTER);

        grid.add(productBox, col, row);
    }
    
}