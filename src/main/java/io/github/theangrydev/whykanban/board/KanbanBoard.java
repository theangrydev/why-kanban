package io.github.theangrydev.whykanban.board;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.unmodifiableList;

public class KanbanBoard {
	private List<ReadyToPlayStory> storiesReadyToPlay;
	private List<InAnalysisStory> storiesInAnalysis;
	private List<InDevelopmentStory> storiesInDevelopment;
	private List<WaitingForTestStory> storiesWaitingForTest;
	private List<InTestingStory> storiesInTesting;
	private List<CompletedStory> storiesCompleted;

	private KanbanBoard() {
		storiesReadyToPlay = new ArrayList<>();
		storiesInAnalysis = new ArrayList<>();
		storiesInDevelopment = new ArrayList<>();
		storiesWaitingForTest = new ArrayList<>();
		storiesInTesting = new ArrayList<>();
		storiesCompleted = new ArrayList<>();
	}

	public static KanbanBoard emptyBoard() {
		return new KanbanBoard();
	}

	public List<ReadyToPlayStory> storiesReadyToPlay() {
		return unmodifiableList(storiesReadyToPlay);
	}

	public List<InAnalysisStory> storiesInAnalysis() {
		return unmodifiableList(storiesInAnalysis);
	}

	public List<InDevelopmentStory> storiesInDevelopment() {
		return unmodifiableList(storiesInDevelopment);
	}

	public List<WaitingForTestStory> storiesWaitingForTest() {
		return unmodifiableList(storiesWaitingForTest);
	}

	public List<CompletedStory> storiesCompleted() {
		return unmodifiableList(storiesCompleted);
	}

	public List<InTestingStory> storiesInTesting() {
		return unmodifiableList(storiesInTesting);
	}

	public void addReadyToPlayStory(Story story) {
		storiesReadyToPlay.add(ReadyToPlayStory.readyToPlayStory(story));
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

	private <FromStory extends StoryInLane, ToStory> void moveToColumn(FromStory story, List<FromStory> fromStories, List<ToStory> toStories, Function<Story, ToStory> transition) {
		Preconditions.checkState(fromStories.contains(story), "%s story '%s' is not in the column!", story.getClass().getSimpleName(), story);
		fromStories.remove(story);
		toStories.add(transition.apply(story.story()));
	}
}
