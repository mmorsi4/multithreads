package Entity;

public enum Gender {
    male(1),
    female(2);
    
    private final int code;

    Gender(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}