package io.github.theangrydev.whykanban.gui;

import javafx.scene.layout.ColumnConstraints;

import java.util.List;

import static java.util.Collections.nCopies;

public class PercentageColumnConstraints {

    public static List<ColumnConstraints> percentageConstraints(int numberOfColumns) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100 / numberOfColumns);
        return nCopies(numberOfColumns, columnConstraints);
    }
}
