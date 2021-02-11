package task3.task3;

public class BuildEngine implements ILineStep{
    @Override
    public IProductPart buildProduct() {
        IProductPart engine = new Engine();
        System.out.println("Create new Engine");
        return engine;
    }
}
