package Entity;

public enum Status {
    draft(1),
    processing(2),
    shipping(3),
    delivered(4),
    closed(5),
    cancelled(6);
    
    private final int code;

    Status(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}