 /**
     *
	 Joana Ataíde Nº15971
	 jfrab@hotmail.com
	 https://github.com/PO2-2019-2020/tp2-15971-po2
     */
	 package pt.ipbeja.po2.contagious.model;

public class EmptyCell extends Cell {
    public EmptyCell(CellPosition cellPosition) {
        super(cellPosition);
    }

    @Override
    public boolean isHealthy() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSick() {
        return false;
    }

    @Override
    public boolean isImmune() {
        return false;
    }

    @Override
    public int getIterationsToHeal() {
        return 0;
    }
}
