package Entity;

public enum PaymentMethod {
    cash(1),
    debit(2),
    credit(3),
    bank(4);
    
    private final int code;

    PaymentMethod(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}