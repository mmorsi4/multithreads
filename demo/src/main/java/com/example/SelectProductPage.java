package com.example;

import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class SelectProductPage {
    private final Central mainApp;

    public SelectProductPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage) {
        Font HM = Font.loadFont(getClass().getResourceAsStream("/fonts/HM.ttf"), 26);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

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
        productButton.setOnMouseClicked(event -> mainApp.showProductPage());
        categoryButton.setCursor(Cursor.HAND);
        categoryButton.setOnMouseClicked(event -> mainApp.showCategoryPage());
        cartButton.setCursor(Cursor.HAND);
        cartButton.setOnMouseClicked(event -> System.out.println("Clicked on CART!"));

        // Return Button as an Image
        Image returnImage = new Image(getClass().getResource("/assets/back-button.png").toExternalForm());
        ImageView returnImageView = new ImageView(returnImage);
        returnImageView.setFitWidth(40);
        returnImageView.setPreserveRatio(true);

        Button returnButton = new Button();
        returnButton.setGraphic(returnImageView);
        returnButton.setStyle("-fx-background-color: transparent;");
        returnButton.setCursor(Cursor.HAND);
        returnButton.setOnAction(e -> mainApp.showProductPage()); // Replace with actual navigation logic

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(bp);

        StackPane.setAlignment(returnButton, Pos.TOP_LEFT);
        StackPane.setMargin(returnButton, new Insets(100, 0, 0, 20)); // Adjust margins as needed
        stackPane.getChildren().add(returnButton);

        HBox productLayout = new HBox(50);
        productLayout.setAlignment(Pos.CENTER);
        productLayout.setPadding(new Insets(50));

        Image productImage = new Image(getClass().getResource("/assets/m.png").toExternalForm());
        ImageView imageView = new ImageView(productImage);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        VBox productInfo = new VBox(20);
        productInfo.setAlignment(Pos.CENTER_LEFT);

        Text productTitle = new Text("Product Title");
        productTitle.setFill(Color.WHITE);
        productTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Text productDescription = new Text("This is a sample product description. It gives details about the product.");
        productDescription.setFill(Color.WHITE);
        productDescription.setStyle("-fx-font-size: 16px;");

        Text productPrice = new Text("$49.99");
        productPrice.setFill(Color.WHITE);
        productPrice.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Text productStock = new Text("Quantity in Stock: 25");
        productStock.setFill(Color.WHITE);
        productStock.setStyle("-fx-font-size: 16px;");

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        addToCartButton.setCursor(Cursor.HAND);

        addToCartButton.setOnAction(e -> System.out.println("Product added to cart!"));

        productInfo.getChildren().addAll(productTitle, productDescription, productPrice, productStock, addToCartButton);
        productLayout.getChildren().addAll(imageView, productInfo);
        bp.setCenter(productLayout);

        return new Scene(stackPane, 1366, 768);
    }
}
