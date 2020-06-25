package pt.ipbeja.po2.contagious.model;

public class HealthyPerson extends Person {

    public HealthyPerson(CellPosition cellPosition) {
        super(cellPosition);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSick() {
        return false;
    }

    @Override
    public boolean isImmune() {
        return false;
    }
}
