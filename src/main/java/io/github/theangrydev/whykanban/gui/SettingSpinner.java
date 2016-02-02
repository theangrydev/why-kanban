package io.github.theangrydev.whykanban.gui;

import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.reactfx.EventStream;

import static org.reactfx.EventStreams.valuesOf;

public class SettingSpinner extends GridPane {

	private Spinner<Integer> spinner;

	private SettingSpinner(String teamMemberType, int initialValue) {
		add(new Text(teamMemberType), 0, 0);
		spinner = new Spinner<>(0, 100, initialValue);
		add(spinner, 0, 1);
	}

	public static SettingSpinner settingSpinner(String teamMemberType, int initialValue) {
		return new SettingSpinner(teamMemberType, initialValue);
	}

	public EventStream<Integer> setting() {
		return valuesOf(spinner.valueProperty());
	}
}
