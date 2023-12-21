import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Filter {

    public ArrayList<Food> TypeOfFoodFilter(ArrayList<Food> consumableArrayList, Object getType) {
        ArrayList<Food> aSpecificTypeOfFood = new ArrayList<>();
        for (Food consumable : consumableArrayList) {
             if (Objects.toString(getType).equals(Objects.toString(consumable.dietType))) {
                 aSpecificTypeOfFood.add(consumable);
            }
        }
        return  aSpecificTypeOfFood;
    }
}
