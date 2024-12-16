package gui;

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

public class ChatListPage {

    private final Central mainApp;

    public ChatListPage(Central mainApp) {
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

        Text chatButton = new Text("CHAT");
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
    VBox mainLayout = new VBox(10);
    mainLayout.setAlignment(Pos.TOP_CENTER);

    // SAMPLE, USE THE DATABASE HERE PLS
    for (int i = 0; i < 10; i++) {
        String role = (i % 2 == 0) ? "Admin" : "Customer";  // Alternate between Admin and Customer
        HBox listing = createListing("User" + (i + 1), role);
        mainLayout.getChildren().addAll(listing, new Separator());
    }

    bp.setCenter(mainLayout);

    return new Scene(sp, 1366, 768);
}

private HBox createListing(String username, String role) {
    Text usernameLabel = new Text(username);
    usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    usernameLabel.setStyle("-fx-fill: white");

    Text roleLabel = new Text(role);
    roleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
    roleLabel.setStyle("-fx-fill: #006fff");

    // username and role vbox
    VBox userBox = new VBox(5, usernameLabel, roleLabel);
    userBox.setAlignment(Pos.CENTER_LEFT);
    userBox.setStyle("-fx-padding: 10px; -fx-border-radius: 10px;");

    // entire listing
    HBox listingBox = new HBox(10);
    listingBox.setAlignment(Pos.CENTER_LEFT);
    listingBox.setStyle("-fx-padding: 10px; -fx-background-color: black; -fx-border-radius: 10px;");
    
    // avatar
    ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/assets/userIcon.png")));
    HBox.setMargin(avatar, new Insets(0, 0, 0, 50));
    avatar.setFitWidth(50);
    avatar.setFitHeight(50);
    
    // add avatar and user box to the HBox
    listingBox.getChildren().addAll(avatar, userBox);

    listingBox.setOnMouseClicked(event -> mainApp.showChatPage());
    // TRANSITION TO CHAT SCENE
    
    listingBox.setCursor(Cursor.HAND);

    // change background color on hover
    listingBox.setOnMouseEntered(event -> listingBox.setStyle("-fx-padding: 10px; -fx-background-color: #333; -fx-border-radius: 10px;"));
    listingBox.setOnMouseExited(event -> listingBox.setStyle("-fx-padding: 10px; -fx-background-color: black; -fx-border-radius: 10px;"));

    return listingBox;
}
}
