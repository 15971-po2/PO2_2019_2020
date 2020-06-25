package pt.ipbeja.po2.contagious.model;

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
    private final int speed;
    private final int directions;

    private Thread simulate;


    public World(View view, int nLines, int nCols, int nHealthyPersons, int nSickPersons, int speed, int directions) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.nHealthyPersons = nHealthyPersons;
        this.nSickPersons = nSickPersons;
        this.speed = speed;
        this.directions = directions;
        this.simulate = new Thread();
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
                        healthyCounter++;
                    } else if (sickCounter < this.nSickPersons) {
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

    public int nLines() {
        return this.nLines;
    }

    public int nCols() {
        return this.nCols;
    }

    private void move() {
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                int oldLine = cell.getLine();
                int oldCol = cell.getCol();
                if (!cell.isEmpty()) {
                    CellPosition position = new CellPosition(oldLine, oldCol);
                    System.out.println("FROM " + position.getLine() + " " + position.getCol());
                    CellPosition newPosition = cell.randomMove();
                    int newLine = newPosition.getLine();
                    int newCol = newPosition.getCol();
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
        this.updateInfections();
    }

    public void updateInfections() {
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                CellPosition position = new CellPosition(line, col);
                if (!cell.isEmpty() && this.checkInfection(position)) {
                    this.cells[line][col] = new SickPerson(position);
                }
            }
        }
    }

    /**
     * Check if move coordinates are not out of bounds
     * @param line line number
     * @param col col number
     * @return true if move is not out of bounds
     */
    public static boolean isValidMove(int line, int col)
    {
        return (line >= 0) && (line < nLines) && (col >= 0) && (col < nCols);
    }

    /**
     * Check for infected neighbors (up, down, left and right)
     * @param position position to check neighbors
     * @return true if person got infected
     */
    private boolean checkInfection(CellPosition position) {
        int line = position.getLine();
        int col = position.getCol();
        boolean isInfected = false;

        if (!this.getCell(line, col).isImmune()) {
            // Up
            if (isValidMove(line - 1, col))
            {
                if (this.cells[line - 1][col].isSick())
                    isInfected = true;
            }
            //Down
            if (isValidMove(line + 1, col))
            {
                if (this.cells[line + 1][col].isSick())
                    isInfected = true;
            }
            //Left
            if (isValidMove(line, col + 1))
            {
                if (this.cells[line][col + 1].isSick())
                    isInfected = true;
            }
            //Right
            if (isValidMove(line, col - 1))
            {
                if (this.cells[line][col - 1].isSick())
                    isInfected = true;
            }
        }
        return isInfected;
    }

    public static Cell getCell(int line, int col) {
        return cells[line][col];
    }

    /**
     * Moves the keeper if the move is valid.
     * @param line the line to move to
     * @param col the column to move to
     * @return true if the person moved, false otherwise
     */
    public boolean movePerson(int line, int col, int newLine, int newCol) {
        boolean validMove = this.isValidMove(newLine, newCol);
        if (validMove) {
            Cell oldCell = this.cells[line][col];
            Cell newCell = this.cells[newLine][newCol];
            if (oldCell.isHealthy() && newCell.isEmpty()) {
                this.cells[line][col] = new EmptyCell(oldCell.cellPosition());
                this.cells[newLine][newCol] = new HealthyPerson(newCell.cellPosition());
                this.updateInfections();
                return true;
            } else if (oldCell.isSick() &&newCell.isEmpty()) {
                this.cells[line][col] = new EmptyCell(oldCell.cellPosition());
                this.cells[newLine][newCol] = new SickPerson(newCell.cellPosition());
                this.updateInfections();
                return true;
            } else if (oldCell.isImmune() && newCell.isEmpty()) {
                this.cells[line][col] = new EmptyCell(oldCell.cellPosition());
                this.cells[newLine][newCol] = new ImmunePerson(newCell.cellPosition());
                this.updateInfections();
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void createHealthyPerson(int line, int col) {
        Cell cell = this.cells[line][col];
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new HealthyPerson(cell.cellPosition());
        }
    }

    public void createSickPerson(int line, int col) {
        Cell cell = this.cells[line][col];
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new SickPerson(cell.cellPosition());
        }
    }

    public void createImmunePerson(int line, int col) {
        Cell cell = this.cells[line][col];
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new ImmunePerson(cell.cellPosition());
        }
    }

    public void start() {
        this.simulate = new Thread( () -> {
            this.simulate(200);
        });
        this.simulate.start();
    }

    public void stop() {
        this.simulate.stop();
    }


    private void simulate(int nIter) {
        for (int i = 0; i < nIter; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move();
        }
    }
}
