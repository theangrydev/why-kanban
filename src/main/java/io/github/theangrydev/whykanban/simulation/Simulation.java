package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.CompletedStory;
import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.team.Team;

public class Simulation {

	private final Backlog backlog;
	private final KanbanBoard kanbanBoard;
	private final Team team;
	private Day currentDay;
	private LeadTimeDistribution leadTimeDistribution;
	private ThroughputRecorder throughputRecorder;
	private CompletedStoriesRecorder completedStoriesRecorder;

	private Simulation(Backlog backlog, KanbanBoard kanbanBoard, Team team) {
		this.backlog = backlog;
		this.kanbanBoard = kanbanBoard;
		this.team = team;
		this.currentDay = Day.firstDay();
		this.leadTimeDistribution = LeadTimeDistribution.leadTimeDistribution();
		this.throughputRecorder = ThroughputRecorder.throughputRecorder();
		this.completedStoriesRecorder = CompletedStoriesRecorder.completedStoriesRecorder();
	}

	public static Simulation simulation(Backlog backlog, KanbanBoard kanbanBoard, Team team) {
		return new Simulation(backlog, kanbanBoard, team);
	}

	public void advanceOneDay() {
		currentDay = currentDay.nextDay();
		backlog.stories().stream().peek(story -> story.markReady(currentDay)).forEach(kanbanBoard::addReadyToPlayStory);
		team.doWork(kanbanBoard);
		kanbanBoard.storiesCompleted().stream().map(CompletedStory::story).forEach(story -> story.markComplete(currentDay));
		leadTimeDistribution.recordDay(currentDay, kanbanBoard);
		throughputRecorder.recordDay(currentDay, kanbanBoard);
		completedStoriesRecorder.recordDay(currentDay, kanbanBoard);
		kanbanBoard.removeCompletedStories();
	}

	public double averageLeadTime() {
		return leadTimeDistribution.averageLeadTime();
	}

	public double storiesCompletedPerDay() {
		return throughputRecorder.storiesCompletedPerDay();
	}

	public KanbanBoard kanbanBoard() {
		return kanbanBoard;
	}

	public void advanceDays(int numberOfDays) {
		for (int i = 0; i < numberOfDays; i++) {
			advanceOneDay();
		}
	}

	public int currentDayNumber() {
		return currentDay.dayNumber();
	}

	public int totalStoriesCompleted() {
		return completedStoriesRecorder.totalStoriesCompleted();
	}
}
