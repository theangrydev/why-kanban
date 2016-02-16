package io.github.theangrydev.whykanban.simulation;

import java.util.List;

public class FlowHistory {

    private final List<Integer> readyFlow;
    private final List<Integer> analysisFlow;
    private final List<Integer> developmentFlow;
    private final List<Integer> waitingForTestFlow;
    private final List<Integer> testingFlow;

    public FlowHistory(List<Integer> readyFlow, List<Integer> analysisFlow, List<Integer> developmentFlow, List<Integer> waitingForTestFlow, List<Integer> testingFlow) {
        this.readyFlow = readyFlow;
        this.analysisFlow = analysisFlow;
        this.developmentFlow = developmentFlow;
        this.waitingForTestFlow = waitingForTestFlow;
        this.testingFlow = testingFlow;
    }

    public static FlowHistory flowHistory(List<Integer> readyFlow, List<Integer> analysisFlow, List<Integer> developmentFlow, List<Integer> waitingForTestFlow, List<Integer> testingFlow) {
        return new FlowHistory(readyFlow, analysisFlow, developmentFlow, waitingForTestFlow, testingFlow);
    }

    public List<Integer> readyFlow() {
        return readyFlow;
    }

    public List<Integer> analysisFlow() {
        return analysisFlow;
    }

    public List<Integer> developmentFlow() {
        return developmentFlow;
    }

    public List<Integer> waitingForTestFlow() {
        return waitingForTestFlow;
    }

    public List<Integer> testingFlow() {
        return testingFlow;
    }
}
