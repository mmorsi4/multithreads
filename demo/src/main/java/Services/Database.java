package Services;

import Entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

final class Database<E extends Entity> {
    private final Gson gson = new Gson();
    private final String TABLES_PATH = "src/main/resources/data/tables.json";
    private static Map<String, String> tables = new HashMap<>();
    private final Class type;

    Database(Class type){
        this.type = type;
        try (Reader reader = new FileReader(TABLES_PATH)) {
            Type listType = new TypeToken<Map<String,String>>() {}.getType();
            tables = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String tableName, ArrayList<E> entities){
        if (tableName == null || entities == null) {
            throw new NullPointerException("this table doesn't exist");
        }

        String file_path = tables.get(tableName.toLowerCase());
        if (file_path == null) {
            throw new NullPointerException("this table doesn't exist");
        }

        try (Writer writer = new FileWriter(file_path)) {
            gson.toJson(entities, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    public ArrayList<E> load(String tableName){
        if (tableName == null) {
            throw new NullPointerException("this table doesn't exist");
        }

        String file_path = tables.get(tableName.toLowerCase());
        if (file_path == null) {
            throw new NullPointerException("this table doesn't exist");
        }

        try (Reader reader = new FileReader(file_path)) {
            ArrayList<E> a = gson.fromJson(reader, TypeToken.getParameterized(ArrayList.class, type).getType());
            if (a==null) {
                return new ArrayList<>();
            }else{
                return a;
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
