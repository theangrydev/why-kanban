package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class WorkOnLaneStrategy<StoryToWorkOn> implements WorkStrategy {

	private final Function<KanbanBoard, List<StoryToWorkOn>> pullStory;
	private final BiConsumer<KanbanBoard, StoryToWorkOn> finishWorkOnStory;

	private WorkOnLaneStrategy(Function<KanbanBoard, List<StoryToWorkOn>> pullStory, BiConsumer<KanbanBoard, StoryToWorkOn> finishWorkOnStory) {
		this.pullStory = pullStory;
		this.finishWorkOnStory = finishWorkOnStory;
	}

	public static <StoryToWorkOn> WorkOnLaneStrategy<StoryToWorkOn> workOnLaneStrategy(Function<KanbanBoard, List<StoryToWorkOn>> pullStory, BiConsumer<KanbanBoard, StoryToWorkOn> finishWorkOnStory) {
		return new WorkOnLaneStrategy<>(pullStory, finishWorkOnStory);
	}

	@Override
	public void doWork(KanbanBoard kanbanBoard) {
		pullStory.apply(kanbanBoard).stream().findFirst().ifPresent(storyToWorkOn -> finishWorkOnStory.accept(kanbanBoard, storyToWorkOn));
	}
}
