package pt.ipbeja.po2.contagious.model;

import java.util.Random;

public class World {
    public static final Random rand = new Random();

    private View view;
    private Cell cell;
    private Cell[][] cells;
    private final int nLines;
    private final int nCols;


    public World(View view, int nLines, int nCols) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.cell = new Cell(new CellPosition(this.nLines / 2,
                this.nCols / 2));
    }

    /**
     * Creates the game board by populating each Place with an EMPTY value
     */
    private void fillBoard() {
        for (int i = 0; i < this.nLines; i++) {
            for (int j = 0; j < this.nCols; j++) {
                Cell cell = new HealthyPerson();
                this.cells[i][j] = cell;
            }
        }
    }

    public void start(int nPersons) {
        for (int i = 0; i < nPersons; i++) {
            new Thread( () -> {
                this.populate();
                this.simulate(10);

            }).start();
        }

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
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cell.randomMove()) {
                this.view.updatePosition(cell.dx(), cell.dy(), i);
            }
        }
    }
}
