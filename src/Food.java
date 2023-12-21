import java.util.Objects;

public class Food extends Consumable implements Comparable<Food>{
    TypeOfFood typeOfFood;
    Object dietType;

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum TypeOfFood {
        ALLA,
        VEGETARIAN,
        MEAT,
        VEGAN
    }
    public Food(String name, int timeToPrepare) {
        super(name, timeToPrepare);
    }

    @Override
    public void setType(Object type) {
        super.setType(type);
        dietType = type;
    }

    @Override
    public String getType() {
        if (dietType == TypeOfFood.VEGAN) {
            typeOfFood = TypeOfFood.VEGAN;
        } else if (dietType == TypeOfFood.VEGETARIAN) {
            typeOfFood = TypeOfFood.VEGETARIAN;
        } else if (dietType == TypeOfFood.MEAT) {
            typeOfFood = TypeOfFood.MEAT;
            System.out.println(typeOfFood);
        }
        return Objects.toString(typeOfFood);
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
