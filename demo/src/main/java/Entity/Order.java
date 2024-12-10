package Entity;

import java.util.*;

public class Order implements Entity {
    private String id;
    private String customer;
    private Map<String, Integer> products;
    private double total;
    private PaymentMethod paymentMethod;
    private Status status;

    public Order(String id, String customer, Map<String, Integer> products, double total, PaymentMethod paymentMethod, Status status) {
        setId(id);
        setCustomer(customer);
        setProducts(products);
        setTotal(total);
        setPaymentMethod(paymentMethod);
        setStatus(status);
    }

    public Order(Order order){
        this(order.id, order.customer, order.products, order.total, order.paymentMethod, order.status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        if (customer == null || customer.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer Id cannot be null or empty.");
        }
        this.customer = customer;
    }

    public Map<String, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public void addProduct(String productId){
        if (products.get(productId)==null) {
            products.put(productId, 1);
        }else {
            products.put(productId, products.get(productId)+1);
        } 
    }
    
    public void removeProduct(String productId){
        if (products.get(productId)==null) {
            throw new IllegalArgumentException("this product is not in the order");
        }

        if (products.get(productId)==1) {
            products.remove(productId);
        }else {
            products.put(productId, products.get(productId)-1);
        } 
    }

    public void setProducts(Map<String, Integer> products) {
        if (products == null) {
            throw new IllegalArgumentException("Products cannot be null.");
        }
        
        for (Map.Entry<String, Integer>entry : products.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new IllegalArgumentException("Product Id cannot be null or empty.");
            }
            if (entry.getValue() < 1) {
                throw new IllegalArgumentException("Quantity can't be less than 1.");
            }
        }

        this.products = new HashMap<>(products);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total<0) {
            throw new IllegalArgumentException("Total cannot be negative.");
        }
        this.total = total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    @Override
    public String getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            return this.id == ((Order) obj).id;
        }
        return false;
    }
}