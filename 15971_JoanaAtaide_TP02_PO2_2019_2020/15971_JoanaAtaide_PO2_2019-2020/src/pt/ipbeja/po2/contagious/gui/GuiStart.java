 /**
     *
	 Joana Ataíde Nº15971
	 jfrab@hotmail.com
	 https://github.com/PO2-2019-2020/tp2-15971-po2
     */
	 package pt.ipbeja.po2.contagious.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStart extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        ContagiousBoard board = new ContagiousBoard();
        Scene scene = new Scene(board);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Contagious");
        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String args) {
        Application.launch(args);
    }
}
