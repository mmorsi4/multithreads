package example;

import java.util.Stack;

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

public class Central extends Application {
    private Stage primaryStage;
    private AuthService auth = new AuthService(null,null);
    private PermissionService permission = new PermissionService(auth);
    private AdminService adminService = new AdminService(auth,permission);
    private CategoryService categoryService = new CategoryService(auth,permission);
    private ProductService productService = new ProductService(auth,permission,categoryService);
    private CustomerService customerService = new CustomerService(auth,permission,productService,null);
    private OrderService orderService = new OrderService(auth,permission,productService,customerService);

    @Override
    public void start(Stage primaryStage) {
        auth.setCustomerService(customerService);
        auth.setAdminService(adminService);
        customerService.setOrderService(orderService);

        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Multithreads");
        primaryStage.show();
        showLoginPage(); // Start with Scene 1
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public AuthService getAuth() {
        return auth;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PermissionService getPermission() {
        return permission;
    }

    public ProductService getProductService() {
        return productService;
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

    public void showChatListPage(){
        ChatListPage chatlistPage = new ChatListPage(this);
        primaryStage.setScene((chatlistPage.getScene(primaryStage)));
    }

    public void showChatPage(){
        ChatPage chatPage = new ChatPage(this);
        primaryStage.setScene((chatPage.getScene(primaryStage)));
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

