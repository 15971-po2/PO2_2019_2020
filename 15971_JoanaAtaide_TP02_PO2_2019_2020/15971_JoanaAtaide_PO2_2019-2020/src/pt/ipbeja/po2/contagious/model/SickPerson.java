 /**
     *
	 Joana Ataíde Nº15971
	 jfrab@hotmail.com
	 https://github.com/PO2-2019-2020/tp2-15971-po2
     */
	 package pt.ipbeja.po2.contagious.model;

public class SickPerson extends Person {
    private int iterationsToHeal;

    public SickPerson(CellPosition cellPosition, int iterationsToHeal) {
        super(cellPosition);
        this.iterationsToHeal = iterationsToHeal;
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

    public int getIterationsToHeal() {
        return this.iterationsToHeal;
    }
}
