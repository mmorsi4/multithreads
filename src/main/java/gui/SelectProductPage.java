package gui;

import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;

import Entity.Product;

public class SelectProductPage {
    private final Central mainApp;
    private Product product; // Now used to store the refetched product
    private final boolean productorCart;

    public SelectProductPage(Central mainApp, Product originalProduct, boolean check) {
        this.mainApp = mainApp;
        this.productorCart = check;

        // Refetch the product from the database using the original product's ID
        this.product = mainApp.getProductService().get(originalProduct.getId());
        if (this.product == null) {
            // Handle the case where the product might have been deleted
            System.err.println("Error: Product not found!");
            // You might want to redirect back to the product list or show an error message
        }
    }

    public Scene getScene(Stage stage) {
        Font HM = Font.loadFont(getClass().getResourceAsStream("/fonts/HM.ttf"), 26);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

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


        // Product Details Layout (Updated to use the refetched product)
        HBox productLayout = createProductLayout();
        bp.setCenter(productLayout);

        // Return Button
        Button returnButton = createReturnButton();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(bp, returnButton);
        StackPane.setAlignment(returnButton, Pos.TOP_LEFT);
        StackPane.setMargin(returnButton, new Insets(100, 0, 0, 20));

        return new Scene(stackPane, 1366, 768);
    }

    private HBox createProductLayout() {
        Image productImage = loadImage(product.getImage(), "/assets/m.png", false); // Load product image or fallback
        ImageView productImageView = createImageView(productImage, 500);

        VBox productInfo = new VBox(20);
        productInfo.setAlignment(Pos.CENTER_LEFT);
        productInfo.getChildren().addAll(
                createText(product.getName(), 24, true, Color.WHITE),
                createText(product.getDescription(), 16, false, Color.WHITE),
                createText(product.getPrice() + " EGP", 20, true, Color.WHITE),
                createText("Quantity in Stock: " + product.getQuantity(), 16, false, Color.WHITE), // Now using the refetched product's quantity
                createAddToCartButton()
        );

        HBox productLayout = new HBox(50, productImageView, productInfo);
        productLayout.setAlignment(Pos.CENTER);
        productLayout.setPadding(new Insets(50));
        return productLayout;
    }

    private Button createAddToCartButton() {
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px;");
        addToCartButton.setCursor(Cursor.HAND);
        addToCartButton.setOnAction(e -> {
            System.out.println("Attempting to add product to cart: " + product.getName());

            try {
                mainApp.getCustomerService().addToCart(mainApp.getCustomerService().getLoggedInUser().getUsername(), product.getId());
                System.out.println("Product added to cart: " + product.getName());

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product added to cart!", ButtonType.OK);
                alert.show();
            } catch (Exception ex) {
                System.err.println("Error adding product to cart: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add product to cart.", ButtonType.OK);
                alert.show();
            }
        });
        return addToCartButton;
    }

    private Button createReturnButton() {
        Image returnImage = loadImage("/assets/back-button.png", "/assets/back-button.png", true);
        ImageView returnImageView = createImageView(returnImage, 40);

        Button returnButton = new Button();
        returnButton.setGraphic(returnImageView);
        returnButton.setStyle("-fx-background-color: transparent;");
        returnButton.setCursor(Cursor.HAND);
        returnButton.setOnAction(e -> {
            if (!productorCart) {
                mainApp.showProductPage(null);
            } else {
                mainApp.showCartPage();
            }
        });
        return returnButton;
    }

    private ImageView createImageView(Image image, double width) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private Image loadImage(String path, String fallback, boolean isReturn) {
        try {
            if(isReturn) return new Image(getClass().getResourceAsStream(path));
            return new Image(new File(path).toURI().toString());
        } catch (Exception e) {
            System.err.println("Error loading image, using fallback: " + fallback);
            return new Image(getClass().getResourceAsStream(fallback));
        }
    }

    private Text createText(String content, int fontSize, boolean bold, Color color) {
        Text text = new Text(content);
        text.setFill(color);
        text.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: " + (bold ? "bold" : "normal") + ";");
        return text;
    }
}