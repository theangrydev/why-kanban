package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.CompletedStory;
import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.Story;

public class LeadTimeDistribution implements StatisticsRecorder {
	private double averageLeadTime;

	public static LeadTimeDistribution leadTimeDistribution() {
		return new LeadTimeDistribution();
	}

	@Override
	public void recordDay(Day day, KanbanBoard kanbanBoard) {
		averageLeadTime = kanbanBoard.storiesCompleted().stream().map(CompletedStory::story).mapToInt(Story::leadTime).average().orElse(0);
	}

	public double averageLeadTime() {
		return averageLeadTime;
	}
}
