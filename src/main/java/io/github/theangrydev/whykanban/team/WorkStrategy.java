package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

public interface WorkStrategy {
	void doWork(KanbanBoard kanbanBoard);
}
