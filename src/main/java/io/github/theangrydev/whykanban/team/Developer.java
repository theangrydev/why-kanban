package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import static io.github.theangrydev.whykanban.team.WorkOnLaneStrategy.workOnLaneStrategy;

public class Developer implements TeamMember {

	private final WorkStrategy workStrategy = workOnLaneStrategy(KanbanBoard::storiesInDevelopment, KanbanBoard::moveToWaitingForTest);

	public static Developer developer() {
		return new Developer();
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		workStrategy.doWork(kanbanBoard);
	}
}
