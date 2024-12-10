package Services;

import java.util.ArrayList;

import Entity.*;

public class ProductService extends EntityService<Product> {
    private PermissionService permission;
    private CategoryService categoryService;

    public ProductService(AuthService authService, PermissionService permission, CategoryService categoryService){
        super("products",Product.class, authService);
        this.permission = permission;
        this.categoryService = categoryService;
    }

    public PermissionService getPermission() {
        return permission;
    }

    public void setPermission(PermissionService permission) {
        this.permission = permission;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public String create(String name, String desc, String image, double price, String categoryId) {
        if (permission.hasPermission("products", "create")) {
            if (categoryId != null) {
                if (categoryService.get(categoryId) == null){
                    throw new IllegalArgumentException("this category doesn't exist");
                }
            }
            String id = getEntityDAO().nextId();
            getEntityDAO().add(new Product(id, name, desc, image, price, categoryId));
            return id;
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void create(String name, String desc, String image, double price, String categoryId, int quantity) {
        if (permission.hasPermission("products", "create")) {
            getEntityDAO().add(new Product(getEntityDAO().nextId(), name, desc, image, price, categoryId, quantity));
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String id) {
        if (permission.hasPermission("products", "delete")) {
            getEntityDAO().delete(id);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Product get(String id){
        if (permission.hasPermission("products","retrieve")) {
            Product product = getEntityDAO().get(id);
            if (product == null) {
                return null;  
            }
            return new Product(product);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Product> getAll(){
        if (permission.hasPermission("products","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String id,String parameter ,T newData) {
        if (permission.hasPermission("products", "update")) {
            Product product = getEntityDAO().get(id);
            if (product == null) {
                throw new IllegalArgumentException("This product doesn't exist.");  
            }

            switch (parameter.toLowerCase()) {
                case "name":
                    product.setName((String)newData);
                    break;
                case "quantity":
                    product.setQuantity((int)newData);
                    break;
                case "price":
                    product.setPrice((double)newData);
                    break;
                case "description":
                    product.setDescription((String)newData);
                    break;
                case "category":
                    if ((String)newData!=null) {
                        categoryService.get((String)newData);
                    }
                    product.setCategoryId((String)newData);
                    break;
                case "image":
                    product.setImage((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(product);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
