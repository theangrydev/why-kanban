package io.github.theangrydev.whykanban.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.reactfx.EventStream;

public class StatisticTicker extends GridPane {

    public static StatisticTicker statisticWithLabel(String statisticName, String statisticUnit, EventStream<Double> statisticSource) {
        StatisticTicker statisticTicker = new StatisticTicker();
        statisticTicker.add(new Text(statisticName), 0, 0);
        statisticTicker.add(statisticTicker(statisticSource, statisticUnit), 0, 1);
        return statisticTicker;
    }

    private static Text statisticTicker(EventStream<Double> statisticSource, String statisticUnit) {
        Text statisticValue = new Text();
        statisticValue.setFont(Font.font(null, FontWeight.BOLD, 15));
        statisticSource.map(statistic -> displayStatistic(statistic, statisticUnit)).subscribe(statisticValue::setText);
        return statisticValue;
    }

    private static String displayStatistic(Double statistic, String statisticUnit) {
        return String.format("%.2f %s", statistic, statisticUnit);
    }
}
