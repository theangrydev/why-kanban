package io.github.theangrydev.whykanban.board;

public class InTestingStory implements StoryInLane {

	private final Story story;

	private InTestingStory(Story story) {
		this.story = story;
	}

	public static InTestingStory inTestingStory(Story story) {
		return new InTestingStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
