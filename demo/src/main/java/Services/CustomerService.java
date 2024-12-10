package Services;
import java.util.*;

import Entity.*;


public class CustomerService extends EntityService<Customer> {
    private PermissionService permission;
    private ProductService productService;
    private OrderService orderService;

    public CustomerService(AuthService authService, PermissionService permission, ProductService productService, OrderService orderService) {
        super("customers", Customer.class, authService);
        this.permission = permission;
        this.productService = productService;
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

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String create(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender) {        
        if (permission.hasPermission("customers", "create")||getLoggedInUser()==null) {
            if (getEntityDAO().getIndex(username)!=-1) {
                throw new IllegalArgumentException("This username is used");
            }

            Customer user = new Customer(username,password,dateOfBirth,balance,address,gender,new ArrayList<>());    
            getEntityDAO().add(user);
            String cartId = orderService.create(username, new HashMap<>(), null, Status.draft);
            user.setCartId(cartId);
            getEntityDAO().update(user);
            return username;
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String username) {
        if (permission.hasPermission("customers", "delete")) {
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }
            this.getEntityDAO().delete(username);
            orderService.delete(customer.getCartId());
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Customer get(String username){
        if (getLoggedInUser() == null) {
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                return null;
            }
            return new Customer(customer);
        }
        
        if (permission.hasPermission("customers","retrieve")|| getLoggedInUser().equals(getEntityDAO().get(username))) {
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                return null;
            }
            return new Customer(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Customer> getAll(){
        if (permission.hasPermission("customers","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String username,String parameter ,T newData) {
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))) {
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }

            switch (parameter.toLowerCase()) {
                case "password":
                    customer.setPassword((String)newData);
                    break;
                case "balance":
                    customer.setBalance((double)newData);
                    break;
                case "address":
                    customer.setAddress((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addToCart(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }

            orderService.addItem(customer.getCartId(), productId);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeFromCart(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }
            orderService.deleteItem(customer.getCartId(), productId);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Map<String, Integer> getCartProducts(String username){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }
            return orderService.get(customer.getCartId()).getProducts();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }
            ArrayList<String> products = customer.getInterests();
            if (products.contains(productId)) {
                throw new RuntimeException("This product is already in interests");
            }
            products.add(productId);
            customer.setInterests(products);
            getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void removeInterest(String username, String productId){
        if (permission.hasPermission("customers", "update")||getLoggedInUser().equals(getEntityDAO().get(username))){
            productService.get(productId);
            Customer customer = getEntityDAO().get(username);
            if (customer == null) {
                throw new IllegalArgumentException("This customer doesn't exist.");  
            }
            ArrayList<String> products = customer.getInterests();
            products.remove(productId);
            customer.setInterests(products);
            getEntityDAO().update(customer);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
