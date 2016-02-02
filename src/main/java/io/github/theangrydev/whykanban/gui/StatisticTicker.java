package io.github.theangrydev.whykanban.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.reactfx.EventStream;

public class StatisticTicker extends GridPane {

    private StatisticTicker(String statisticName, String statisticDisplayFormat, EventStream<?> statisticSource) {
        add(new Text(statisticName), 0, 0);
        add(statisticTicker(statisticSource, statisticDisplayFormat), 0, 1);
    }

    public static StatisticTicker statisticWithLabel(String statisticName, String statisticDisplayFormat, EventStream<?> statisticSource) {
        return new StatisticTicker(statisticName, statisticDisplayFormat, statisticSource);
    }

    private static Text statisticTicker(EventStream<?> statisticSource, String statisticDisplayFormat) {
        Text statisticValue = new Text();
        statisticValue.setFont(Font.font(null, FontWeight.BOLD, 15));
        statisticSource.map(statistic -> displayStatistic(statistic, statisticDisplayFormat)).subscribe(statisticValue::setText);
        return statisticValue;
    }

    private static String displayStatistic(Object statistic, String statisticDisplayFormat) {
        return String.format(statisticDisplayFormat, statistic);
    }
}
