import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static FileHandler instance;

    static Gson gson;

    private FileHandler(){
        gson = new Gson();
    }

    public static void writeListToFile(ArrayList<Food> consumableList) {
        try (FileWriter writer = new FileWriter("src/Consumable.ser")) {
            gson.toJson(consumableList,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Food> readListFromFile(){
        try(FileReader reader = new FileReader("src/Consumable.ser")){
            return gson.fromJson(reader, new TypeToken<List<Food>>(){}.getType());
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static FileHandler getInstance(){
        if(instance == null){
            return instance = new FileHandler();
        }
        return instance;
    }
}


