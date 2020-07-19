package pt.ipbeja.po2.contagious.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStart extends Application {
    private static String[] cmdInput;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ContagiousBoard board = new ContagiousBoard(this.cmdInput);
        Scene scene = new Scene(board);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Contagious");
        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String args[]) {
        cmdInput = args;
        Application.launch(args);
    }
}
