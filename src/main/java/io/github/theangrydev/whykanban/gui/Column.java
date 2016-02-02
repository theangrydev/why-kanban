package io.github.theangrydev.whykanban.gui;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Column extends GridPane {

    public static Column column(String title, Node... nodes) {
        Column column = new Column();
        column.setVgap(10);
        column.add(title(title), 0, 0);
        for (int i = 0; i < nodes.length; i++) {
            column.add(nodes[i], 0, i + 1);
        }
        return column;
    }

    private static Text title(String title) {
        Text titleText = new Text(title);
        titleText.setFont(Font.font(null, FontWeight.BOLD, 20));
        return titleText;
    }
}
