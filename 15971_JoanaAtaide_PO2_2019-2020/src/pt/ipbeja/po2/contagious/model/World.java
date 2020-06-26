package pt.ipbeja.po2.contagious.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class World {
    public static final Random rand = new Random();

    private View view;
    private static Cell[][] cells;
    private static int nLines = 0;
    private static int nCols = 0;
    private int nHealthyPersons;
    private int nSickPersons;
    private int nImmunePersons;
    private final int nPersons;
    private final int speed;
    private final int directions;

    private Thread simulate;

    private List<String> fileData;


    public World(View view, int nLines, int nCols,
                 int nHealthyPersons, int nSickPersons, int nImmunePersons, int speed, int directions) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.nHealthyPersons = nHealthyPersons;
        this.nSickPersons = nSickPersons;
        this.nImmunePersons = nImmunePersons;
        this.speed = speed;
        this.directions = directions;
        this.simulate = new Thread();
        this.nPersons = nHealthyPersons + nSickPersons + nImmunePersons;
        this.cells = new Cell[this.nLines][this.nCols];
        this.fill();
    }

    /**
     * Create contagious board
     */
    private void fill() {
        int healthyCounter = 0;
        int sickCounter = 0;
        int immuneCounter = 0;
        int nCells = 0;
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = null;
                CellPosition position = new CellPosition(line, col);
                if (nCells % 3 == 0 && ((healthyCounter + sickCounter + immuneCounter) < this.nPersons)) {
                    if (healthyCounter < this.nHealthyPersons) {
                        cell = new HealthyPerson(position);
                        healthyCounter++;
                    } else if (sickCounter < this.nSickPersons) {
                        int iterationsToHeal = this.rand.nextInt(3);
                        cell = new SickPerson(position, iterationsToHeal);
                        sickCounter++;
                    } else if (immuneCounter < this.nImmunePersons) {
                        cell = new ImmunePerson(position);
                        immuneCounter++;
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

    /**
     * Shuffle cells array
     */
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
                        int iterationsToHeal = newCell.getIterationsToHeal();
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition(), iterationsToHeal);
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
                        int iterationsToHeal = newCell.getIterationsToHeal();
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition(), iterationsToHeal);
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new EmptyCell(newCell.cellPosition());
                    }
                } else if (oldCell.isSick()) {
                    if (newCell.isEmpty()) {
                        int iterationsToHeal = oldCell.getIterationsToHeal();
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition(), iterationsToHeal);
                    } else if (newCell.isHealthy()) {
                        int iterationsToHeal = oldCell.getIterationsToHeal();
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition(), iterationsToHeal);
                    } else if (newCell.isSick()) {
                        int oldIterationsToHeal = oldCell.getIterationsToHeal();
                        int newIterationsToHeal = newCell.getIterationsToHeal();
                        this.cells[m][n] = new SickPerson(oldCell.cellPosition(), oldIterationsToHeal);
                        this.cells[m][n] = new SickPerson(newCell.cellPosition(), newIterationsToHeal);
                    } else if (newCell.isImmune()) {
                        int iterationsToHeal = newCell.getIterationsToHeal();
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new SickPerson(newCell.cellPosition(), iterationsToHeal);
                    }
                } else if (oldCell.isImmune()) {
                    if (newCell.isEmpty()) {
                        this.cells[i][j] = new EmptyCell(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isHealthy()) {
                        this.cells[i][j] = new HealthyPerson(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isSick()) {
                        int iterationsToHeal = newCell.getIterationsToHeal();
                        this.cells[i][j] = new SickPerson(oldCell.cellPosition(), iterationsToHeal);
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    } else if (newCell.isImmune()) {
                        this.cells[i][j] = new ImmunePerson(oldCell.cellPosition());
                        this.cells[m][n] = new ImmunePerson(newCell.cellPosition());
                    }
                }

            }
        }
    }

    /**
     * Get number of lines
     * @return - number of lines
     */
    public int nLines() {
        return this.nLines;
    }

    /**
     * Get number of columns
     * @return - number of columns
     */
    public int nCols() {
        return this.nCols;
    }

    /**
     * Move people
     */
    private void move(int iteration) {
        int people = 0;
        int minPeople = this.nPersons - (this.nPersons / 4);
        int peopleToMove = this.rand.nextInt(((this.nPersons - minPeople) + 1)) + minPeople;
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                int oldLine = cell.getLine();
                int oldCol = cell.getCol();
                if (!cell.isEmpty() && people < peopleToMove) {
                    CellPosition position = new CellPosition(oldLine, oldCol);
                    CellPosition newPosition = cell.randomMove(this.speed);
                    int newLine = newPosition.getLine();
                    int newCol = newPosition.getCol();
                    this.cells[oldLine][oldCol] = new EmptyCell(position);
                    if (cell.isImmune()) {
                        this.cells[newLine][newCol] = new ImmunePerson(newPosition);
                        people++;
                    } else if (cell.isSick()) {
                        int iterationsToHeal = cell.getIterationsToHeal();
                        this.cells[newLine][newCol] = new SickPerson(newPosition, iterationsToHeal);
                        people++;
                    } else if (cell.isHealthy()) {
                        this.cells[newLine][newCol] = new HealthyPerson(newPosition);
                        people++;
                    }
                    this.view.updatePosition(position, newPosition);
                }
            }
        }
        this.view.updateChart(iteration + 1, this.nHealthyPersons, this.nSickPersons, this.nImmunePersons);
        this.updateInfections();
        this.updateIterationsToHeal();
    }

    /**
     * Update person's sickness status
     */
    public void updateInfections() {
        int iterationsToHeal = this.rand.nextInt(3);
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                CellPosition position = new CellPosition(line, col);
                if (!cell.isEmpty() && this.checkInfection(position)) {
                    this.cells[line][col] = new SickPerson(position, iterationsToHeal);
                    this.nSickPersons++;
                    this.nHealthyPersons--;
                }
            }
        }
    }

    /**
     * Check if move coordinates are not out of bounds
     * @param line - line number
     * @param col - col number
     * @return true if move is not out of bounds
     */
    public static boolean isValidMove(int line, int col)
    {
        return (line >= 0) && (line < nLines) && (col >= 0) && (col < nCols);
    }

    /**
     * Check for infected neighbors (up, down, left and right)
     * @param position - position to check neighbors
     * @return - true if person got infected
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
     * Moves the person if the move is valid.
     * @param line - person's current line
     * @param col - person's current col
     * @param newLine - the line to move to
     * @param newCol - the column to move to
     * @return - true if the person moved, false otherwise
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
                int iterationsToHeal = oldCell.getIterationsToHeal();
                this.cells[line][col] = new EmptyCell(oldCell.cellPosition());
                this.cells[newLine][newCol] = new SickPerson(newCell.cellPosition(), iterationsToHeal);
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

    /**
     * Create healthy person
     * @param line - new healthy person's line
     * @param col - new healthy person's col
     */
    public void createHealthyPerson(int line, int col) {
        Cell cell = this.cells[line][col];
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new HealthyPerson(cell.cellPosition());
        }
    }

    /**
     * Create sick person
     * @param line - new sick person's line
     * @param col - new sick person's col
     */
    public void createSickPerson(int line, int col) {
        Cell cell = this.cells[line][col];
        int iterationsToHeal = this.rand.nextInt(3);
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new SickPerson(cell.cellPosition(), iterationsToHeal);
        }
    }

    /**
     * Create immune person
     * @param line - new immune person's line
     * @param col - new immune person's col
     */
    public void createImmunePerson(int line, int col) {
        Cell cell = this.cells[line][col];
        if (this.isValidMove(line, col) && cell.isEmpty()) {
            this.cells[line][col] = new ImmunePerson(cell.cellPosition());
        }
    }

    /**
     * Start contagious movement thread
     */
    public void start() {
        this.simulate = new Thread( () -> {
            this.simulate(10000);
        });
        this.simulate.start();
    }

    /**
     * Stop contagious movement thread
     */
    public void stop() {
        this.simulate.stop();
    }

    /**
     * Contagious simulation
     */
    private void simulate(int nIter) {
        for (int i = 0; i < nIter; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move(nIter);
        }
    }

    /**
     * Read data from text file
     */
    public void readFile() {
        this.fileData = new ArrayList<>();
        try {
            File f = new File("src/resources/board.txt");
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                fileData.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.fillFromData();
    }

    /**
     * Fill grid with data read from file
     */
    private void fillFromData() {
        if (this.fileData.size() > 4) {
            this.nLines = Integer.parseInt(this.fileData.get(0));
            this.nCols = Integer.parseInt(this.fileData.get(1));
            this.cells = new Cell[this.nLines][this.nCols];
            for (int line = 0; line < this.nLines; line++) {
                for (int col = 0; col < this.nCols; col++) {
                    Cell cell = null;
                    CellPosition position = new CellPosition(line, col);
                    cell = new EmptyCell(position);
                    this.cells[line][col] = cell;
                }
            }
            for (int i = 2; i < this.fileData.size() - 1; i++) {
                String data = this.fileData.get(i);
                if (!data.matches(".*\\d.*")) {
                    switch (data) {
                        case "healthy":
                            for (int cell = i + 1; cell < this.fileData.size() - 2; cell++) {
                                if (this.fileData.get(cell).matches(".*\\d.*")) {
                                    String[] splitted = this.fileData.get(cell).split(" ");
                                    this.createHealthyPerson(Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]));
                                } else {
                                    break;
                                }
                            }
                            break;
                        case "immune":
                            for (int cell = i + 1; cell < this.fileData.size() - 2; cell++) {
                                if (this.fileData.get(cell).matches(".*\\d.*")) {
                                    String[] splitted = this.fileData.get(cell).split(" ");
                                    this.createImmunePerson(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
                                } else {
                                    break;
                                }
                            }
                            break;
                        case "sick":
                            for (int cell = i + 1; cell < this.fileData.size(); cell++) {
                                if (this.fileData.get(cell).matches(".*\\d.*")) {
                                    String[] splitted = this.fileData.get(cell).split(" ");
                                    this.createSickPerson(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
                                } else {
                                    break;
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    /**
     * Save current grid to text file
     */
    public void saveFile() {
        List<String> saveData = new ArrayList<>();
        List<String> healthy = new ArrayList<>();
        List<String> immune = new ArrayList<>();
        List<String> sick = new ArrayList<>();
        saveData.add(String.valueOf(this.nLines));
        saveData.add(String.valueOf(this.nCols));
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                if (this.cells[line][col].isHealthy()) {
                    healthy.add(line + " " + col);
                } else if (this.cells[line][col].isImmune()) {
                    immune.add(line + " " + col);
                } else if (this.cells[line][col].isSick()) {
                    sick.add(line + " " + col);
                }

            }
        }
        saveData.add("healthy");
        for (int i = 0; i < healthy.size(); i++) {
            saveData.add(healthy.get(i));
        }
        saveData.add("immune");
        for (int i = 0; i < immune.size(); i++) {
            saveData.add(immune.get(i));
        }
        saveData.add("sick");
        for (int i = 0; i < sick.size(); i++) {
            saveData.add(sick.get(i));
        }
        try {
            FileWriter writer = new FileWriter("src/resources/board.txt");
            for (int i = 0; i < saveData.size(); i++) {
                writer.write(saveData.get(i) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateIterationsToHeal() {
        for (int line = 0; line < this.nLines; line++) {
            for (int col = 0; col < this.nCols; col++) {
                Cell cell = this.cells[line][col];
                int oldLine = cell.getLine();
                int oldCol = cell.getCol();
                if (cell.isSick()) {
                    CellPosition position = new CellPosition(oldLine, oldCol);
                    int iterationsToHeal = cell.getIterationsToHeal() - 1;
                    if (iterationsToHeal != 0) {
                        this.cells[oldLine][oldCol] = new SickPerson(position, iterationsToHeal);
                    } else {
                        this.cells[oldLine][oldCol] = new ImmunePerson(position);
                        this.nSickPersons--;
                        this.nImmunePersons++;
                    }
                    this.view.updatePosition(position, position);
                }
            }
        }
    }
}
