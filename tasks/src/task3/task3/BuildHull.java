package task3.task3;

public class BuildHull implements ILineStep {
    @Override
    public IProductPart buildProduct() {
        IProductPart hull = new Hull();
        System.out.println("Create new Hull");
        return hull;
    }
}
