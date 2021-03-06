package pt.ipbeja.po2.contagious.model;

public abstract class Cell {
    private CellPosition cellPosition;
    private int line;
    private int col;

    /**
     * Constructor
     * @param cellPosition - position to create cell
     */
    public Cell(CellPosition cellPosition) {
        this.cellPosition = cellPosition;
        this.line = cellPosition.getLine();
        this.col = cellPosition.getCol();
    }

    /**
     * Get current position
     * @return - current position
     */
    public CellPosition cellPosition() {
        return this.cellPosition;
    }

    /**
     * Randomly move cell
     * @param speed - speed input
     * @param directions - direction change input
     * @return - new position
     */
    public CellPosition randomMove(int speed, int directions) {
        int[] v = {-1, 0, 1};
        boolean valid = false;
        if (directions == 1) {
            v = new int[] {-1, 1, 1};
        }
        while(!valid) {
            int lineMove = (v[World.rand.nextInt(3)] * speed);
            int colMove = v[World.rand.nextInt(3)] * speed;
            if (lineMove == 0 && colMove == 0) {// to force a move
                lineMove = 1;
            }
            int newLine = this.line + lineMove;
            int newCol = this.col + colMove;
            if (World.isValidMove(newLine, newCol) && World.getCell(newLine, newCol).isEmpty()) {
                this.line = newLine;
                this.col = newCol;
                this.cellPosition = new CellPosition(newLine, newCol);
                valid = true;
            }
        }
        return this.cellPosition;
    }

    /**
     * Get current line
     * @return - current line
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Get current col
     * @return - current col
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Convert cell to string
     * @return - cell in string format
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

    /**
     * @return iterations to get healed
     */
    public abstract int getIterationsToHeal();
}
