package Entity;

import java.util.ArrayList;

public class Category implements Entity {
    private String id;
    private String name;
    private String description;
    private ArrayList<String> products;

    public Category(String id, String name, String description, ArrayList<String> products){
        setId(id); 
        setName(name); 
        setDescription(description); 
        setProducts(products); 
    }

    public Category(Category category){
        this(category.id, category.name, category.description, category.products);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name cannot exceed 50 characters.");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Description cannot exceed 500 characters.");
        }
        this.description = description;
    }

    public ArrayList<String> getProducts() {
        return new ArrayList<>(products); 
    }

    public void setProducts(ArrayList<String> products) {
        if (products == null) {
            throw new IllegalArgumentException("Products list cannot be null.");
        }
        for (String product : products) {
            if (product == null || product.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID cannot be null or empty.");
            }
        }
        this.products = products; 
    }
    
    @Override
    public Object getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nDescription: " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Category){
            return this.id == ((Category)obj).id;
        }
        return false;
    }
}