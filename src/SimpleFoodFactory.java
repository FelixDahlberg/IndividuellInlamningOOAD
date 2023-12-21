public class SimpleFoodFactory implements FoodFactory{
    @Override
    public Food createFood(String name, int timeToPrepare, Object dietType) {
        Food food = new Food(name, timeToPrepare);
        food.setType(dietType);
        return food;
    }
}
