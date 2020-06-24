package pt.ipbeja.po2.contagious.model;

public abstract class Person extends Cell {

    public Person(CellPosition cellPosition) {
        super(cellPosition);
    }

    /**
     * @return true if cell is healthy, false otherwise
     */
    public abstract boolean isHealthy();

    /**
     * @return true if cell is empty, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * @return true if cell is sick, false otherwise
     */
    public abstract boolean isSick();

    /**
     * @return true if cell is immune, false otherwise
     */
    public abstract boolean isImmune();
}
