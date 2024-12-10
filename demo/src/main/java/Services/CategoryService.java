package Services;

import java.util.ArrayList;

import Entity.*;

public class CategoryService extends EntityService<Category> {
    private PermissionService permission;

    public CategoryService(AuthService authService, PermissionService permission){
        super("categories",Category.class, authService);
        this.permission = permission;
    }

    public PermissionService getPermission() {
        return permission;
    }

    public void setPermission(PermissionService permission) {
        this.permission = permission;
    }

    public String create(String name, String description) {
        if (permission.hasPermission("categories", "create")) {
            String id = getEntityDAO().nextId();
            getEntityDAO().add(new Category(id, name, description,new ArrayList<>()));
            return id;
        } else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(String id) {
        if (permission.hasPermission("categories", "delete")) {
            getEntityDAO().delete(id);
        }else {
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public Category get(String id){
        if (permission.hasPermission("categories","retrieve")) {
            Category category = getEntityDAO().get(id);
            if (category == null) {
                return null;
            }
            return new Category(category);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public ArrayList<Category> getAll(){
        if (permission.hasPermission("categories","retrieve")) {
            return getEntityDAO().getAll();
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public <T> void update(String id,String parameter ,T newData) {
        if (permission.hasPermission("categories", "update")) {
            Category category = getEntityDAO().get(id);
            if (category == null) {
                throw new IllegalArgumentException("This category doesn't exist.");
            }
            switch (parameter.toLowerCase()) {
                case "name":
                    category.setName((String)newData);
                    break;
                case "description":
                    category.setDescription((String)newData);
                    break;
                default:
                    throw new IllegalArgumentException("This parameter doesn't exist");
            }
            this.getEntityDAO().update(category);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }
}
