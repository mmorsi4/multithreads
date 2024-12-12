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

public class ChatPage {

    private final Central mainApp;

    public ChatPage(Central mainApp) {
        this.mainApp = mainApp;
        chatBox = new VBox(10);
        chatBox.setAlignment(Pos.TOP_CENTER);
    }

    private VBox chatBox;
    private TextField messageInput;
    private ScrollPane scrollPane;
    private Text currentUserText;

    public Scene getScene(Stage stage){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

        // // FOR ANY SCROLLABLE PAGE, USE THESE LINES:
        // ScrollPane sp = new ScrollPane(bp);
        // sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // remove horizontal bar
        // Platform.runLater(() -> sp.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        // // when the viewport loads in set its style to transparent so it doesn't affect scrollpane styling
        // sp.setFitToWidth(true); // extend the scrollpane on the entire view
        // sp.setStyle("-fx-background-color: black; -fx-border-color: transparent"); // make it black
        
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

        // Chat Box Container
        scrollPane = new ScrollPane(chatBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        Platform.runLater(() -> scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));
        scrollPane.setStyle("-fx-background-color: black;");

        // Return Button as an Image
        Image returnImage = new Image(getClass().getResource("/assets/back-button.png").toExternalForm());
        ImageView returnImageView = new ImageView(returnImage);
        returnImageView.setFitWidth(25);
        returnImageView.setPreserveRatio(true);
        Button returnButton = new Button();
        returnButton.setGraphic(returnImageView);
        returnButton.setStyle("-fx-background-color: transparent;");
        returnButton.setCursor(Cursor.HAND);
        returnButton.setOnAction(e -> {mainApp.showChatListPage();});        

        // User info at the top
        currentUserText = new Text("Chatting with: [User Name]");
        currentUserText.setFill(Color.WHITE);
        currentUserText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        HBox userInfoBox = new HBox(returnButton, currentUserText);
        userInfoBox.setAlignment(Pos.CENTER_LEFT);
        userInfoBox.setStyle("-fx-padding: 15px; -fx-background-color: #333;");

        // Message Input Box
        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.CENTER_LEFT);
        messageBox.setStyle("-fx-padding: 10px;");

        messageInput = new TextField();
        messageInput.setPromptText("Send a message...");
        messageInput.setPrefWidth(1280);
        messageInput.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #006fff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
        sendButton.setCursor(Cursor.HAND);

        sendButton.setOnMouseClicked(event -> sendMessage()); // send message on click send
        messageInput.setOnAction(event -> sendMessage()); // send message on click enter

        messageBox.getChildren().addAll(messageInput, sendButton);
        
        // Set bottom layout
        bp.setBottom(messageBox);
        bp.setCenter(scrollPane);
        bp.setTop(userInfoBox);

        return new Scene(bp, 1366, 768);
    }

    // Function to add message to the chat
    private void sendMessage() {
        String message = messageInput.getText();
        if (!message.isEmpty()) {

            HBox chatBubble = createChatBubble(message, true);
            // TRUE --> BLUE USER MESSAGE
            // FALSE --> PURPLE OTHER USER MESSAGE
            chatBox.getChildren().add(chatBubble);
            HBox.setMargin(chatBubble, new Insets(100, 100, 5, 5));

            messageInput.clear();

            // scroll to the bottom after sending the message
            Platform.runLater(() -> {
                chatBox.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    scrollPane.setVvalue(1.0); // 1 means bottom 0 means top, go to the bottom of the scrollable content
                });
            });
        }
    }

    // function to create a message bubble
    private HBox createChatBubble(String message, boolean isUserMessage) {

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-padding: 15px; -fx-font-size: 20px; -fx-background-color: " + (isUserMessage ? "#006fff" : "#97006e") + "; -fx-text-fill: white; -fx-background-radius: 30px;");
        
        HBox chatBubble = new HBox(10);
        chatBubble.setAlignment(isUserMessage ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        chatBubble.getChildren().add(messageLabel);
        HBox.setMargin(messageLabel, new Insets(5, 20, 0, 20));

        return chatBubble;
    }
}