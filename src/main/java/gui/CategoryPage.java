package gui;

import java.util.Stack;

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

public class CategoryPage {

    private final Central mainApp;

    public CategoryPage(Central mainApp) {
        this.mainApp = mainApp;
    }

    public Scene getScene(Stage stage){
        Font HM = Font.loadFont(getClass().getResourceAsStream("/fonts/HM.ttf"), 26);
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");

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

        StackPane gp = new StackPane();
        bp.setCenter(gp);
        gp.setAlignment(Pos.CENTER);

        StackPane w = new StackPane();
        Image wImage = new Image(getClass().getResource("/assets/w.png").toExternalForm());
        ImageView wImageView = new ImageView(wImage);
        w.getChildren().add(wImageView);
        wImageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.6));
        wImageView.setPreserveRatio(true);
        //w.widthProperty().bind(stage.widthProperty().multiply(0.6));

        HBox fourCatg = new HBox(25);

        StackPane x = new StackPane();
        Image xImage = new Image(getClass().getResource("/assets/x.png").toExternalForm());
        ImageView xImageView = new ImageView(xImage);
        Text xText = new Text("HOODIES");
        xText.setStyle("-fx-fill: white");
        xText.setFont(HM);
        StackPane.setAlignment(xText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(xText, new Insets(10));
        x.getChildren().addAll(xImageView, xText);

        StackPane y = new StackPane();
        Image yImage = new Image(getClass().getResource("/assets/y.png").toExternalForm());
        ImageView yImageView = new ImageView(yImage);
        Text yText = new Text("T-SHIRTS");
        yText.setStyle("-fx-fill: white");
        yText.setFont(HM);
        StackPane.setAlignment(yText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(yText, new Insets(10));
        y.getChildren().addAll(yImageView, yText);

        StackPane z = new StackPane();
        Image zImage = new Image(getClass().getResource("/assets/z.png").toExternalForm());
        ImageView zImageView = new ImageView(zImage);
        Text zText = new Text("TROUSERS");
        zText.setStyle("-fx-fill: white");
        zText.setFont(HM);
        StackPane.setAlignment(zText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(zText, new Insets(10));
        z.getChildren().addAll(zImageView, zText);

        StackPane m = new StackPane();
        Image mImage = new Image(getClass().getResource("/assets/m.png").toExternalForm());
        ImageView mImageView = new ImageView(mImage);
        Text mText = new Text("SHOES");
        mText.setStyle("-fx-fill: white");
        mText.setFont(HM);
        StackPane.setAlignment(mText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(mText, new Insets(10));
        m.getChildren().addAll(mImageView, mText);

        fourCatg.getChildren().addAll(x,y,z,m);
        fourCatg.setAlignment(Pos.CENTER);

        VBox allCatg = new VBox(25);
        allCatg.getChildren().addAll(w, fourCatg);
        allCatg.setAlignment(Pos.CENTER);
        gp.getChildren().add(allCatg);

        xImageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.6).subtract(3 * 25).divide(4));
        yImageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.6).subtract(3 * 25).divide(4));
        zImageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.6).subtract(3 * 25).divide(4));
        mImageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.6).subtract(3 * 25).divide(4));

        xImageView.setPreserveRatio(true);
        yImageView.setPreserveRatio(true);
        zImageView.setPreserveRatio(true);
        mImageView.setPreserveRatio(true);

        // CONTROLS

        // For xImageView
        xImageView.setCursor(Cursor.HAND);
        xImageView.setOnMouseClicked(event -> {
            System.out.println("Clicked on x!");
            mainApp.showProductPage("Hoodies");


        });

        // For yImageView
        yImageView.setCursor(Cursor.HAND);
        yImageView.setOnMouseClicked(event -> {
            System.out.println("Clicked on y!"); // Replace with desired action
            mainApp.showProductPage(("T-Shirts"));
        });

        // For zImageView
        zImageView.setCursor(Cursor.HAND);
        zImageView.setOnMouseClicked(event -> {
            System.out.println("Clicked on z!"); // Replace with desired action
            mainApp.showProductPage(("Trousers"));
        });

        // For mImageView
        mImageView.setCursor(Cursor.HAND);
        mImageView.setOnMouseClicked(event -> {
            System.out.println("Clicked on m!"); // Replace with desired action
            mainApp.showProductPage(("Shoes"));
        });


        //

        return new Scene(bp, 1366, 768);
    }

}