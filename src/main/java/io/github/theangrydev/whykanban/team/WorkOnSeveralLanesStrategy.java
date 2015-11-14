package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import java.util.Arrays;
import java.util.List;

public class WorkOnSeveralLanesStrategy implements WorkStrategy {
	private final List<WorkOnLaneStrategy<?>> workOnLaneStrategies;

	private WorkOnSeveralLanesStrategy(List<WorkOnLaneStrategy<?>> workOnLaneStrategies) {
		this.workOnLaneStrategies = workOnLaneStrategies;
	}

	public static WorkOnSeveralLanesStrategy workOnSeveralLanesStrategy(WorkOnLaneStrategy<?>... workOnLaneStrategies) {
		return new WorkOnSeveralLanesStrategy(Arrays.asList(workOnLaneStrategies));
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		workOnLaneStrategies.stream().forEach(workOnLaneStrategy -> workOnLaneStrategy.doWork(kanbanBoard));
	}
}
