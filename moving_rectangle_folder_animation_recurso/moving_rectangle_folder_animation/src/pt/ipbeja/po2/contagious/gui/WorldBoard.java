package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.World;

public class WorldBoard extends Pane {

    static public final Color[] STATE_COLORS = {Color.BLUE, Color.RED, Color.GREEN};
    private final World world;
    private final int CELL_SIZE;
    private final int nLinesPane;
    private final int nColsPane;
    private Rectangle[][] rectangles;

    /**
     * Constructor
     * @param world - world instance
     * @param size - world size
     */
    public WorldBoard(World world, int size) {
        this.world = world;
        this.CELL_SIZE = size;
        this.nLinesPane = world.nLines() * CELL_SIZE;
        this.nColsPane = world.nCols() * CELL_SIZE;
        this.setPrefSize(nLinesPane, nColsPane);
        this.rectangles = new Rectangle[world.nCols()][world.nLines()];
        this.fillBoard();
    }

    /**
     * Update positions
     * @param position - position to be removed
     * @param newPosition - position to move
     */
    public void updatePosition(CellPosition position, CellPosition newPosition) {
        int line = position.getLine();
        int col = position.getCol();
        int newLine = newPosition.getLine();
        int newCol = newPosition.getCol();

        this.getChildren().remove(this.rectangles[col][line]);
        this.rectangles[col][line].setOpacity(0);
        this.rectangles[newCol][newLine] = this.addRectangle(newPosition);
    }

    /**
     * Fill the board from world data
     */
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

    /**
     * Add rectangle to pane
     * @param position - position to add rectangle
     * @return - rectangle
     */
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
