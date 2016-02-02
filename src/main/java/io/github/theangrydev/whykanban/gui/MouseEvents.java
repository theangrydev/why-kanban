package io.github.theangrydev.whykanban.gui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.reactfx.EventStream;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static org.reactfx.EventStreams.eventsOf;

public class MouseEvents {

    public static boolean leftClick(MouseEvent mouseEvent) {
        return mouseEvent.getButton() == MouseButton.PRIMARY;
    }

    public static EventStream<MouseEvent> leftClicks(Button button) {
        return eventsOf(button, MOUSE_CLICKED).filter(MouseEvents::leftClick);
    }
}
