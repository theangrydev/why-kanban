package io.github.theangrydev.whykanban.board;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.theangrydev.whykanban.board.Story.aStory;

public class KanbanBoardTest implements WithAssertions, WithKanbanBoardExamples {

	@Test
	public void anEmptyBoardHasNoStories() {
		KanbanBoard kanbanBoard = KanbanBoard.emptyBoard();

		assertThat(kanbanBoard.storiesReadyToPlay()).isEmpty();
		assertThat(kanbanBoard.storiesInAnalysis()).isEmpty();
		assertThat(kanbanBoard.storiesInDevelopment()).isEmpty();
		assertThat(kanbanBoard.storiesWaitingForTest()).isEmpty();
		assertThat(kanbanBoard.storiesInTesting()).isEmpty();
		assertThat(kanbanBoard.storiesCompleted()).isEmpty();
	}

	@Test
	public void storiesCanBePulledFromTheBacklogToReadyToPlay() {
		KanbanBoard kanbanBoard = KanbanBoard.emptyBoard();

		kanbanBoard.addReadyToPlayStory(aStory());

		assertThat(kanbanBoard.storiesReadyToPlay()).hasSize(1);
	}

	@Test
	public void storiesCanBePulledFromReadyToPlayToAnalysis() {
		KanbanBoard kanbanBoard = boardWithOneReadyToPlayStory();
		ReadyToPlayStory readyToPlayStory = kanbanBoard.storiesReadyToPlay().get(0);

		kanbanBoard.moveToAnalysis(readyToPlayStory);

		assertThat(kanbanBoard.storiesReadyToPlay()).isEmpty();
		assertThat(kanbanBoard.storiesInAnalysis()).hasSize(1);
	}

	@Test
	public void storiesCanBePulledFromInAnalysisToInDevelopment() {
		KanbanBoard kanbanBoard = boardWithOneInAnalysisStory();
		InAnalysisStory inAnalysisStory = kanbanBoard.storiesInAnalysis().get(0);

		kanbanBoard.moveToInDevelopment(inAnalysisStory);

		assertThat(kanbanBoard.storiesInAnalysis()).isEmpty();
		assertThat(kanbanBoard.storiesInDevelopment()).hasSize(1);
	}

	@Test
	public void storiesCanBePulledFromInDevelopmentToWaitingForTest() {
		KanbanBoard kanbanBoard = boardWithOneInDevelopmentStory();
		InDevelopmentStory inDevelopmentStory = kanbanBoard.storiesInDevelopment().get(0);

		kanbanBoard.moveToWaitingForTest(inDevelopmentStory);

		assertThat(kanbanBoard.storiesInDevelopment()).isEmpty();
		assertThat(kanbanBoard.storiesWaitingForTest()).hasSize(1);
	}

	@Test
	public void storiesCanBePulledFromWaitingForTestToInTesting() {
		KanbanBoard kanbanBoard = boardWithOneWaitingForTestStory();
		WaitingForTestStory waitingForTestStory = kanbanBoard.storiesWaitingForTest().get(0);

		kanbanBoard.moveToInTesting(waitingForTestStory);

		assertThat(kanbanBoard.storiesWaitingForTest()).isEmpty();
		assertThat(kanbanBoard.storiesInTesting()).hasSize(1);
	}

	@Test
	public void storiesCanBePulledFromInTestingToCompleted() {
		KanbanBoard kanbanBoard = boardWithOneInTestingStory();
		InTestingStory inTestingStory = kanbanBoard.storiesInTesting().get(0);

		kanbanBoard.moveToCompleted(inTestingStory);

		assertThat(kanbanBoard.storiesInTesting()).isEmpty();
		assertThat(kanbanBoard.storiesCompleted()).hasSize(1);
	}

	@Test
	public void completedStoriesCanBeRemoved() {
		KanbanBoard kanbanBoard = boardWithOneCompletedStory();

		kanbanBoard.removeCompletedStories();

		assertThat(kanbanBoard.storiesCompleted()).isEmpty();
	}
}
