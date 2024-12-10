package Entity;

/**
 * Entity Interface
 *
 */
public interface Entity{    
    abstract public String toString();
    abstract public Object getKey();
    abstract public boolean equals(Object obj);
}
