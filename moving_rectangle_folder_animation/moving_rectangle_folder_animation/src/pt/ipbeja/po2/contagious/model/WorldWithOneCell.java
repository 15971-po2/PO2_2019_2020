package pt.ipbeja.po2.contagious.model;

import java.util.Random;

public class WorldWithOneCell {
    public static final Random rand = new Random();

    private View view;
    private Cell cell;
    private final int nLines;
    private final int nCols;


    public WorldWithOneCell(View view, int nLines, int nCols) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.cell = new Cell(new CellPosition(this.nLines / 2,
                                               this.nCols / 2));
    }

    public void start() {
        new Thread( () -> {
            this.populate();
            this.simulate(200);
        }).start();
    }

    public int nLines() {
        return this.nLines;
    }

    public int nCols() {
        return this.nCols;
    }

    private void populate() {
        view.populateWorld(cell.cellPosition());
    }

    private void simulate(int nIter) {
        for (int i = 0; i < nIter; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cell.randomMove()) {
                this.view.updatePosition(cell.dx(), cell.dy(), i);
            }
        }
    }
}