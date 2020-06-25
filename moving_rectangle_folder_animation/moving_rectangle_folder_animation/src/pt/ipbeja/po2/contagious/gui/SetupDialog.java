package pt.ipbeja.po2.contagious.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SetupDialog extends Dialog<SetupData> {
    private ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private int nHealthy;
    private int nSick;

    public SetupDialog() {
        this.setTitle("New Contagious Settings");
        this.setHeaderText(null);

        GridPane pane = new GridPane();

        Label healthyLabel = new Label("Number of healthy persons: ");
        Label sickLabel = new Label("Number of sick persons: ");
        pane.add(healthyLabel, 0, 0);
        pane.add(sickLabel, 0, 1);

        Spinner<Integer> healthySpinner = new Spinner<Integer>(1, 50, 10);
        Spinner<Integer> sickSpinner = new Spinner<Integer>(1, 50, 2);

        pane.add(healthySpinner, 1, 0);
        pane.add(sickSpinner, 1, 1);

        getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        getDialogPane().setContent(pane);

        setResultConverter(button -> {
            return new SetupData(healthySpinner.getValue(), sickSpinner.getValue());
        });
    }
}
