package pt.ipbeja.po2.contagious.model;

import pt.ipbeja.po2.contagious.gui.ContagiousBoard;

public abstract class Cell {
    private CellPosition cellPosition;
    private int line;
    private int col;

    public Cell(CellPosition cellPosition) {
        this.cellPosition = cellPosition;
        this.line = cellPosition.getLine();
        this.col = cellPosition.getCol();
    }

    public CellPosition cellPosition() {
        return cellPosition;
    }

//    public boolean randomMove() {
//        final int[] v = {-1, 0, 1};
//        this.dx = v[World.rand.nextInt(3)];
//        this.dy = v[World.rand.nextInt(3)];
//        if (dx == 0 && dy == 0) {// to force a move
//            dx = 1;
//        }
//        this.cellPosition = new CellPosition(
//                this.cellPosition.getLine() + dy,
//                this.cellPosition.getCol() + dx);
//
//        return true;
//    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return this.col;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + this.line + ", " + this.col + ")";
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
