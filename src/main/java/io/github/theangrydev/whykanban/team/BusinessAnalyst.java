package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import static io.github.theangrydev.whykanban.team.WorkOnLaneStrategy.workOnLaneStrategy;
import static io.github.theangrydev.whykanban.team.WorkOnSeveralLanesStrategy.workOnSeveralLanesStrategy;

public class BusinessAnalyst implements TeamMember {

	private final WorkStrategy workStrategy = workOnSeveralLanesStrategy(
		workOnLaneStrategy(KanbanBoard::storiesReadyToPlay, KanbanBoard::moveToAnalysis),
		workOnLaneStrategy(KanbanBoard::storiesInAnalysis, KanbanBoard::moveToInDevelopment));

	public static BusinessAnalyst businessAnalyst() {
		return new BusinessAnalyst();
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		workStrategy.doWork(kanbanBoard);
	}
}
