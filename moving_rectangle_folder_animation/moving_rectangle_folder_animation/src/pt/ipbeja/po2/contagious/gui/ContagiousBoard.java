package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.View;
import pt.ipbeja.po2.contagious.model.World;

import java.util.Optional;

public class ContagiousBoard extends VBox implements View {
    public static World world;
    private WorldBoard pane;
    private Label counterLabel;

    private int nHealthy;
    private int nSick;
    private int speed;
    private int directions;

    MenuBar menuBar;

    public ContagiousBoard() {

        Menu file = new Menu("File");

        MenuItem start = new MenuItem("Start");
        start.setOnAction(e -> {
            this.start();
        });

        MenuItem stop = new MenuItem("Stop");
        stop.setOnAction(e -> {
            this.stop();
        });

        MenuItem setup = new MenuItem("Setup");
        setup.setOnAction(e -> {
            this.setup();
        });

        file.getItems().add(start);
        file.getItems().add(stop);
        file.getItems().add(setup);
        menuBar = new MenuBar();
        menuBar.getMenus().add(file);

        Button startButton = new Button("Start");
        startButton.setPrefWidth(200);

        this.getChildren().add(startButton);

        startButton.setOnMouseClicked((e) -> {
            this.world = new World(this, 50, 50, 10, 2, 0, 0);
            this.pane = new WorldBoard(this.world, 10);
            this.counterLabel = new Label(("0"));
            this.counterLabel.setPrefWidth(pane.getPrefWidth());
            startButton.setPrefWidth(pane.getPrefWidth());
            this.getChildren().remove(startButton);
            this.getChildren().addAll(menuBar, this.pane);
            this.getScene().getWindow().sizeToScene();
        });
    }

    private void start() {
        world.start();
    }

    private void stop() {
        world.stop();
    }

    private void setup() {
        SetupDialog dialog = new SetupDialog();;
        Optional<SetupData> result = dialog.showAndWait();
        SetupData setup = result.get();
        this.nHealthy = setup.getHealthy();
        this.nSick = setup.getSick();
        this.speed = setup.getSpeed();
        this.directions = setup.getDirections();
        this.world = new World(this, 50, 50, this.nHealthy, this.nSick, this.speed, this.directions);
        this.pane.getChildren().clear();
        this.pane = new WorldBoard(this.world, 10);
        this.getChildren().clear();
        this.getChildren().addAll(this.menuBar, this.pane);
        this.getScene().getWindow().sizeToScene();
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
}