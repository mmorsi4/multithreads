package Services;

import Entity.*;
import java.util.ArrayList;

final class EntityDAO<E extends Entity> {
    private final Database<E> db;
    private ArrayList<E> entities;
    private String tableName;
    private final Class type;

    EntityDAO(String tableName, Class type) {
        this.type = type;
        db = new Database<>(type);
        this.tableName = tableName;
        entities = (ArrayList<E>) db.load(tableName);
    }

    public ArrayList<E> getAll() {
        return new ArrayList<>(entities);
    }

    public void add(E e) throws NullPointerException {
        if (e == null){
            throw new IllegalArgumentException("Entity Object cannot be null.");
        }

        if (getIndex(e.getKey()) != -1) {
            throw new RuntimeException("There exists an entity with the same key");
        }
        entities.add(e);
        db.save(tableName,entities);
    }

    public void erase() {
        entities = new ArrayList<>();
        db.save(tableName, entities);
    }

    public void delete(Object key) {
        if (key==null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        entities.removeIf(entity -> entity.getKey().equals(key));
        db.save(tableName,entities);
    }

    public E get(Object key) throws RuntimeException{
        if (key==null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (E e : entities) {
            if(e.getKey().equals(key)){
                return e;
            }
        }
        return null;
    }

    public int getIndex(Object key){
        if (key==null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i).getKey().equals(key)) return i;
        }
        return -1;
    }

    public void update(E updatedE) throws RuntimeException {
        if (updatedE == null){
            throw new IllegalArgumentException("Entity Object cannot be null.");
        }

        int i = getIndex(updatedE.getKey());
        if (i!=-1) {
            entities.set(i, updatedE);
            db.save(tableName,entities);
        }else{
            throw new RuntimeException("Entity doesn't Exist");
        }
    }

    public void sort() {
        entities.sort(null);
    }

    public String nextId(){
        return String.valueOf(entities.size()+1);
    }

}
