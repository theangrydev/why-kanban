package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import static io.github.theangrydev.whykanban.team.WorkOnLaneStrategy.workOnLaneStrategy;
import static io.github.theangrydev.whykanban.team.WorkOnSeveralLanesStrategy.workOnSeveralLanesStrategy;

public class Tester implements TeamMember {

	private final WorkStrategy workStrategy = workOnSeveralLanesStrategy(
		workOnLaneStrategy(KanbanBoard::storiesWaitingForTest, KanbanBoard::moveToInTesting),
		workOnLaneStrategy(KanbanBoard::storiesInTesting, KanbanBoard::moveToCompleted));

	public static Tester tester() {
		return new Tester();
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		workStrategy.doWork(kanbanBoard);
	}
}
