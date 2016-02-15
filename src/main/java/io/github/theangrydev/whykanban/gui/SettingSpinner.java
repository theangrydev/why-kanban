package io.github.theangrydev.whykanban.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.reactfx.EventStream;

import static io.github.theangrydev.whykanban.gui.PercentageColumnConstraints.percentageConstraints;
import static org.reactfx.EventStreams.valuesOf;

public class SettingSpinner extends GridPane {

	private Spinner<Integer> spinner;

	private SettingSpinner(String teamMemberType, int initialValue) {
		add(new Text(teamMemberType), 0, 0);
		getColumnConstraints().addAll(percentageConstraints(1));
		spinner = new Spinner<>(0, 100, initialValue);
		spinner.setMinWidth(50);
		spinner.setMaxWidth(Double.MAX_VALUE);
		makeEditable(initialValue);
		add(spinner, 0, 1);
	}

	private void makeEditable(int initialValue) {
		SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, initialValue);
		spinner.setValueFactory(factory);
		TextFormatter<Integer> formatter = new TextFormatter<>(factory.getConverter(), factory.getValue());
		spinner.getEditor().setTextFormatter(formatter);
		factory.valueProperty().bindBidirectional(formatter.valueProperty());
		spinner.setEditable(true);
	}

	public static SettingSpinner settingSpinner(String settingName, int initialValue) {
		return new SettingSpinner(settingName, initialValue);
	}

	public EventStream<Integer> setting() {
		return valuesOf(spinner.valueProperty());
	}
}
