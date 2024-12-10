package Entity;

import java.util.ArrayList;

public class Customer extends User {
    private double balance;
    private String address;
    private Gender gender;
    private ArrayList<String> interests;
    private String cartId;
    
    public Customer(String username, String password, java.util.Date dateOfBirth, double balance, String address, Gender gender, ArrayList<String> interests){
        super(username, password, Role.customer, dateOfBirth);
        setBalance(balance);
        setGender(gender);
        setInterests(interests);
    }

    public Customer(Customer customer) {
        this(customer.getUsername(), customer.getPassword(), customer.getDateOfBirth(), customer.balance, customer.address, customer.gender, customer.interests);
        setCartId(customer.cartId);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance<0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null.");
        }
        this.gender = gender;
    }

    public ArrayList<String> getInterests() {
        return new ArrayList<>(interests);
    }

    public void setInterests(ArrayList<String> interests) {
        if (interests == null) {
            throw new IllegalArgumentException("Products cannot be null.");
        }

        for (String product : interests) {
            if (product == null || product.trim().isEmpty()) {
                throw new IllegalArgumentException("Product Id cannot be null or empty.");
            }
        }

        this.interests = new ArrayList<>(interests);
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        if (cartId == null || cartId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cart Id cannot be null or empty.");
        }

        this.cartId = cartId;
    }

    @Override
    public void setRole(Role role) {
        super.setRole(Role.customer);
    }

    @Override
    public String toString() {
        return super.toString()+"\nBalance: "+balance+"\nAddress: "+address+"\nGender: "+gender;
    }
}
