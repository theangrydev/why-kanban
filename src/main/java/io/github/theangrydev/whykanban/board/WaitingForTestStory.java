package io.github.theangrydev.whykanban.board;

public class WaitingForTestStory implements StoryInLane {

	private final Story story;

	private WaitingForTestStory(Story story) {
		this.story = story;
	}

	public static WaitingForTestStory waitingForTestStory(Story story) {
		return new WaitingForTestStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
