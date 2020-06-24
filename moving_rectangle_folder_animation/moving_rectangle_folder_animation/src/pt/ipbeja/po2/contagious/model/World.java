package pt.ipbeja.po2.contagious.model;

import java.util.Random;

public class World {
    public static final Random rand = new Random();

    private View view;
    private Cell[][] cells;
    private final int nLines;
    private final int nCols;
    private final int nHealthyPersons;
    private final int nSickPersons;
    private final int nPersons;


    public World(View view, int nLines, int nCols, int nHealthyPersons, int nSickPersons) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.nHealthyPersons = nHealthyPersons;
        this.nSickPersons = nSickPersons;
        this.nPersons = nHealthyPersons + nSickPersons;
        this.cells = new Cell[this.nLines][this.nCols];
    }

    /**
     * Creates the game board
     */
    private void fillBoard() {
        int healthyCounter = 0;
        int sickCounter = 0;
        int nCells = 0;
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = null;
                CellPosition position = new CellPosition(line, col);
                if (nCells % 15 == 0 && ((healthyCounter + sickCounter) < this.nPersons - 1)) {
                    if (healthyCounter < this.nHealthyPersons) {
                        cell = new HealthyPerson(position);
                        healthyCounter++;
                    } else if (sickCounter < this.nSickPersons) {
                        cell = new SickPerson(position);
                        sickCounter++;
                    }
                } else {
                    cell = new EmptyCell(position);
                }
                this.cells[line][col] = cell;
                System.out.println(cell.isHealthy());
                nCells++;
            }
        }
    }

    public void start() {
        this.fillBoard();
//        for (int i = 0; i < nPersons; i++) {
//            new Thread( () -> {
//                this.populate();
//                this.simulate(10);
//
//            }).start();
//        }

    }

    public int nLines() {
        return this.nLines;
    }

    public int nCols() {
        return this.nCols;
    }

    private void populate(Cell cell) {
        this.view.addPerson(cell.cellPosition());
        view.populateWorld(cell.cellPosition());
    }

    private void move() {
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                if (this.cells[line][col].getClass() == Person.class) {

                }
            }
        }
    }

    private boolean isValid(int line, int col)
    {
        // Returns true if row number and column number
        // is in range
        return (line >= 0) && (line < this.nLines) &&
                (col >= 0) && (col < this.nCols);
    }

    private boolean checkInfection(CellPosition position) {
        int line = position.getLine();
        int col = position.getCol();
        boolean isInfected = false;

        //----------- 1st Neighbour (North) ------------

        if (isValid (line - 1, col))
        {
            if (this.cells[line - 1][col].isSick())
                isInfected = true;
        }

        //----------- 2nd Neighbour (South) ------------

        if (isValid (line + 1, col))
        {
            if (this.cells[line + 1][col].isSick())
                isInfected = true;
        }

        //----------- 3rd Neighbour (East) ------------

        if (isValid (line, col + 1))
        {
            if (this.cells[line][col + 1].isSick())
                isInfected = true;
        }

        //----------- 4th Neighbour (West) ------------

        if (isValid (line, col - 1))
        {
            if (this.cells[line][col - 1].isSick())
                isInfected = true;
        }

        //----------- 5th Neighbour (North-East) ------------

        if (isValid (line - 1, col + 1))
        {
            if (this.cells[line - 1][col + 1].isSick())
                isInfected = true;
        }

        //----------- 6th Neighbour (North-West) ------------

        if (isValid (line - 1, col - 1))
        {
            if (this.cells[line - 1][col - 1].isSick())
                isInfected = true;
        }

        //----------- 7th Neighbour (South-East) ------------

        if (isValid (line + 1, col + 1))
        {
            if (this.cells[line + 1][col + 1].isSick())
                isInfected = true;
        }

        //----------- 8th Neighbour (South-West) ------------

        if (isValid (line + 1, col - 1) == true)
        {
            if (this.cells[line + 1][col - 1].isSick())
                isInfected = true;
        }

        return isInfected;
    }

    private void simulate(int nIter) {
//        for (int i = 0; i < nIter; i++) {
//            try {
//                Thread.sleep(400);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (cell.randomMove()) {
//                this.view.updatePosition(cell.dx(), cell.dy(), i);
//            }
//        }
    }
}
