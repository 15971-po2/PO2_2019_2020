package pt.ipbeja.po2.contagious.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pt.ipbeja.po2.contagious.model.Cell;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.World;

public class WorldBoard extends Pane {

    static public final Color[] STATE_COLORS = {Color.BLUE, Color.RED, Color.GREEN};
    private final World world;
    private final int CELL_SIZE;
    private final int nLinesPane;
    private final int nColsPane;
    private Rectangle[][] rectangles;

    public WorldBoard(World world, int size) {
        this.world = world;
        this.CELL_SIZE = size;
        this.nLinesPane = world.nLines() * CELL_SIZE;
        this.nColsPane = world.nCols() * CELL_SIZE;
        this.setPrefSize(nLinesPane, nColsPane);
        this.rectangles = new Rectangle[world.nCols()][world.nLines()];

        this.fillBoard();
    }

    public void populateWorld(CellPosition position) {
        //this.rectangle = this.addRectangle(position);
    }

    public void addPerson(CellPosition position) {
        this.rectangles[position.getLine()][position.getCol()] = this.addRectangle(position);
    }

//    public void updatePosition(int dx, int dy) {
//        this.rectangle.setX(this.rectangle.getX() + dx * CELL_SIZE); // move rectangle graphic
//        this.rectangle.setY(this.rectangle.getY() + dy * CELL_SIZE); // move rectangle graphic
//    }

    public void updatePosition(CellPosition position, CellPosition newPosition) {
//        this.rectangles = new Rectangle[world.nCols()][world.nLines()];
//        this.getChildren().clear();
//        for (int line = 0; line < this.world.nLines(); line++) {
//            for (int col = 0; col < this.world.nCols(); col++) {
//                if (!this.world.getCell(line, col).isEmpty()) {
//                    CellPosition position = new CellPosition(line, col);
//                    this.rectangles[col][line] = this.addRectangle(position);
//                }
//            }
//        }
//    }
        int line = position.getLine();
        int col = position.getCol();
        int newLine = newPosition.getLine() * CELL_SIZE;
        int newCol = newPosition.getCol() * CELL_SIZE;
//        System.out.println("Old: " + line + " " + col);
//        System.out.println("New " + newLine + " " + newCol);
//        System.out.println();
        Rectangle rect = this.rectangles[col][line];
        TranslateTransition tt =
                new TranslateTransition(Duration.millis(200), rect);
        tt.setFromX(newCol / 50);
        tt.setFromY(newLine / 50);
        tt.play();
    }


    private void fillBoard() {
        for (int line = 0; line < this.world.nLines(); line++ ) {
            for (int col = 0; col < this.world.nCols(); col++) {
                if (!this.world.getCell(line, col).isEmpty()) {
                    CellPosition position = new CellPosition(line, col);
                    this.rectangles[col][line] = this.addRectangle(position);
                }
            }
        }
    }


    private Rectangle addRectangle(CellPosition position) {
        int i = position.getLine();
        int j = position.getCol();
        int line = i * CELL_SIZE;
        int col = j * CELL_SIZE;
        Color color = null;

        Rectangle r = new Rectangle(col, line, CELL_SIZE, CELL_SIZE);

        if (this.world.getCell(i, j).isHealthy()) {
            color = STATE_COLORS[0];
        } else if (this.world.getCell(i, j).isSick()) {
            color = STATE_COLORS[1];
        } if (this.world.getCell(i, j).isImmune()) {
            color = STATE_COLORS[2];
        }

        r.setFill(color);
        Platform.runLater( () -> {
            this.getChildren().add(r);
        });
        return r;
    }

}
