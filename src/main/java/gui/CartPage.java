package gui;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import Entity.*;
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

public class CartPage {

    private final Central mainApp;
    private List<VBox> allProducts = new ArrayList<>();

    public CartPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        ScrollPane sp = new ScrollPane(bp);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        // when the viewport loads in set its style to transparent so it doesn't affect
        // scrollpane styling
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

        productButton.setCursor(Cursor.HAND);
        productButton.setOnMouseClicked(event -> {
            mainApp.showProductPage(null);
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

        chatButton.setCursor(Cursor.HAND);
        chatButton.setOnMouseClicked(event -> {
            mainApp.showChatListPage();
        });

        logoView.setCursor(Cursor.HAND);
        logoView.setOnMouseClicked(event -> {
            mainApp.getAuth().Logout();
            mainApp.showLoginPage();
        });

        // content

        VBox mainLayout = new VBox(50);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        HBox controlBar = new HBox(25);
        controlBar.setPadding(new Insets(30, 15, 0, 70));

        // Create the search bar
        /*
         * TextField searchBar = new TextField();
         * searchBar.setPromptText("Search for products...");
         * searchBar.setPrefWidth(250);
         * 
         * // Create category filter dropdown
         * ComboBox<String> categoryComboBox = new ComboBox<>();
         * categoryComboBox.getItems().addAll("All", "Hoodies", "T-Shirts", "Trousers",
         * "Shoes");
         * categoryComboBox.setValue("All");
         * 
         * // Create sort by dropdown
         * ComboBox<String> sortByComboBox = new ComboBox<>();
         * sortByComboBox.getItems().addAll("Price (Low to High)",
         * "Price (High to Low)", "Name (A-Z)", "Name (Z-A)");
         * sortByComboBox.setValue("Price (Low to High)");
         */

        FlowPane productGrid = new FlowPane();
        productGrid.setHgap(50); // Horizontal gap between columns
        productGrid.setAlignment(null);
        productGrid.setAlignment(Pos.CENTER);

        try {
            // Get the products in the cart as a Map (productId -> quantity)
            Map<String, Integer> cartMap = mainApp.getCustomerService()
                    .getCartProducts(mainApp.getCustomerService().getLoggedInUser().getUsername());

            // List to store the products in the cart
            List<Product> products = new ArrayList<>();

            // Loop through the cart map and fetch products
            for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
                String productId = entry.getKey(); // The product ID (key in the map)
                Integer quantity = entry.getValue(); // The quantity of the product

                // Retrieve the product from the product service (assuming you have a getProduct
                // method)
                Product product = mainApp.getProductService().get(productId);

                if (product != null) {
                    // You can modify the product object to include quantity information if needed
                    products.add(product); // Add product to the list
                }
            }

            // Add products to the grid
            for (Product product : products) {
                addProductToGrid(productGrid, product); // Add each product to the grid
            }

        } catch (RuntimeException e) {
            System.err.println("Error getting products: " + e.getMessage());
            // Optionally, display a message in the UI for the user
            Label errorLabel = new Label("Failed to load products.");
            errorLabel.setTextFill(Color.RED);
            productGrid.getChildren().add(errorLabel);
        }

        // controlBar.getChildren().addAll(searchBar, categoryComboBox, sortByComboBox);
        mainLayout.getChildren().addAll(controlBar, productGrid);
        bp.setCenter(mainLayout);

        // Add "Order" button and payment method dropdown at the bottom
        HBox orderSection = new HBox(20);
        orderSection.setAlignment(Pos.BOTTOM_RIGHT);
        orderSection.setPadding(new Insets(30, 30, 30, 30)); // Padding at the bottom

        // Validation
        Text warning = new Text("");
        warning.setFill(Color.RED);

        // Payment method dropdown
        // Payment method dropdown
        ComboBox<String> paymentMethodComboBox = new ComboBox<>();
        paymentMethodComboBox.setMaxWidth(200);

        // Populate with payment methods
        for (PaymentMethod method : PaymentMethod.values()) {
            paymentMethodComboBox.getItems().add(method.name());
        }

        paymentMethodComboBox.setValue("Select Payment Method.."); // Default option

        // "Order" Button
        Button orderButton = new Button("Order");
        orderButton.setStyle(
                "-fx-background-color: #28a745; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 16px;");
        orderButton.setCursor(Cursor.HAND);
        orderButton.setOnMouseClicked(event -> {
            if (paymentMethodComboBox.getValue() == "Select Payment Method..") {
                warning.setText("Please select a payment method!");
            } else {
                String cartId = mainApp.getCustomerService().get(mainApp.getCustomerService().getLoggedInUser().getUsername()).getCartId();
                try {
                    String orderId = mainApp.getOrderService().convertToOrder(cartId);
                    mainApp.getOrderService().pay(orderId, PaymentMethod.valueOf(paymentMethodComboBox.getValue()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeight(600);
                    alert.setTitle("Order Confirmation");
                    alert.setHeaderText("ORDER ID: " + cartId);
                    alert.setContentText("Your order has been placed successfully.\n"
                            + "\nYou can expect your items to be shipped within the next 2-3 business days.");
                    warning.setText("");
                    alert.showAndWait();
                    mainApp.showCategoryPage();
                } catch (IllegalArgumentException e) {
                    warning.setText("Your balance is not enough");
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    warning.setText("Cart is empty!");
                }
            }
        });

        orderSection.getChildren().addAll(warning, paymentMethodComboBox, orderButton);

        // Add order section to the bottom of the page
        bp.setBottom(orderSection);

        return new Scene(sp, 1366, 768);

    }

    private void addProductToGrid(FlowPane grid, Product product) {
        Image productImage;
        try {
            productImage = new Image(getClass().getResourceAsStream(product.getImage()));
        } catch (Exception e) {

            productImage = new Image(getClass().getResourceAsStream("/assets/m.png")); // Fallback image
        }
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(200);
        productImageView.setFitHeight(200);

        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.WHITE);

        Label priceLabel = new Label(product.getPrice() + "EGP");
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        HBox buttonLine = new HBox(10);
        buttonLine.setAlignment(Pos.CENTER);

        Button showProductButton = new Button("Show Product");
        showProductButton.setCursor(Cursor.HAND);
        showProductButton.setOnMouseClicked(e -> {
            String productId = product.getId(); // Directly get the product ID
            Product selectedProduct = mainApp.getProductService().get(productId);
            mainApp.showSelectProductPage(selectedProduct, false); // Show the product page
        });
        showProductButton.setStyle(
                "-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;");

        Button removeButton = new Button("Remove");
        removeButton.setCursor(Cursor.HAND);
        removeButton.setStyle(
                "-fx-background-color: #ff0000; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px; -fx-font-size: 14px;");
        removeButton.setOnMouseClicked(e -> {
            // Remove product from cart logic
            String productId = product.getId(); // Get the ID of the product to remove
            String username = mainApp.getCustomerService().getLoggedInUser().getUsername(); // Get the logged-in user's
                                                                                            // username

            // Call the service to remove the product
            mainApp.getCustomerService().removeFromCart(username, productId);

            // Remove the product from the UI by accessing the productBox and removing it
            // from the grid
            grid.getChildren().removeIf(node -> node instanceof VBox && node.getUserData() == product);

            // Optionally, you could refresh the cart or show a success message
        });

        buttonLine.getChildren().addAll(showProductButton, removeButton);

        VBox productBox = new VBox(10);
        productBox.setAlignment(Pos.CENTER);
        productBox.getChildren().addAll(productImageView, nameLabel, priceLabel, buttonLine);

        // Store productBox as user data so it can be easily accessed later
        productBox.setUserData(product);

        grid.getChildren().add(productBox);
    }

}
