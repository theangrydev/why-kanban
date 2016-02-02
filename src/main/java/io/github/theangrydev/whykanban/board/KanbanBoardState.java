package io.github.theangrydev.whykanban.board;

import java.util.List;

public interface KanbanBoardState {
    List<ReadyToPlayStory> storiesReadyToPlay();

    List<InAnalysisStory> storiesInAnalysis();

    List<InDevelopmentStory> storiesInDevelopment();

    List<WaitingForTestStory> storiesWaitingForTest();

    List<CompletedStory> storiesCompleted();

    List<InTestingStory> storiesInTesting();
}
