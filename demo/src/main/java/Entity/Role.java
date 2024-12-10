package Entity;

public enum Role {
    superadmin(1),
    product(2),
    customer(3),
    order(4);
    
    private final int code;

    Role(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}