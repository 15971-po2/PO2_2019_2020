package pt.ipbeja.po2.contagious.model;

import pt.ipbeja.po2.contagious.gui.ContagiousBoard;

public class Cell {
    private CellPosition cellPosition;
    private int dx;
    private int dy;

    public Cell(CellPosition cellPosition) {
        this.cellPosition = cellPosition;
    }

    public CellPosition cellPosition() {
        return cellPosition;
    }

    public boolean randomMove() {
        final int[] v = {-1, 0, 1};
        this.dx = v[WorldWithOneCell.rand.nextInt(3)];
        this.dy = v[WorldWithOneCell.rand.nextInt(3)];
        if (dx == 0 && dy == 0) {// to force a move
            dx = 1;
        }
        this.cellPosition = new CellPosition(
                this.cellPosition.getLine() + dy,
                this.cellPosition.getCol() + dx);

        return true;
    }

    public int dx() {
        return this.dx;
    }

    public int dy() {
        return this.dy;
    }
}
