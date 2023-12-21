import java.io.Serializable;

public class Consumable implements Serializable {
    String name;
    int timeToPrepare;
    public Consumable(String name, int timeToPrepare){
        this.name = name;
        this.timeToPrepare = timeToPrepare;
    }


    public void setType(Object type) {
    }

    public String getType() {
        return null;
    }


    public int getTimeToPrepare() {
        return timeToPrepare;
    }
}
