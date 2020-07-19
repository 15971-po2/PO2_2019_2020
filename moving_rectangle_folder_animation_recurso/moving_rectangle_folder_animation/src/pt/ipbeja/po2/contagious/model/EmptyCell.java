package pt.ipbeja.po2.contagious.model;

public class EmptyCell extends Cell {

    /**
     * Constructor
     * @param cellPosition - position to create empty cell
     */
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
