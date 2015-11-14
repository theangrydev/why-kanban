package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import static io.github.theangrydev.whykanban.team.WorkOnLaneStrategy.workOnLaneStrategy;

public class BusinessAnalyst implements TeamMember {

	private final WorkStrategy workStrategy = workOnLaneStrategy(KanbanBoard::storiesInAnalysis, KanbanBoard::moveToInDevelopment);

	public static BusinessAnalyst businessAnalyst() {
		return new BusinessAnalyst();
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		workStrategy.doWork(kanbanBoard);
	}
}
