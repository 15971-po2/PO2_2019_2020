package pt.ipbeja.po2.contagious.model;

import java.util.Collections;
import java.util.Random;

public class World {
    public static final Random rand = new Random();

    private View view;
    private static Cell[][] cells;
    private static int nLines = 0;
    private static int nCols = 0;
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
        this.fill();
    }

    /**
     * Creates the game board
     */
    private void fill() {
        int healthyCounter = 0;
        int sickCounter = 0;
        int nCells = 0;
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = null;
                CellPosition position = new CellPosition(line, col);
                if (nCells % 5 == 0 && ((healthyCounter + sickCounter) < this.nPersons)) {
                    if (healthyCounter < this.nHealthyPersons) {
                        cell = new HealthyPerson(position);
                        //System.out.println(line + " " + col);
                        //System.out.println("From position " + position.getLine() + " " + position.getCol());
                        healthyCounter++;
                    } else if (sickCounter < this.nSickPersons) {
                        //System.out.println(line + " " + col);
                        //System.out.println("From position " + position.getLine() + " " + position.getCol());
                        cell = new SickPerson(position);
                        sickCounter++;
                    }
                } else {
                    cell = new EmptyCell(position);
                }
                this.cells[line][col] = cell;
                nCells++;
            }
        }
        this.shuffleCells();
        //System.out.println(getCell(0, 30).isSick());
    }

    private void shuffleCells() {
        Random random = new Random();

        for (int i = this.cells.length - 1; i > 0; i--) {
            for (int j = cells[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                Cell oldCell = this.cells[i][j];
                Cell newCell = this.cells[m][n];
                if (oldCell.isHealthy()) {
                    if (newCell.isEmpty()) {
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new HealthyPerson(newCell.cellPosition());
                    } else if (newCell.isHealthy()) {
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new HealthyPerson(newCell.cellPosition());
                    } else if (newCell.isSick()) {
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition());
                        this.cells[m][n] = new HealthyPerson(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new HealthyPerson(newCell.cellPosition());
                    }

                } else if (oldCell.isEmpty()) {
                    if (newCell.isEmpty()) {
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    } else if (newCell.isHealthy()) {
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    } else if (newCell.isSick()) {
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition());
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    }
                } else if (oldCell.isSick()) {
                    if (newCell.isEmpty()) {
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition());
                    } else if (newCell.isHealthy()) {
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition());
                    } else if (newCell.isSick()) {
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition());
                    }
                } else if (oldCell.isImmune()) {
                    if (newCell.isEmpty()) {
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isHealthy()) {
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isSick()) {
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    }
                }

            }
        }
    }

    public void start() {
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

    public void move() {
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                //System.out.println(line + " " + col);
                //System.out.println(this.getCell(cell.getLine(), cell.getCol()));
                //System.out.println(this.getCell(cell.cellPosition().getLine(), cell.cellPosition().getCol()));
                //System.out.println("From position " + cell.getLine() + " " + cell.getCol());
                int oldLine = cell.getLine();
                int oldCol = cell.getCol();
                if (!cell.isEmpty()) {
                    //System.out.println(cell.getLine() + " " + cell.getCol());
                    CellPosition position = new CellPosition(oldLine, oldCol);
                    System.out.println("FROM " + position.getLine() + " " + position.getCol());
                    CellPosition newPosition = cell.randomMove();
                    int newLine = newPosition.getLine();
                    int newCol = newPosition.getCol();
                    //System.out.println("To " + newLine + " " + newCol);
                    System.out.println("TO " + newPosition.getLine() + " " + newPosition.getCol());
                    //System.out.println();
                    this.cells[oldLine][oldCol] = new EmptyCell(position);
                    if (cell.isImmune()) {
                        this.cells[newLine][newCol] = new ImmunePerson(newPosition);
                    } else if (cell.isSick()) {
                        this.cells[newLine][newCol] = new SickPerson(newPosition);
                    } else if (cell.isHealthy()) {
                        this.cells[newLine][newCol] = new HealthyPerson(newPosition);
                    }
                    this.view.updatePosition(position, newPosition);
                }
            }
        }
    }

    public static boolean isValid(int line, int col)
    {
        // Returns true if line and column number
        // is in range
        return (line >= 0) && (line < nLines) &&
                (col >= 0) && (col < nCols);
    }

    private boolean checkInfection(CellPosition position) {
        int line = position.getLine();
        int col = position.getCol();
        boolean isInfected = false;

        //----------- 1st Neighbour (North) ------------

        if (isValid(line - 1, col))
        {
            if (this.cells[line - 1][col].isSick())
                isInfected = true;
        }

        //----------- 2nd Neighbour (South) ------------

        if (isValid(line + 1, col))
        {
            if (this.cells[line + 1][col].isSick())
                isInfected = true;
        }

        //----------- 3rd Neighbour (East) ------------

        if (isValid(line, col + 1))
        {
            if (this.cells[line][col + 1].isSick())
                isInfected = true;
        }

        //----------- 4th Neighbour (West) ------------

        if (isValid(line, col - 1))
        {
            if (this.cells[line][col - 1].isSick())
                isInfected = true;
        }

        //----------- 5th Neighbour (North-East) ------------

        if (isValid(line - 1, col + 1))
        {
            if (this.cells[line - 1][col + 1].isSick())
                isInfected = true;
        }

        //----------- 6th Neighbour (North-West) ------------

        if (isValid(line - 1, col - 1))
        {
            if (this.cells[line - 1][col - 1].isSick())
                isInfected = true;
        }

        //----------- 7th Neighbour (South-East) ------------

        if (isValid(line + 1, col + 1))
        {
            if (this.cells[line + 1][col + 1].isSick())
                isInfected = true;
        }

        //----------- 8th Neighbour (South-West) ------------

        if (isValid(line + 1, col - 1) == true)
        {
            if (this.cells[line + 1][col - 1].isSick())
                isInfected = true;
        }

        return isInfected;
    }

    public static Cell getCell(int line, int col) {
        return cells[line][col];
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
