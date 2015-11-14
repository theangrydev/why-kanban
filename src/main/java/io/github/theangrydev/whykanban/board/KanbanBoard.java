package io.github.theangrydev.whykanban.board;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;

public class KanbanBoard {
	private WorkLane<ReadyToPlayStory> storiesReadyToPlay;
	private WorkLane<InAnalysisStory> storiesInAnalysis;
	private WorkLane<InDevelopmentStory> storiesInDevelopment;
	private WorkLane<WaitingForTestStory> storiesWaitingForTest;
	private WorkLane<InTestingStory> storiesInTesting;
	private WorkLane<CompletedStory> storiesCompleted;

	private KanbanBoard() {
		storiesReadyToPlay = new WorkLane<>();
		storiesInAnalysis = new WorkLane<>();
		storiesInDevelopment = new WorkLane<>();
		storiesWaitingForTest = new WorkLane<>();
		storiesInTesting = new WorkLane<>();
		storiesCompleted = new WorkLane<>();
	}

	public static KanbanBoard emptyBoard() {
		return new KanbanBoard();
	}

	public KanbanBoard withWorkInProgressLimit(int workInProgressLimit) {
		storiesReadyToPlay.setWorkInProgressLimit(workInProgressLimit);
		storiesInAnalysis.setWorkInProgressLimit(workInProgressLimit);
		storiesInDevelopment.setWorkInProgressLimit(workInProgressLimit);
		storiesWaitingForTest.setWorkInProgressLimit(workInProgressLimit);
		storiesInTesting.setWorkInProgressLimit(workInProgressLimit);
		return this;
	}

	public List<ReadyToPlayStory> storiesReadyToPlay() {
		return Lists.newArrayList(storiesReadyToPlay);
	}

	public List<InAnalysisStory> storiesInAnalysis() {
		return Lists.newArrayList(storiesInAnalysis);
	}

	public List<InDevelopmentStory> storiesInDevelopment() {
		return Lists.newArrayList(storiesInDevelopment);
	}

	public List<WaitingForTestStory> storiesWaitingForTest() {
		return Lists.newArrayList(storiesWaitingForTest);
	}

	public List<CompletedStory> storiesCompleted() {
		return Lists.newArrayList(storiesCompleted);
	}

	public List<InTestingStory> storiesInTesting() {
		return Lists.newArrayList(storiesInTesting);
	}

	public void addReadyToPlayStory(Story story) {
		if (!storiesReadyToPlay.workInProgressLimitHasBeenReached()) {
			storiesReadyToPlay.add(ReadyToPlayStory.readyToPlayStory(story));
		}
	}

	public void moveToAnalysis(ReadyToPlayStory readyToPlayStory) {
		moveToColumn(readyToPlayStory, storiesReadyToPlay, storiesInAnalysis, InAnalysisStory::inAnalysisStory);
	}

	public void moveToInDevelopment(InAnalysisStory inAnalysisStory) {
		moveToColumn(inAnalysisStory, storiesInAnalysis, storiesInDevelopment, InDevelopmentStory::inDevelopmentStory);
	}

	public void moveToWaitingForTest(InDevelopmentStory inDevelopmentStory) {
		moveToColumn(inDevelopmentStory, storiesInDevelopment, storiesWaitingForTest, WaitingForTestStory::waitingForTestStory);
	}

	public void moveToInTesting(WaitingForTestStory waitingForTestStory) {
		moveToColumn(waitingForTestStory, storiesWaitingForTest, storiesInTesting, InTestingStory::inTestingStory);
	}

	public void moveToCompleted(InTestingStory inTestingStory) {
		moveToColumn(inTestingStory, storiesInTesting, storiesCompleted, CompletedStory::completedStory);
	}

	public void removeCompletedStories() {
		storiesCompleted.clear();
	}

	private <FromStory extends StoryInLane, ToStory> void moveToColumn(FromStory story, WorkLane<FromStory> fromStories, WorkLane<ToStory> toStories, Function<Story, ToStory> transition) {
		Preconditions.checkState(fromStories.contains(story), "%s story '%s' is not in the column!", story.getClass().getSimpleName(), story);
		if (!toStories.workInProgressLimitHasBeenReached()) {
			fromStories.remove(story);
			toStories.add(transition.apply(story.story()));
		}
	}
}
