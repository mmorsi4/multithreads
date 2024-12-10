package example;

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
import javafx.scene.paint.*;

public class ProductPage {

    private final Central mainApp;

    public ProductPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        // when the viewport loads in set its style to transparent so it doesn't affect scrollpane styling
        sp.setFitToWidth(true); // extend the scrollpane on the entire view
        sp.setStyle("-fx-background-color: black; -fx-border-color: transparent"); // make it black
        
        // nav

        Image logo = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(85);
        logoView.setPreserveRatio(true);

        Text cartButton = new Text("CART");
        cartButton.setFill(Color.WHITE);
        cartButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text productButton = new Text("PRODUCTS");
        productButton.setFill(Color.WHITE);
        productButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text categoryButton = new Text("CATEGORIES");
        categoryButton.setFill(Color.WHITE);
        categoryButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Text ordersButton = new Text("ORDERS");
        ordersButton.setFill(Color.WHITE);
        ordersButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox controlBox = new HBox(40);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(productButton, categoryButton, cartButton, ordersButton);

        HBox navbar = new HBox(50);
        navbar.getChildren().addAll(logoView, controlBox);
        navbar.setStyle("-fx-background-color: black;");
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 15, 15, 15));

        Image navbarDeco = new Image(getClass().getResource("/assets/navDeco.png").toExternalForm());
        ImageView navbarDecoView = new ImageView(navbarDeco);
        VBox barAndDeco = new VBox();
        barAndDeco.getChildren().addAll(navbar, navbarDecoView);

        bp.setTop(barAndDeco);

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
            mainApp.showCartPage();
        });

        ordersButton.setCursor(Cursor.HAND);
        ordersButton.setOnMouseClicked(event -> {
            mainApp.showOrdersPage();
        });

        logoView.setCursor(Cursor.HAND);
        logoView.setOnMouseClicked(event -> {
            mainApp.showLoginPage();
        });

        // content

        VBox mainLayout = new VBox(50);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox controlBar = new HBox(25);
        controlBar.setPadding(new Insets(30, 15, 0, 70));

        // Create the search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for products...");
        searchBar.setPrefWidth(250);

        // Create category filter dropdown
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Hoodies", "T-Shirts", "Trousers", "Shoes");
        categoryComboBox.setValue("All");

        // Create sort by dropdown
        ComboBox<String> sortByComboBox = new ComboBox<>();
        sortByComboBox.getItems().addAll("Price (Low to High)", "Price (High to Low)", "Name (A-Z)", "Name (Z-A)");
        sortByComboBox.setValue("Price (Low to High)");

        FlowPane productGrid = new FlowPane();
        productGrid.setHgap(50);  // Horizontal gap between columns
        productGrid.setAlignment(null);
        productGrid.setAlignment(Pos.CENTER);
        // Add products to the grid (Example)
        addProductToGrid(productGrid, "Product 1", "10.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 2", "15.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 3", "20.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png");
        addProductToGrid(productGrid, "Product 4", "25.99", "/assets/m.png");

        controlBar.getChildren().addAll(searchBar, categoryComboBox, sortByComboBox);
        mainLayout.getChildren().addAll(controlBar, productGrid);
        bp.setCenter(mainLayout);

        return new Scene(sp, 1366, 768);

    }

    private void addProductToGrid(FlowPane grid, String productName, String productPrice, String imagePath) {
        Image productImage = new Image(getClass().getResourceAsStream(imagePath));
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(200);
        productImageView.setFitHeight(200);

        Label nameLabel = new Label(productName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        Label priceLabel = new Label(productPrice + "EGP");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Button showProductButton = new Button("Show Product");
        showProductButton.setCursor(Cursor.HAND);
        showProductButton.setOnMouseClicked(e -> {
            mainApp.showSelectProductPage(false);
        });
        showProductButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;");

        VBox productBox = new VBox(10, productImageView, nameLabel, priceLabel, showProductButton);
        productBox.setAlignment(Pos.CENTER);

        grid.getChildren().add(productBox);
        FlowPane.setMargin(productBox, new Insets(0, 0, 50, 0));

    }
    
}