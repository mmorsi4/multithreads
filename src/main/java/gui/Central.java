package gui;

import java.util.ArrayList;
import java.util.Stack;

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

public class Central extends Application {
    private Stage primaryStage;
    private AuthService auth = new AuthService(null,null);
    private PermissionService permission = new PermissionService(auth);
    private AdminService adminService = new AdminService(auth,permission);
    private CategoryService categoryService = new CategoryService(auth,permission);
    private ProductService productService = new ProductService(auth,permission,categoryService);
    private CustomerService customerService = new CustomerService(auth,permission,productService,null);
    private OrderService orderService = new OrderService(auth,permission,productService,customerService);
    private ChatService chatService = new ChatService(auth, permission, adminService, customerService);
    private ArrayList<Client> clients = new ArrayList<>();

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
        // Getters and Setters
        public AdminService getAdminService() {
            return adminService;
        }
    
        public void setAdminService(AdminService adminService) {
            this.adminService = adminService;
        }
    
        public AuthService getAuth() {
            return auth;
        }
    
        public void setAuth(AuthService auth) {
            this.auth = auth;
        }
    
        public CategoryService getCategoryService() {
            return categoryService;
        }
    
        public void setCategoryService(CategoryService categoryService) {
            this.categoryService = categoryService;
        }
    
        public CustomerService getCustomerService() {
            return customerService;
        }
    
        public void setCustomerService(CustomerService customerService) {
            this.customerService = customerService;
        }
    
        public OrderService getOrderService() {
            return orderService;
        }
    
        public void setOrderService(OrderService orderService) {
            this.orderService = orderService;
        }
    
        public PermissionService getPermission() {
            return permission;
        }
    
        public void setPermission(PermissionService permission) {
            this.permission = permission;
        }
    
        public ProductService getProductService() {
            return productService;
        }
    
        public void setProductService(ProductService productService) {
            this.productService = productService;
        }
    
        public ChatService getChatService() {
            return chatService;
        }
    
        public void setChatService(ChatService chatService) {
            this.chatService = chatService;
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

    public void showProductPage(String SelectedCategory) {
        ProductPage productPage = new ProductPage(this);
        primaryStage.setScene(productPage.getScene(primaryStage, SelectedCategory));
    }

    public void showSelectProductPage(Product product, boolean check) {
        SelectProductPage selectProductPage = new SelectProductPage(this, product, check);
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


    public void showChatPage(User user){
        ChatPage chatPage = new ChatPage(this,user);
        primaryStage.setScene((chatPage.getScene(primaryStage)));
        clients.add(chatPage.getClient());
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

    /* 
    private void close(){
        if (clients != null) {
            for (Client client : clients) {
                System.out.println("biuuig");
                client.closeConnection();
            }
        }
    }
    
    @Override
    public void stop() {
        close();
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}

