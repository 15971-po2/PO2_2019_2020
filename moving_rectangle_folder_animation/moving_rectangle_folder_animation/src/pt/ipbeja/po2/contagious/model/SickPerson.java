package pt.ipbeja.po2.contagious.model;

public class SickPerson extends Person {

    public SickPerson(CellPosition cellPosition) {
        super(cellPosition);
    }

    @Override
    public boolean isHealthy() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSick() {
        return true;
    }

    @Override
    public boolean isImmune() {
        return false;
    }
}
