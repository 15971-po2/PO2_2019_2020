 /**
     *
	 Joana Ataíde Nº15971
	 jfrab@hotmail.com
	 https://github.com/PO2-2019-2020/tp2-15971-po2
     */
	 package pt.ipbeja.po2.contagious.gui;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

public class SetupDialog extends Dialog<SetupData> {
    private ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private int nHealthy;
    private int nSick;
    private int speed;
    private int directions;

    public SetupDialog() {
        this.setTitle("New Contagious Settings");
        this.setHeaderText(null);

        GridPane pane = new GridPane();

        Label healthyLabel = new Label("Number of healthy persons: ");
        Label sickLabel = new Label("Number of sick persons: ");
        Label immuneLabel = new Label("Number of immune persons: ");
        Label speedLabel = new Label("Squares per move: ");
        Label directionLabel = new Label("Direction changes: ");
        pane.add(healthyLabel, 0, 0);
        pane.add(sickLabel, 0, 1);
        pane.add(immuneLabel, 0, 2);
        pane.add(speedLabel, 0, 3);
        pane.add(directionLabel, 0, 4);

        Spinner<Integer> healthySpinner = new Spinner<Integer>(1, 50, 10);
        Spinner<Integer> sickSpinner = new Spinner<Integer>(1, 50, 2);
        Spinner<Integer> immuneSpinner = new Spinner<Integer>(1, 50, 2);
        Spinner<Integer> speedSpinner = new Spinner<Integer>(1, 5, 1);
        Spinner<Integer> directionSpinner = new Spinner<Integer>(0, 1, 1);

        pane.add(healthySpinner, 1, 0);
        pane.add(sickSpinner, 1, 1);
        pane.add(immuneSpinner, 1, 2);
        pane.add(speedSpinner, 1, 3);
        pane.add(directionSpinner, 1, 4);

        getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        getDialogPane().setContent(pane);

        setResultConverter(button -> {
            return new SetupData(healthySpinner.getValue(),
                    sickSpinner.getValue(),
                    immuneSpinner.getValue(),
                    speedSpinner.getValue(),
                    directionSpinner.getValue());
        });
    }
}
