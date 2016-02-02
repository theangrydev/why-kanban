package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.KanbanBoard;

public class CompletedStoriesRecorder implements StatisticsRecorder {

	private int totalStoriesCompleted;

	@Override
	public void recordDay(Day day, KanbanBoard kanbanBoard) {
		totalStoriesCompleted += kanbanBoard.storiesCompleted().size();
	}

	public static CompletedStoriesRecorder completedStoriesRecorder() {
		return new CompletedStoriesRecorder();
	}

	public int totalStoriesCompleted() {
		return totalStoriesCompleted;
	}
}
