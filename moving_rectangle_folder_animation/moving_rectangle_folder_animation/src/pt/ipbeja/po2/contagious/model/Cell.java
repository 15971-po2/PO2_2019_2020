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
        return this.cellPosition;
    }

    public CellPosition randomMove() {
        CellPosition oldPosition = this.cellPosition;
        final int[] v = {-1, 0, 1};
        boolean valid = false;
        while(!valid) {
            int lineMove = v[World.rand.nextInt(3)];
            int colMove = v[World.rand.nextInt(3)];
            if (lineMove == 0 && colMove == 0) {// to force a move
                lineMove = 1;
            }
            int newLine = this.line + lineMove;
            int newCol = this.col + colMove;
            if (World.isValidMove(newLine, newCol) && World.getCell(newLine, newCol).isEmpty()) {
                //System.out.println("Old coordinates " + this.line + " " + this.col);
                this.line = newLine;
                this.col = newCol;
                //System.out.println("New coordinates " + this.line + " " + this.col);
                //System.out.println();
                this.cellPosition = new CellPosition(newLine, newCol);
                valid = true;
            }
        }
        return this.cellPosition;
    }

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
