package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.CompletedStory;
import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.Story;

import java.util.ArrayList;
import java.util.List;

public class LeadTimeDistribution implements StatisticsRecorder {
	private List<Integer> leadTimes;
	private double averageLeadTime;

	private LeadTimeDistribution() {
		leadTimes = new ArrayList<>();
	}

	public static LeadTimeDistribution leadTimeDistribution() {
		return new LeadTimeDistribution();
	}

	@Override
	public void recordDay(Day day, KanbanBoard kanbanBoard) {
		kanbanBoard.storiesCompleted().stream().map(CompletedStory::story).mapToInt(Story::leadTime).forEach(leadTimes::add);
		averageLeadTime = leadTimes.stream().mapToInt(value -> value).average().orElse(0);
	}

	public double averageLeadTime() {
		return averageLeadTime;
	}
}
