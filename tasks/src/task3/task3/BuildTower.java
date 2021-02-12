package task3.task3;

public class BuildTower implements ILineStep{
    @Override
    public IProductPart buildProduct() {
        IProductPart tower = new Tower();
        System.out.println("Create new Tower");
        return tower;
    }
}
