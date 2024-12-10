package Services;

import Entity.*;
import java.util.*;


public class PermissionService extends EntityService<Permisson>{

    public PermissionService(AuthService authService) {
        super("permissons", Permisson.class, authService);
    }

    public String getServiceActionName(String tableName, String action){
        if (tableName==null||tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty.");
        }

        if (action==null||action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty.");
        }

        return tableName.toLowerCase()+"_"+action.toLowerCase();
    }

    public boolean hasPermission(String tableName ,String action){
        if (getLoggedInUser()==null) {
            return false;
        }
        return hasPermission(getLoggedInUser().getRole(),tableName, action);
    }

    public boolean hasPermission(String action){
        if (getLoggedInUser()==null) {
            return false;
        }
        return hasPermission(getLoggedInUser().getRole(), action);
    }

    public boolean hasPermission(Role role, String action) {
        if (role==null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }

        if (action==null||action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty.");
        }
        Permisson permisson =  getEntityDAO().get(role);
        if (permisson == null) {
            throw new IllegalArgumentException("this permisson doesn't exist.");
        }

        ArrayList<String> allowedActions = permisson.getActions();
        if (allowedActions == null) return false;

        for (String allowedAction : allowedActions) {
            if (allowedAction.equals(action.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermission(Role role,String tableName ,String action) {
        if (tableName==null||tableName.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty.");
        }

        if (action==null||action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty.");
        }

        action = getServiceActionName(tableName, action);
        return hasPermission(role, action);
    }

    public Role create(Role role, ArrayList<String> actions) throws RuntimeException{
        if (getLoggedInUser() == null) {
            throw new RuntimeException("Login first to do this action");
        }
        if (getLoggedInUser().getRole().equals(Role.superadmin)) {
            getEntityDAO().add(new Permisson(role, actions));
            return role;
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void delete(Role role) throws RuntimeException {
        if (getLoggedInUser() == null) {
            throw new RuntimeException("Login first to do this action");
        }
        if (getLoggedInUser().getRole().equals(Role.superadmin)) {
            getEntityDAO().delete(role);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }

    }

    public void addAction(Role role, String action){
        if (action==null||action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action cannot be null or empty.");
        }
        if (getLoggedInUser() == null) {
            throw new RuntimeException("Login first to do this action");
        }

        if (getLoggedInUser().getRole().equals(Role.superadmin)) {
            Permisson permisson = getEntityDAO().get(role);
            if (permisson == null) {
                throw new IllegalArgumentException("this permisson doesn't exist.");
            }
            permisson.getActions().add(action);
            getEntityDAO().update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void addAction(Role role, String tableName ,String action){
        addAction(role, getServiceActionName(tableName, action));
    }

    public void addActions(Role role, ArrayList<String> actions) throws RuntimeException{
        if (getLoggedInUser() == null) {
            throw new RuntimeException("Login first to do this action");
        }

        if (getLoggedInUser().getRole().equals(Role.superadmin)) {
            Permisson permisson = getEntityDAO().get(role);
            if (permisson == null) {
                throw new IllegalArgumentException("this permisson doesn't exist.");
            }
            permisson.setActions(actions);
            getEntityDAO().update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }
    }

    public void deleteAllActions(Role role) throws RuntimeException{
        addActions(role, new ArrayList<>());
    }

    public void deleteAction(Role role, String action) throws RuntimeException {
        if (getLoggedInUser() == null) {
            throw new RuntimeException("Login first to do this action");
        }
        
        if (getLoggedInUser().getRole().equals(Role.superadmin)) {
            Permisson permisson = getEntityDAO().get(role);
            if (permisson == null) {
                throw new IllegalArgumentException("this permisson doesn't exist.");
            }
            permisson.getActions().remove(action);
            getEntityDAO().update(permisson);
        }else{
            throw new RuntimeException("You don't have the permisson to do this action");
        }

    }

    public void deleteAction(Role role, String tableName ,String action) throws RuntimeException {
        deleteAction(role, getServiceActionName(tableName, action));
    }    
}
