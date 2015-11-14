package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.KanbanBoard;

public class ThroughputRecorder implements StatisticsRecorder {

	private int totalStoriesCompleted;
	private double storiesCompletedPerDay;

	@Override
	public void recordDay(Day day, KanbanBoard kanbanBoard) {
		totalStoriesCompleted += kanbanBoard.storiesCompleted().size();
		storiesCompletedPerDay = (double) totalStoriesCompleted / day.dayNumber();
	}

	public static ThroughputRecorder throughputRecorder() {
		return new ThroughputRecorder();
	}

	public double storiesCompletedPerDay() {
		return storiesCompletedPerDay;
	}
}
