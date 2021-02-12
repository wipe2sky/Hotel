package task3.task3;

public class AssemblyLine implements IAssemblyLine{

    private ILineStep hullLineStep = new BuildHull();
    private ILineStep towerLineStep = new BuildTower();
    private ILineStep engineLineStep = new BuildEngine();

    public ILineStep getHullLineStep() {
        return hullLineStep;
    }

    private void setHullLineStep(ILineStep hullLineStep) {
        this.hullLineStep = hullLineStep;
    }

    public ILineStep getTowerLineStep() {
        return towerLineStep;
    }

    private void setTowerLineStep(ILineStep towerLineStep) {
        this.towerLineStep = towerLineStep;
    }

    public ILineStep getEngineLineStep() {
        return engineLineStep;
    }

    private void setEngineLineStep(ILineStep engineLineStep) {
        this.engineLineStep = engineLineStep;
    }

    @Override
    public IProduct assemblyProduct(IProduct tank) {

        tank.installFirstPart(hullLineStep.buildProduct());
        tank.installSecondPart(towerLineStep.buildProduct());
        tank.installThirdPart(engineLineStep.buildProduct());
        System.out.println("Tank is done");
        return tank;
    }
}
