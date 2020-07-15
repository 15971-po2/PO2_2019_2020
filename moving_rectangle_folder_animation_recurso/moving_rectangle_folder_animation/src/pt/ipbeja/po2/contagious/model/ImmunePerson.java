package pt.ipbeja.po2.contagious.model;

public class ImmunePerson extends Person {

    public ImmunePerson(CellPosition cellPosition) {
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
        return false;
    }

    @Override
    public boolean isImmune() {
        return true;
    }

    @Override
    public int getIterationsToHeal() {
        return 0;
    }
}
