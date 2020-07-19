package pt.ipbeja.po2.contagious.model;

import java.util.Objects;

public class CellPosition {
    private int line;
    private int col;

    /**
     * Constructor
     * @param line - position's line
     * @param col - position's col
     */
    public CellPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    /**
     * Get current line
     * @return - current line
     */
    public int getLine() {
        return line;
    }

    /**
     * Get current col
     * @return - current col
     */
    public int getCol() {
        return col;
    }

    /**
     * Compare positions
     * @param o
     * @return - true if positions are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition cellPosition = (CellPosition) o;
        return line == cellPosition.line &&
                col == cellPosition.col;
    }

    /**
     * Get hash code
     * @return - hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }

    /**
     * Move to another position
     * @param dx - no of cells to move x
     * @param dy - no of cells to move y
     * @return - new position
     */
    public CellPosition move(int dx, int dy) {
        return new CellPosition(this.line + dy, this.col + dx);
    }

    /**
     * Check if position is inside board
     * @param nLines - number of lines
     * @param nCols - number of cols
     * @return - true if inside
     */
    public boolean isInside(int nLines, int nCols) {
        return 0 <= this.line && this.line < nLines && 0 <= this.col && this.col < nCols;
    }

    /**
     * Convert position to string
     * @return - position's string
     */
    @Override
    public String toString() {
        return "(" + line + ", " + col + ")";
    }
}
