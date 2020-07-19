package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.View;
import pt.ipbeja.po2.contagious.model.World;

import java.io.File;
import java.util.Optional;

public class ContagiousBoard extends VBox implements View {
    public static World world;
    private WorldBoard pane;
    private Label counterLabel;

    // Inputs
    private String path;
    private int nHealthy;
    private int nSick;
    private int nImmune;
    private int speed;
    private int directions;
    private int mode;

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private LineChart<Number,Number> lineChart;
    private XYChart.Series<Number, Number> healthy;
    private XYChart.Series<Number, Number> sick;
    private XYChart.Series<Number, Number> immune;

    private final int WINDOW_SIZE = 10;

    private MenuBar menuBar;

    public ContagiousBoard(String args[]) {

        this.mode = 0;
        if (args.length == 1) {
            this.mode = 1;
            this.path = args[0];
        } else if (args.length == 3) {
            this.mode = 2;
            this.path = args[0];
            this.speed = Integer.parseInt(args[1]);
            this.directions = Integer.parseInt(args[2]);
        } else if (args.length == 5) {
            this.mode = 3;
            this.nHealthy = Integer.parseInt(args[0]);
            this.nSick = Integer.parseInt(args[1]);
            this.nImmune = Integer.parseInt(args[2]);
            this.speed = Integer.parseInt(args[3]);
            this.directions = Integer.parseInt(args[4]);
        }

        // Setup menu
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

        MenuItem open = new MenuItem("Open");
        open.setOnAction(e -> {
            this.open();
        });

        MenuItem save = new MenuItem("Save As...");
        save.setOnAction(e -> {
            this.save();
        });

        file.getItems().add(start);
        file.getItems().add(stop);
        file.getItems().add(setup);
        file.getItems().add(open);
        file.getItems().add(save);
        this.menuBar = new MenuBar();
        this.menuBar.getMenus().add(file);

        Button startButton = new Button("Start");
        startButton.setPrefWidth(200);

        this.getChildren().add(startButton);

        startButton.setOnMouseClicked((e) -> {
            this.world = new World(this, 50, 50,50, 50, 2, 0, 0);
            this.pane = new WorldBoard(this.world, 10);
            this.counterLabel = new Label(("0"));
            this.counterLabel.setPrefWidth(pane.getPrefWidth());
            startButton.setPrefWidth(pane.getPrefWidth());
            this.getChildren().remove(startButton);
            this.getChildren().addAll(menuBar);
            this.getScene().getWindow().sizeToScene();
            if (this.mode == 0) {
                this.setup();
            } else if (this.mode == 1) {
                this.world.readFile(this.path);
                this.pane.getChildren().clear();
                this.pane = new WorldBoard(this.world, 10);
                this.getChildren().clear();
                this.getChildren().addAll(this.menuBar, this.pane);
                this.setupChart();
                this.getScene().getWindow().sizeToScene();
            } else if (this.mode == 2) {
                this.world.readFile(this.path,this.speed, this.directions);
                this.pane.getChildren().clear();
                this.pane = new WorldBoard(this.world, 10);
                this.getChildren().clear();
                this.getChildren().addAll(this.menuBar, this.pane);
                this.setupChart();
                this.getScene().getWindow().sizeToScene();
            } else if (this.mode == 3) {
                this.world = new World(this, 50, 50,
                        this.nHealthy, this.nSick, this.nImmune, this.speed, this.directions);
                this.pane.getChildren().clear();
                this.pane = new WorldBoard(this.world, 10);
                this.getChildren().clear();
                this.getChildren().addAll(this.menuBar, this.pane);
                this.setupChart();
                this.getScene().getWindow().sizeToScene();
            }
        });
    }

    /**
     * Handle start action
     */
    private void start() {
        world.start();
    }

    /**
     * Handle stop action
     */
    private void stop() {
        world.stop();
    }

    /**
     * Handle setup action
     */
    private void setup() {
        this.stop();
        SetupDialog dialog = new SetupDialog();;
        Optional<SetupData> result = dialog.showAndWait();
        SetupData setup = result.get();
        this.nHealthy = setup.getHealthy();
        this.nSick = setup.getSick();
        this.nImmune = setup.getImmune();
        this.speed = setup.getSpeed();
        this.directions = setup.getDirections();
        this.world = new World(this, 50, 50,
                this.nHealthy, this.nSick, this.nImmune, this.speed, this.directions);
        this.pane.getChildren().clear();
        this.pane = new WorldBoard(this.world, 10);
        this.getChildren().clear();
        this.getChildren().addAll(this.menuBar, this.pane);
        this.setupChart();
        this.getScene().getWindow().sizeToScene();
    }

    /**
     * Handle open action
     */
    private void open() {
        this.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Board Text File", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        this.world.readFile(selectedFile.getAbsolutePath());
        this.pane.getChildren().clear();
        this.pane = new WorldBoard(this.world, 10);
        this.getChildren().clear();
        this.getChildren().addAll(this.menuBar, this.pane);
        this.setupChart();
        this.getScene().getWindow().sizeToScene();
    }

    /**
     * Handle save action
     */
    private void save() {
        this.stop();
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Board Text File", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        world.saveFile(file);
    }

    /**
     * Update position in world board
     * @param position - current position
     * @param newPosition - new position to move
     */
    @Override
    public void updatePosition(CellPosition position, CellPosition newPosition) {
        Platform.runLater( () -> {
            pane.updatePosition(position, newPosition);
        });
    }

    private void setupChart() {
        this.xAxis.setLabel("Iteration no");
        this.xAxis.setAnimated(false);
        this.yAxis.setLabel("Count");
        this.yAxis.setAnimated(false);

        this.lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        this.lineChart.setTitle("Pandemic evolution");
        this.lineChart.setAnimated(false);

        this.healthy = new XYChart.Series<>();
        //this.healthy.getNode().lookup("healhy.chart-series-line").setStyle("-fx-stroke: #0033cc");
        this.healthy.setName("Healthy people");
        this.healthy.getData().add(new XYChart.Data(0, this.nHealthy));

        this.sick = new XYChart.Series<>();
        this.sick.setName("Sick People");
        this.sick.getData().add(new XYChart.Data(0, this.nSick));

        this.immune = new XYChart.Series<>();
        this.immune.setName("Immune People");
        this.immune.getData().add(new XYChart.Data(0, this.nImmune));

        this.lineChart.getData().addAll(this.healthy, this.sick, this.immune);
        this.lineChart.setAnimated(true);
        this.getChildren().add(this.lineChart);
    }

    public void updateChart(int iteration, int healthy, int sick, int immune) {
        System.out.println(iteration + " " + healthy + " " + sick + " " + immune);
        Platform.runLater(() -> {
            this.healthy.getData().add(new XYChart.Data<>(iteration, healthy));
            this.sick.getData().add(new XYChart.Data<>(iteration, sick));
            this.immune.getData().add(new XYChart.Data<>(iteration, immune));
            System.out.println(this.lineChart.getWidth());
            if (this.healthy.getData().size() > WINDOW_SIZE) {
                this.healthy.getData().remove(0);
                this.sick.getData().remove(0);
                this.immune.getData().remove(0);
                xAxis.setLowerBound(xAxis.getLowerBound() - 1);
                xAxis.setUpperBound(xAxis.getUpperBound() - 1);
            }
        });
    }
}