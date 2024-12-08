package com.example;

import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;

public class OrdersPage {

    private final Central mainApp;

    public OrdersPage(Central mainApp) {
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

        // Create the TableView
        TableView<Order> tableView = new TableView<>();

        // Create columns for the table
        TableColumn<Order, String> customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());

        TableColumn<Order, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Order, String> paymentMethodColumn = new TableColumn<>("Payment Method");
        paymentMethodColumn.setCellValueFactory(cellData -> cellData.getValue().paymentMethodProperty());

        TableColumn<Order, String> productsColumn = new TableColumn<>("Products");
        productsColumn.setCellValueFactory(cellData -> cellData.getValue().productsProperty());

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        TableColumn<Order, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());

        // Extra column for Cancel button
        TableColumn<Order, Void> cancelColumn = new TableColumn<>("Cancel");
        cancelColumn.setCellFactory(col -> {
            TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                private final Button cancelButton = new Button("Cancel");

                {
                    cancelButton.setOnAction(event -> {
                        // Handle cancel button click
                        Order order = getTableView().getItems().get(getIndex());
                        System.out.println("Cancel Order: " + order.getCustomer());
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(cancelButton);
                    }
                }
            };
            return cell;
        });

        // this changes the color and this is the only thing i understand lol
        tableView.setRowFactory(tv -> {
            TableRow<OrdersPage.Order> row = new TableRow<>();
            row.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            return row;
        });

        // Add columns to the table
        tableView.getColumns().addAll(customerColumn, idColumn, paymentMethodColumn, productsColumn, statusColumn, totalColumn, cancelColumn);

        // Sample data for the table
        tableView.getItems().addAll(
            new Order("John Doe", "001", "Credit Card", "Product A, Product B", "Pending", "$50"),
            new Order("Jane Smith", "002", "PayPal", "Product C", "Shipped", "$30"),
            new Order("Alice Brown", "003", "Bank Transfer", "Product D", "Cancelled", "$40")
        );

        bp.setCenter(tableView);

        return new Scene(sp, 1366, 768);

    }

    // Order class representing each row of data
    public static class Order {
        private final SimpleStringProperty customer;
        private final SimpleStringProperty id;
        private final SimpleStringProperty paymentMethod;
        private final SimpleStringProperty products;
        private final SimpleStringProperty status;
        private final SimpleStringProperty total;

        public Order(String customer, String id, String paymentMethod, String products, String status, String total) {
            this.customer = new SimpleStringProperty(customer);
            this.id = new SimpleStringProperty(id);
            this.paymentMethod = new SimpleStringProperty(paymentMethod);
            this.products = new SimpleStringProperty(products);
            this.status = new SimpleStringProperty(status);
            this.total = new SimpleStringProperty(total);
        }

        public String getCustomer() {
            return customer.get();
        }

        public String getId() {
            return id.get();
        }

        public String getPaymentMethod() {
            return paymentMethod.get();
        }

        public String getProducts() {
            return products.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getTotal() {
            return total.get();
        }

        public SimpleStringProperty customerProperty() {
            return customer;
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public SimpleStringProperty paymentMethodProperty() {
            return paymentMethod;
        }

        public SimpleStringProperty productsProperty() {
            return products;
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public SimpleStringProperty totalProperty() {
            return total;
        }
    }

    
}