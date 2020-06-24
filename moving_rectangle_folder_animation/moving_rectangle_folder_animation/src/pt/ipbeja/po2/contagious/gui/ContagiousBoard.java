package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.View;
import pt.ipbeja.po2.contagious.model.World;

public class ContagiousBoard extends VBox implements View {
    public static World world;
    private WorldBoard pane;
    private Label counterLabel;

    private final Button newGameButton;

    public ContagiousBoard() {

        Button startButton = new Button("Start");
        startButton.setPrefWidth(200);

        this.newGameButton = new Button("New Game");
        NewGameButtonHandler handler = new NewGameButtonHandler();

        this.newGameButton.setOnAction(handler);

        this.getChildren().add(startButton);

        startButton.setOnMouseClicked((e) -> {
            this.world = new World(this, 50, 50, 10, 3);
            this.pane = new WorldBoard(this.world, 10);
            this.counterLabel = new Label(("0"));
            this.counterLabel.setPrefWidth(pane.getPrefWidth());
            startButton.setPrefWidth(pane.getPrefWidth());
            this.getChildren().remove(startButton);
            this.getChildren().addAll(this.newGameButton, this.pane);
            this.getScene().getWindow().sizeToScene();

            world.start();
        });
    }

    @Override
    public void populateWorld(CellPosition position) {
        pane.populateWorld(position);
    }

    @Override
    public void addPerson(CellPosition position) {
        pane.addPerson(position);
    }

    @Override
    public void updatePosition(CellPosition position, CellPosition newPosition) {
        Platform.runLater( () -> {
            pane.updatePosition(position, newPosition);
        });
    }

    /**
     * Click handler for the new game button
     */
    class NewGameButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            newGame();
        }
    }

    private void newGame(){
        this.world.move();
    }
}