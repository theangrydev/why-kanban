package io.github.theangrydev.whykanban.board;

import java.util.List;
import java.util.Objects;

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

    public static KanbanBoardState snapshot(List<ReadyToPlayStory> storiesReadyToPlay, List<InAnalysisStory> storiesInAnalysis, List<InDevelopmentStory> storiesInDevelopment, List<WaitingForTestStory> storiesWaitingForTest, List<InTestingStory> storiesInTesting, List<CompletedStory> storiesCompleted) {
        return new KanbanBoardSnapshot(storiesReadyToPlay, storiesInAnalysis, storiesInDevelopment, storiesWaitingForTest, storiesInTesting, storiesCompleted);
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

    @Override
    public int hashCode() {
        return Objects.hash(storiesReadyToPlay, storiesInAnalysis, storiesInDevelopment, storiesWaitingForTest, storiesCompleted, storiesInTesting);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final KanbanBoardSnapshot other = (KanbanBoardSnapshot) obj;
        return Objects.equals(this.storiesReadyToPlay, other.storiesReadyToPlay)
            && Objects.equals(this.storiesInAnalysis, other.storiesInAnalysis)
            && Objects.equals(this.storiesInDevelopment, other.storiesInDevelopment)
            && Objects.equals(this.storiesWaitingForTest, other.storiesWaitingForTest)
            && Objects.equals(this.storiesCompleted, other.storiesCompleted)
            && Objects.equals(this.storiesInTesting, other.storiesInTesting);
    }
}
