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
        showSelectProductPage(); // Start with Scene 1
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

    public void showSelectProductPage() {
        SelectProductPage selectProductPage = new SelectProductPage(this);
        primaryStage.setScene(selectProductPage.getScene(primaryStage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

