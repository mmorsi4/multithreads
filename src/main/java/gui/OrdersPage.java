package gui;

import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import Chat.Client;
import Services.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import Entity.*;
import Services.OrderService;

public class OrdersPage {

    private final Central mainApp;

    public OrdersPage(Central mainApp) {
        this.mainApp = mainApp;
        System.out.println(mainApp.getCustomerService().getOrders());
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

        // Create the TableView
        TableView<Order> tableView = new TableView<>();

        TableColumn<Order, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        // Create columns for the table
        TableColumn<Order, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer()));

        TableColumn<Order, String> paymentMethodColumn = new TableColumn<>("Payment Method");
        paymentMethodColumn.setCellValueFactory(
                cellData -> cellData.getValue().getPaymentMethod() == null ? new SimpleStringProperty("")
                        : new SimpleStringProperty(cellData.getValue().getPaymentMethod().toString()));

        TableColumn<Order, String> productsColumn = new TableColumn<>("Products");
        productsColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProducts().size())));

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        TableColumn<Order, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotal())));

        Button cancelButton = new Button("Cancel");

        cancelButton.setOnAction(event -> {
            Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                String orderId = selectedOrder.getId();
                try {
                    mainApp.getOrderService().cancel(orderId);
                    mainApp.showOrdersPage();
                } catch (RuntimeException e) {
                }
            }
        });

        


        // this changes the color and this is the only thing i understand lol
        tableView.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
            return row;
        });

        // Add columns to the table
        tableView.getColumns().addAll(idColumn, customerColumn, paymentMethodColumn, productsColumn, statusColumn, totalColumn);
        // Sample data for the table
        for (Order order : mainApp.getCustomerService().getOrders()) {
            tableView.getItems().add(order);
        }

        VBox vertical = new VBox(20);
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(cancelButton);
        vertical.getChildren().addAll(tableView, hbox);
        VBox.setMargin(hbox, new Insets(0, 0, 0, 15));
        bp.setCenter(vertical);

        return new Scene(sp, 1366, 768);

    }
}