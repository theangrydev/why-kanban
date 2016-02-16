package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class CumulativeFlowRecorder implements StatisticsRecorder {

    private List<Integer> readyFlow = new ArrayList<>();
    private List<Integer> analysisFlow = new ArrayList<>();
    private List<Integer> developmentFlow = new ArrayList<>();
    private List<Integer> waitingForTestFlow = new ArrayList<>();
    private List<Integer> testingFlow = new ArrayList<>();

    @Override
    public void recordDay(Day day, KanbanBoard kanbanBoard) {
        readyFlow.add(kanbanBoard.storiesReadyToPlay().size());
        analysisFlow.add(kanbanBoard.storiesInAnalysis().size());
        developmentFlow.add(kanbanBoard.storiesInDevelopment().size());
        waitingForTestFlow.add(kanbanBoard.storiesWaitingForTest().size());
        testingFlow.add(kanbanBoard.storiesInTesting().size());
    }

    public FlowHistory flowHistory() {
        return FlowHistory.flowHistory(newArrayList(readyFlow), newArrayList(analysisFlow), newArrayList(developmentFlow), newArrayList(waitingForTestFlow), newArrayList(testingFlow));
    }

    public static CumulativeFlowRecorder cumulativeFlowRecorder() {
        return new CumulativeFlowRecorder();
    }
}
