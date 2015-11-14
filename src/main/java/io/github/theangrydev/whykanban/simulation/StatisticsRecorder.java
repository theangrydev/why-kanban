package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.KanbanBoard;

public interface StatisticsRecorder {
	void recordDay(Day day, KanbanBoard kanbanBoard);
}
