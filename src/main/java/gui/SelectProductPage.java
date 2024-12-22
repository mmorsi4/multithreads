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
import Entity.Product;

public class SelectProductPage {
    private final Central mainApp;
    private final Product product; // The product to display
    private final boolean productorCart;

    public SelectProductPage(Central mainApp, Product product, boolean check) {
        this.mainApp = mainApp;
        this.product = product; // Pass the selected product
        this.productorCart = check; // Instance variable to avoid static misuse
    }

    public Scene getScene(Stage stage) {
        Font HM = Font.loadFont(getClass().getResourceAsStream("/fonts/HM.ttf"), 26);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // Navigation bar
        Image logo = new Image(getClass().getResource("/assets/multithreadsLogo.png").toExternalForm());
        ImageView logoView = createImageView(logo, 85);

        HBox navbar = createNavbar(logoView);
        bp.setTop(navbar);

        // Product Details Layout
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

    private HBox createNavbar(ImageView logoView) {
        Text cartButton = createNavButton("CART", () -> mainApp.showCartPage());
        Text productButton = createNavButton("PRODUCTS", () -> mainApp.showProductPage(null));
        Text categoryButton = createNavButton("CATEGORIES", () -> mainApp.showCategoryPage());
        Text ordersButton = createNavButton("ORDERS", () -> mainApp.showOrdersPage());
        Text chatButton = createNavButton("CHAT", () -> mainApp.showChatListPage());

        logoView.setOnMouseClicked(event -> {
            mainApp.getAuth().Logout();
            mainApp.showLoginPage();
        });

        HBox controlBox = new HBox(40, productButton, categoryButton, cartButton, ordersButton, chatButton);
        controlBox.setAlignment(Pos.CENTER);

        HBox navbar = new HBox(50, logoView, controlBox);
        navbar.setStyle("-fx-background-color: black;");
        navbar.setPadding(new Insets(15));
        return navbar;
    }

    private HBox createProductLayout() {
        Image productImage = loadImage("/assets/m.png", "/assets/m.png"); // Load product image or fallback
        ImageView productImageView = createImageView(productImage, 350);

        VBox productInfo = new VBox(20);
        productInfo.setAlignment(Pos.CENTER_LEFT);
        productInfo.getChildren().addAll(
                createText(product.getName(), 24, true, Color.WHITE),
                createText(product.getDescription(), 16, false, Color.WHITE),
                createText(product.getPrice() + " EGP", 20, true, Color.WHITE),
                createText("Quantity in Stock: " + product.getQuantity(), 16, false, Color.WHITE),
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
        Image returnImage = loadImage("/assets/back-button.png", "/assets/back-button.png");
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

    private Text createNavButton(String text, Runnable action) {
        Text navButton = new Text(text);
        navButton.setFill(Color.WHITE);
        navButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        navButton.setCursor(Cursor.HAND);
        navButton.setOnMouseClicked(e -> action.run());
        return navButton;
    }

    private ImageView createImageView(Image image, double width) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private Image loadImage(String path, String fallback) {
        try {
            return new Image(getClass().getResourceAsStream(path));
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
