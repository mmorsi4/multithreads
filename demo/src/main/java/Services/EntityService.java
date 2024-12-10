package Services;

import Entity.*;

abstract public class EntityService<E extends Entity> {
    private final EntityDAO<E> entityDAO;
    private final Class type;
    private final String tableName;
    private  AuthService authService;

    public EntityService(String tableName, Class type, AuthService authService){
        this.type = type;
        this.tableName = tableName;
        entityDAO = new EntityDAO<>(tableName,type);
        if (authService==null) {
            throw new IllegalArgumentException("Authentication Service cannot be null.");
        }
        this.authService = authService;
    }


    protected EntityDAO<E> getEntityDAO() {
        return entityDAO;
    }

    public User getLoggedInUser() {
        return authService.getLoggedInUser();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}

