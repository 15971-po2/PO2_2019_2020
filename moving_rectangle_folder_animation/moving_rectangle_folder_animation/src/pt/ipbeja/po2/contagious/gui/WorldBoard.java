package pt.ipbeja.po2.contagious.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.WorldWithOneCell;

public class WorldBoard extends Pane {

    static public final Color[] STATE_COLORS = {Color.BLUE, Color.RED, Color.GREEN};
    private final int CELL_SIZE;
    private final int nLinesPane;
    private final int nColsPane;

    private Rectangle rectangle;

    public WorldBoard(WorldWithOneCell world, int size) {
        this.CELL_SIZE = size;
        this.nLinesPane = world.nLines() * CELL_SIZE;
        this.nColsPane = world.nCols() * CELL_SIZE;
        this.setPrefSize(this.nLinesPane, this.nColsPane);

        this.rectangle = null;

    }

    public void populateWorld(CellPosition position) {
        this.rectangle = this.addRectangle(position);
    }

//    public void updatePosition(int dx, int dy) {
//        this.rectangle.setX(this.rectangle.getX() + dx * CELL_SIZE); // move rectangle graphic
//        this.rectangle.setY(this.rectangle.getY() + dy * CELL_SIZE); // move rectangle graphic
//    }

    public void updatePosition(int dx, int dy) {
            TranslateTransition tt =
                    new TranslateTransition(Duration.millis(200), this.rectangle);
            tt.setByX(dx * CELL_SIZE);
            tt.setByY(dy * CELL_SIZE);
            tt.play();
    }

    private Rectangle addRectangle(CellPosition position) {
        int line = position.getLine() * CELL_SIZE;
        int col = position.getCol() * CELL_SIZE;

        Rectangle r = new Rectangle(col, line, CELL_SIZE, CELL_SIZE);

        r.setFill(Color.BLUE);
        Platform.runLater( () -> {
            this.getChildren().add(r);
        });
        return r;
    }
}
