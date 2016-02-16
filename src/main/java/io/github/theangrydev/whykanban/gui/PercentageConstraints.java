package io.github.theangrydev.whykanban.gui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.nCopies;
import static java.util.stream.Collectors.toList;

public class PercentageConstraints {

    public static List<ColumnConstraints> percentageColumnConstraints(int numberOfColumns) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100 / numberOfColumns);
        return nCopies(numberOfColumns, columnConstraints);
    }

    public static List<RowConstraints> percentageRowConstraints(double... percentages) {
        return Arrays.stream(percentages).mapToObj(PercentageConstraints::percentageRowConstraint).collect(toList());
    }

    private static RowConstraints percentageRowConstraint(double percentHeight) {
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(percentHeight);
        return rowConstraints;
    }
}
