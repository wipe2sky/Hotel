package task3.task2;

import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    List<Flower> flowerList = new ArrayList<>();
    public void addFlower(Flower flower){
        flowerList.add(flower);
    }
    public int getCostBouquet(){
        int cost = 0;
        for (Flower flower:
                flowerList) {
            cost +=flower.getCost();
        }
        return cost;
    }
}
