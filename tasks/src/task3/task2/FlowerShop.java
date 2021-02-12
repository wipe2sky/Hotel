package task3.task2;

public class FlowerShop {

    public static void main(String[] args) {
        Bouquet bouquet = new Bouquet();
        bouquet.addFlower(new Rose());
        bouquet.addFlower(new Rose());
        bouquet.addFlower(new Rose());
        bouquet.addFlower(new Chamomile());
        bouquet.addFlower(new Tulip());
        System.out.println("Bouquet cost: " + bouquet.getCostBouquet() + "$");
    }
}
