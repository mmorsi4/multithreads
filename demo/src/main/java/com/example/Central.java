package com.example;

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

public class Central extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        showLoginPage(); // Start with Scene 1
        primaryStage.setTitle("Multithreads");
        primaryStage.show();
    }

    public void showLoginPage() {
        LoginPage loginPage = new LoginPage(this);
        primaryStage.setScene(loginPage.getScene(primaryStage));
    }

    public void showRegisterPage(){
        RegisterPage registerPage = new RegisterPage(this);
        primaryStage.setScene(registerPage.getScene(primaryStage));
    }

    public void showCategoryPage() {
        CategoryPage categoryPage = new CategoryPage(this);
        primaryStage.setScene(categoryPage.getScene(primaryStage));
    }

    public void showProductPage() {
        ProductPage productPage = new ProductPage(this);
        primaryStage.setScene(productPage.getScene(primaryStage));
    }

    public void showSelectProductPage(boolean check) {
        SelectProductPage selectProductPage = new SelectProductPage(this, check);
        primaryStage.setScene(selectProductPage.getScene(primaryStage));
    }

    public void showCartPage(){
        CartPage cartPage = new CartPage(this);
        primaryStage.setScene((cartPage.getScene(primaryStage)));
    }

    public void showOrdersPage(){
        OrdersPage ordersPage = new OrdersPage(this);
        primaryStage.setScene((ordersPage.getScene(primaryStage)));
    }

    // Admin pages

    public void showAdminProductsPage() {
        AdminProductsPage adminProductsPage = new AdminProductsPage(this);
        primaryStage.setScene(adminProductsPage.getScene(primaryStage));
    }
    
    public void showAdminCategoriesPage() {
        AdminCategoriesPage adminCategoriesPage = new AdminCategoriesPage(this);
        primaryStage.setScene(adminCategoriesPage.getScene(primaryStage));
    }
    
    public void showAdminUsersPage() {
        AdminUsersPage adminUsersPage = new AdminUsersPage(this);
        primaryStage.setScene(adminUsersPage.getScene(primaryStage));
    }
    
    public void showAdminOrdersPage() {
        AdminOrdersPage adminOrdersPage = new AdminOrdersPage(this);
        primaryStage.setScene(adminOrdersPage.getScene(primaryStage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

