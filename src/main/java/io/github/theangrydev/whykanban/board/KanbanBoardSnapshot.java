package io.github.theangrydev.whykanban.board;

import java.util.List;

public class KanbanBoardSnapshot implements KanbanBoardState {

    private List<ReadyToPlayStory> storiesReadyToPlay;
    private List<InAnalysisStory> storiesInAnalysis;
    private List<InDevelopmentStory> storiesInDevelopment;
    private List<WaitingForTestStory> storiesWaitingForTest;
    private List<CompletedStory> storiesCompleted;
    private List<InTestingStory> storiesInTesting;

    private KanbanBoardSnapshot(List<ReadyToPlayStory> storiesReadyToPlay, List<InAnalysisStory> storiesInAnalysis, List<InDevelopmentStory> storiesInDevelopment, List<WaitingForTestStory> storiesWaitingForTest, List<InTestingStory> storiesInTesting, List<CompletedStory> storiesCompleted) {
        this.storiesReadyToPlay = storiesReadyToPlay;
        this.storiesInAnalysis = storiesInAnalysis;
        this.storiesInDevelopment = storiesInDevelopment;
        this.storiesWaitingForTest = storiesWaitingForTest;
        this.storiesInTesting = storiesInTesting;
        this.storiesCompleted = storiesCompleted;
    }

    public static KanbanBoardState snapshot(KanbanBoard kanbanBoard) {
        return new KanbanBoardSnapshot(kanbanBoard.storiesReadyToPlay(), kanbanBoard.storiesInAnalysis(), kanbanBoard.storiesInDevelopment(), kanbanBoard.storiesWaitingForTest(), kanbanBoard.storiesInTesting(), kanbanBoard.storiesCompleted());
    }

    @Override
    public List<ReadyToPlayStory> storiesReadyToPlay() {
        return storiesReadyToPlay;
    }

    @Override
    public List<InAnalysisStory> storiesInAnalysis() {
        return storiesInAnalysis;
    }

    @Override
    public List<InDevelopmentStory> storiesInDevelopment() {
        return storiesInDevelopment;
    }

    @Override
    public List<WaitingForTestStory> storiesWaitingForTest() {
        return storiesWaitingForTest;
    }

    @Override
    public List<CompletedStory> storiesCompleted() {
        return storiesCompleted;
    }

    @Override
    public List<InTestingStory> storiesInTesting() {
        return storiesInTesting;
    }
}
