package task3.task3;

public class Tank implements IProduct{
    private IProductPart hull;
    private IProductPart tower;
    private IProductPart engine;

    public IProductPart getHull() {
        return hull;
    }

    private void setHull(IProductPart hull) {
        this.hull = hull;
    }

    public IProductPart getTower() {
        return tower;
    }

    private void setTower(IProductPart tower) {
        this.tower = tower;
    }

    public IProductPart getEngine() {
        return engine;
    }

    private void setEngine(IProductPart engine) {
        this.engine = engine;
    }

    @Override
    public void installFirstPart(IProductPart hull) {
        if(hull instanceof Hull) {
            setHull(hull);
            System.out.println("Install Hull Part");
        }else System.out.println("Can't install Hull, wrong type of part");


    }

    @Override
    public void installSecondPart(IProductPart tower) {
        if( tower instanceof Tower) {
            setTower(tower);
            System.out.println("Install Tower Part");
        }else System.out.println("Can't install Tower, wrong type of part");

    }

    @Override
    public void installThirdPart(IProductPart engine) {
        if(engine instanceof Engine) {
            setEngine(engine);
            System.out.println("Install Engine Part");
        }else System.out.println("Can't install Engine, wrong type of part");

    }
}
