package io.github.theangrydev.whykanban.gui;

import io.github.theangrydev.whykanban.simulation.FlowHistory;
import javafx.embed.swing.SwingNode;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.reactfx.EventStream;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CumulativeFlowDiagram extends SwingNode {

    public static final boolean INCLUDE_LEGEND = true;
    private static final int READY_INDEX = 0;
    private static final int ANALYSIS_INDEX = 1;
    private static final int DEVELOPMENT_INDEX = 2;
    private static final int WATING_FOR_TEST_INDEX = 3;
    private static final int TESTING_INDEX = 4;

    private static final String[] HEADERS = {"Ready", "Analysis", "Development", "Waiting For Test", "Testing"};
    private static final String CHART_TITLE = "Cumulative Flow";
    private static final String STORY_COUNT_AXIS_LABEL = "Day Number";
    private static final String STORY_AXIS_LABEL = "Stories Waiting";
    private static final boolean ENABLE_TOOLTIPS = true;
    private static final boolean ENABLE_URLS = false;

    private CumulativeFlowDiagram(EventStream<FlowHistory> flowHistories) {
        flowHistories.map(this::flowHistoryData).subscribe(this::populateChart);
    }

    private void populateChart(double[][] data) {
        ChartPanel content = new ChartPanel(chart(data));
        content.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        content.setDomainZoomable(false);
        content.setRangeZoomable(false);
        content.setMinimumSize(new Dimension(0, 0));
        SwingUtilities.invokeLater(() -> {
            setContent(content);
            content.repaint();
        });

    }

    private JFreeChart chart(double[][] data) {
        DefaultCategoryDataset dataset = categoryDataset(data);
        return ChartFactory.createStackedAreaChart(CHART_TITLE, STORY_COUNT_AXIS_LABEL, STORY_AXIS_LABEL, dataset, PlotOrientation.VERTICAL, INCLUDE_LEGEND, ENABLE_TOOLTIPS, ENABLE_URLS);
    }

    private DefaultCategoryDataset categoryDataset(double[][] data) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        for (int row = 0; row < data.length; ++row) {
            String rowKey = HEADERS[row];

            for(int column = 0; column < data[row].length; ++column) {
                String columnKey = "" + (column + 1);
                result.addValue(new Double(data[row][column]), rowKey, columnKey);
            }
        }
        return result;
    }

    private double[][] flowHistoryData(FlowHistory flowHistory) {
        double[][] data = new double[5][];
        data[READY_INDEX] = swimLaneHistory(flowHistory.readyFlow());
        data[ANALYSIS_INDEX] = swimLaneHistory(flowHistory.analysisFlow());
        data[DEVELOPMENT_INDEX] = swimLaneHistory(flowHistory.developmentFlow());
        data[WATING_FOR_TEST_INDEX] = swimLaneHistory(flowHistory.waitingForTestFlow());
        data[TESTING_INDEX] = swimLaneHistory(flowHistory.testingFlow());
        return data;
    }

    private double[] swimLaneHistory(List<Integer> history) {
        return history.stream().mapToDouble(Integer::doubleValue).toArray();
    }

    public static CumulativeFlowDiagram cumulativeFlowChart(EventStream<FlowHistory> flowHistory) {
        return new CumulativeFlowDiagram(flowHistory);
    }
}
