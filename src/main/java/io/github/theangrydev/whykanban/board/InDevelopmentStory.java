package io.github.theangrydev.whykanban.board;

public class InDevelopmentStory implements StoryInLane {

	private final Story story;

	private InDevelopmentStory(Story story) {
		this.story = story;
	}

	public static InDevelopmentStory inDevelopmentStory(Story story) {
		return new InDevelopmentStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
