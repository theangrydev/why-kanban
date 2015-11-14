package io.github.theangrydev.whykanban.board;

public class CompletedStory implements StoryInLane {

	private final Story story;

	private CompletedStory(Story story) {
		this.story = story;
	}

	public static CompletedStory completedStory(Story story) {
		return new CompletedStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
