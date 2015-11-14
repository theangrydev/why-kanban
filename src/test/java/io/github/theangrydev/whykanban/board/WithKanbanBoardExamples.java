package io.github.theangrydev.whykanban.board;

import java.util.Arrays;

import static io.github.theangrydev.whykanban.board.Story.aStory;

public interface WithKanbanBoardExamples {

	default KanbanBoard boardWithOneReadyToPlayStory() {
		KanbanBoard kanbanBoard = KanbanBoard.emptyBoard();
		kanbanBoard.addReadyToPlayStory(aStory());
		return kanbanBoard;
	}

	default KanbanBoard boardWithOneInAnalysisStory() {
		KanbanBoard kanbanBoard = boardWithOneReadyToPlayStory();
		ReadyToPlayStory readyToPlayStory = kanbanBoard.storiesReadyToPlay().get(0);
		kanbanBoard.moveToAnalysis(readyToPlayStory);
		return kanbanBoard;
	}

	default KanbanBoard boardWithOneInDevelopmentStory() {
		KanbanBoard kanbanBoard = boardWithOneInAnalysisStory();
		InAnalysisStory inAnalysisStory = kanbanBoard.storiesInAnalysis().get(0);
		kanbanBoard.moveToInDevelopment(inAnalysisStory);
		return kanbanBoard;
	}

	default KanbanBoard boardWithOneWaitingForTestStory() {
		KanbanBoard kanbanBoard = boardWithOneInDevelopmentStory();
		InDevelopmentStory inDevelopmentStory = kanbanBoard.storiesInDevelopment().get(0);
		kanbanBoard.moveToWaitingForTest(inDevelopmentStory);
		return kanbanBoard;
	}

	default KanbanBoard boardWithOneInTestingStory() {
		KanbanBoard kanbanBoard = boardWithOneWaitingForTestStory();
		WaitingForTestStory waitingForTestStory = kanbanBoard.storiesWaitingForTest().get(0);
		kanbanBoard.moveToInTesting(waitingForTestStory);
		return kanbanBoard;
	}

	default KanbanBoard boardWithOneCompletedStory() {
		return boardWithCompletedStories(Story.aStory());
	}

	default KanbanBoard boardWithCompletedStories(Story... stories) {
		KanbanBoard kanbanBoard = KanbanBoard.emptyBoard();
		Arrays.stream(stories).forEach(kanbanBoard::addReadyToPlayStory);
		kanbanBoard.storiesReadyToPlay().stream().forEach(kanbanBoard::moveToAnalysis);
		kanbanBoard.storiesInAnalysis().stream().forEach(kanbanBoard::moveToInDevelopment);
		kanbanBoard.storiesInDevelopment().stream().forEach(kanbanBoard::moveToWaitingForTest);
		kanbanBoard.storiesWaitingForTest().stream().forEach(kanbanBoard::moveToInTesting);
		kanbanBoard.storiesInTesting().stream().forEach(kanbanBoard::moveToCompleted);
		return kanbanBoard;
	}
}
