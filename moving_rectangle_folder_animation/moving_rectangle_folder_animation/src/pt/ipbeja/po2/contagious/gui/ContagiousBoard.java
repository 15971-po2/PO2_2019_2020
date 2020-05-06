package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.View;
import pt.ipbeja.po2.contagious.model.WorldWithOneCell;

public class ContagiousBoard extends VBox implements View {
    public static WorldWithOneCell world;
    private WorldBoard pane;
    private Label counterLabel;


    public ContagiousBoard() {

        Button startButton = new Button("Start");
        startButton.setPrefWidth(200);

        this.getChildren().add(startButton);

        startButton.setOnMouseClicked((e) -> {
            world = new WorldWithOneCell(this, 100, 100);
            this.pane = new WorldBoard(this.world, 10);
            this.counterLabel = new Label(("0"));
            this.counterLabel.setPrefWidth(pane.getPrefWidth());
            startButton.setPrefWidth(pane.getPrefWidth());
            this.getChildren().remove(startButton);
            this.getChildren().addAll(this.counterLabel, this.pane);
            this.getScene().getWindow().sizeToScene();

            world.start();
        });
    }

    @Override
    public void populateWorld(CellPosition position) {
        pane.populateWorld(position);
    }

    @Override
    public void updatePosition(int dx, int dy, int i) {
        Platform.runLater( () -> {
            pane.updatePosition(dx, dy);
            System.out.println(i);
            this.counterLabel.setText("" + i);
        });
    }
}