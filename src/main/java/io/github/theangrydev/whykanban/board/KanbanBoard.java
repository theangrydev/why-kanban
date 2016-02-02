package io.github.theangrydev.whykanban.board;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.reactfx.EventSource;
import org.reactfx.EventStream;

import java.util.List;
import java.util.function.Function;

import static io.github.theangrydev.whykanban.board.KanbanBoardSnapshot.snapshot;

public class KanbanBoard implements KanbanBoardState {

	private EventSource<KanbanBoardState> boardChanges;
	private WorkLane<ReadyToPlayStory> storiesReadyToPlay;
	private WorkLane<InAnalysisStory> storiesInAnalysis;
	private WorkLane<InDevelopmentStory> storiesInDevelopment;
	private WorkLane<WaitingForTestStory> storiesWaitingForTest;
	private WorkLane<InTestingStory> storiesInTesting;
	private WorkLane<CompletedStory> storiesCompleted;

	private KanbanBoard() {
		boardChanges = new EventSource<>();
		storiesReadyToPlay = new WorkLane<>();
		storiesInAnalysis = new WorkLane<>();
		storiesInDevelopment = new WorkLane<>();
		storiesWaitingForTest = new WorkLane<>();
		storiesInTesting = new WorkLane<>();
		storiesCompleted = new WorkLane<>();
	}

	public EventStream<KanbanBoardState> boardChanges() {
		return boardChanges;
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

	@Override
	public List<ReadyToPlayStory> storiesReadyToPlay() {
		return Lists.newArrayList(storiesReadyToPlay);
	}

	@Override
	public List<InAnalysisStory> storiesInAnalysis() {
		return Lists.newArrayList(storiesInAnalysis);
	}

	@Override
	public List<InDevelopmentStory> storiesInDevelopment() {
		return Lists.newArrayList(storiesInDevelopment);
	}

	@Override
	public List<WaitingForTestStory> storiesWaitingForTest() {
		return Lists.newArrayList(storiesWaitingForTest);
	}

	@Override
	public List<CompletedStory> storiesCompleted() {
		return Lists.newArrayList(storiesCompleted);
	}

	@Override
	public List<InTestingStory> storiesInTesting() {
		return Lists.newArrayList(storiesInTesting);
	}

	public void addReadyToPlayStory(Story story) {
		if (!storiesReadyToPlay.workInProgressLimitHasBeenReached()) {
			storiesReadyToPlay.add(ReadyToPlayStory.readyToPlayStory(story));
		}
		pushSnapshot();
	}

	private void pushSnapshot() {
		boardChanges.push(snapshot(this));
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
		pushSnapshot();
	}
}
