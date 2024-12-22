package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Entity.Product;

public class ProductPage {

    private final Central mainApp;
    private List<VBox> allProducts = new ArrayList<>(); // Store all product boxes
    private FlowPane productGrid; // Store the product grid globally

    public ProductPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage, String SelectedCategory) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Platform.runLater(() -> {
            try {
                sp.lookup(".viewport").setStyle("-fx-background-color: transparent;");
            } catch (NullPointerException e) {
                System.err.println("Error setting viewport style: " + e.getMessage());
            }
        });
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: black; -fx-border-color: transparent");

        // Navigation Bar
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
        Text chatButton = new Text("CHAT");
        chatButton.setFill(Color.WHITE);
        chatButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox controlBox = new HBox(40);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.getChildren().addAll(productButton, categoryButton, cartButton, ordersButton, chatButton);

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

        // Navigation Actions
        productButton.setCursor(Cursor.HAND);
        productButton.setOnMouseClicked(event -> mainApp.showProductPage(null));
        categoryButton.setCursor(Cursor.HAND);
        categoryButton.setOnMouseClicked(event -> mainApp.showCategoryPage());
        cartButton.setCursor(Cursor.HAND);
        cartButton.setOnMouseClicked(event -> mainApp.showCartPage());
        ordersButton.setCursor(Cursor.HAND);
        ordersButton.setOnMouseClicked(event -> mainApp.showOrdersPage());
        chatButton.setCursor(Cursor.HAND);
        chatButton.setOnMouseClicked(event -> mainApp.showChatListPage());
        logoView.setCursor(Cursor.HAND);
        logoView.setOnMouseClicked(event -> {
            mainApp.getAuth().Logout();
            mainApp.showLoginPage();
        });

        // Main Content
        VBox mainLayout = new VBox(50);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox controlBar = new HBox(25);
        controlBar.setPadding(new Insets(30, 15, 0, 70));

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for products...");
        searchBar.setPrefWidth(250);

        // Category Filter Dropdown
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Hoodies", "T-Shirts", "Trousers", "Shoes");

        // Sort By Dropdown
        ComboBox<String> sortByComboBox = new ComboBox<>();
        sortByComboBox.getItems().addAll("Price (Low to High)", "Price (High to Low)", "Name (A-Z)", "Name (Z-A)");

        // Product Grid
        productGrid = new FlowPane();
        productGrid.setHgap(50);
        productGrid.setAlignment(Pos.CENTER);

        // Load Products and Populate Grid
        try {
            List<Product> products = mainApp.getProductService().getAll();
            System.out.println("Retrieved products: " + products.size());
            allProducts.clear();
            for (Product product : products) {
                VBox productBox = createProductBox(product);
                allProducts.add(productBox);
            }
            productGrid.getChildren().addAll(allProducts);
        } catch (RuntimeException e) {
            System.err.println("Error getting products: " + e.getMessage());
            Label errorLabel = new Label("Failed to load products.");
            errorLabel.setTextFill(Color.RED);
            productGrid.getChildren().add(errorLabel);
        }

        // Set Default Values for Dropdowns
        Platform.runLater(() -> {
            if (SelectedCategory == null) {
                categoryComboBox.setValue("All");
            } else {
                categoryComboBox.setValue(SelectedCategory);
            }
            sortByComboBox.setValue("Price (Low to High)");
        });

        // Control Bar and Main Layout Setup
        controlBar.getChildren().addAll(searchBar, categoryComboBox, sortByComboBox);
        mainLayout.getChildren().addAll(controlBar, productGrid);
        bp.setCenter(mainLayout);

        // Event Handlers for Search, Sort, and Filter
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filterProducts(categoryComboBox.getValue(), newValue, sortByComboBox.getValue()));
        sortByComboBox.setOnAction(event ->
                filterProducts(categoryComboBox.getValue(), searchBar.getText(), sortByComboBox.getValue()));
        categoryComboBox.setOnAction(event ->
                filterProducts(categoryComboBox.getValue(), searchBar.getText(), sortByComboBox.getValue()));

        return new Scene(sp, 1366, 768);
    }

    // Filter Products Based on Search, Category, and Sort
    private void filterProducts(String selectedCategory, String searchText, String selectedSort) {
        productGrid.getChildren().clear();
        List<VBox> filteredProducts = new ArrayList<>();

        for (VBox productBox : allProducts) {
            Label nameLabel = (Label) productBox.getChildren().get(1);
            Label categoryLabel = (Label) productBox.getChildren().get(4);

            boolean matchesSearch = searchText == null || searchText.isEmpty() ||
                    nameLabel.getText().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesCategory = selectedCategory.equals("All") || categoryLabel.getText().equals(selectedCategory);

            if (matchesSearch && matchesCategory) {
                filteredProducts.add(productBox);
            }
        }

        sortProducts(filteredProducts, selectedSort);
        productGrid.getChildren().addAll(filteredProducts);
    }

    // Sort Products Based on Selected Option
    private void sortProducts(List<VBox> products, String selectedSort) {
        switch (selectedSort) {
            case "Price (Low to High)":
                products.sort((p1, p2) -> {
                    Label priceLabel1 = (Label) p1.getChildren().get(2);
                    Label priceLabel2 = (Label) p2.getChildren().get(2);
                    double price1 = Double.parseDouble(priceLabel1.getText().replace("EGP", "").trim());
                    double price2 = Double.parseDouble(priceLabel2.getText().replace("EGP", "").trim());
                    return Double.compare(price1, price2);
                });
                break;
            case "Price (High to Low)":
                products.sort((p1, p2) -> {
                    Label priceLabel1 = (Label) p1.getChildren().get(2);
                    Label priceLabel2 = (Label) p2.getChildren().get(2);
                    double price1 = Double.parseDouble(priceLabel1.getText().replace("EGP", "").trim());
                    double price2 = Double.parseDouble(priceLabel2.getText().replace("EGP", "").trim());
                    return Double.compare(price2, price1);
                });
                break;
            case "Name (A-Z)":
                products.sort((p1, p2) -> {
                    Label nameLabel1 = (Label) p1.getChildren().get(1);
                    Label nameLabel2 = (Label) p2.getChildren().get(1);
                    return nameLabel1.getText().compareToIgnoreCase(nameLabel2.getText());
                });
                break;
            case "Name (Z-A)":
                products.sort((p1, p2) -> {
                    Label nameLabel1 = (Label) p1.getChildren().get(1);
                    Label nameLabel2 = (Label) p2.getChildren().get(1);
                    return nameLabel2.getText().compareToIgnoreCase(nameLabel1.getText());
                });
                break;
        }
    }

    // Create Product Box for Display
    private VBox createProductBox(Product product) {
        Image productImage;
        try {
            productImage = new Image("/assets/m.png");
        } catch (Exception e) {
            productImage = new Image(getClass().getResourceAsStream("/assets/m.png"));
        }
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(200);
        productImageView.setFitHeight(200);

        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        Label priceLabel = new Label(product.getPrice() + " EGP");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        priceLabel.setTextFill(Color.LIGHTGRAY);

        Button showProductButton = new Button("Show Product");
        showProductButton.setCursor(Cursor.HAND);
        showProductButton.setOnAction(e -> {
            Product selectedProduct = mainApp.getProductService().get(product.getId());
            if (selectedProduct != null) {
                mainApp.showSelectProductPage(selectedProduct, false);
            } else {
                System.err.println("Error: Product not found!");
            }
        });

        showProductButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;");

        String categoryName = CATEGORY_MAP.getOrDefault(product.getCategoryId(), "Unknown");
        Label categoryLabel = new Label(categoryName);
        categoryLabel.setVisible(false);

        VBox productBox = new VBox(10, productImageView, nameLabel, priceLabel, showProductButton, categoryLabel);
        productBox.setAlignment(Pos.CENTER);
        FlowPane.setMargin(productBox, new Insets(0, 0, 50, 0));

        return productBox;
    }

    private static final Map<String, String> CATEGORY_MAP = Map.of(
            "1", "Hoodies",
            "2", "T-Shirts",
            "3", "Trousers",
            "4", "Shoes"
    );
}