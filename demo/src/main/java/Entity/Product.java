package Entity;

public class Product implements Entity, Comparable<Product> {
    private String id;
    private String name;
    private String description;
    private String image;
    private double price;
    private String categoryId;
    private int quantity;

    public Product(String id, String name, String desc, String image, double price, String categoryId) {
        setId(id);
        setName(name);
        setDescription(desc);
        setPrice(price);
        setCategoryId(categoryId);
        setQuantity(1);
    }

    public Product(String id, String name, String desc, String image, double price, String categoryId, int quantity) {
        this(id, name, desc, image, price, categoryId);
        setQuantity(quantity);
    }

    public Product(Product product){
        this(product.id, product.name, product.description, product.image, product.price, product.categoryId);
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
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Description cannot exceed 500 characters.");
        }
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        if (categoryId != null) {
            if (categoryId.trim().isEmpty()) {
                throw new IllegalArgumentException("Category Id cannot be Empty.");
            }
        }
        
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Object getKey() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nName: " + name + "\nDescription: " + description + "\nPrice: " + price + "\nQuantity: "
                + quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return this.id == ((Product) obj).id;
        }
        return false;
    }

    @Override
    public int compareTo(Product p) {
        if (price == p.price)
            return 0;
        else if (price > p.price)
            return 1;
        return -1;
    }
}
