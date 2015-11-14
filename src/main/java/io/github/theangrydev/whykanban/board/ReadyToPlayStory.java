package io.github.theangrydev.whykanban.board;

public class ReadyToPlayStory implements StoryInLane {

	private final Story story;

	private ReadyToPlayStory(Story story) {
		this.story = story;
	}

	public static ReadyToPlayStory readyToPlayStory(Story story) {
		return new ReadyToPlayStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
