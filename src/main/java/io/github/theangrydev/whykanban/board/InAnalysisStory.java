package io.github.theangrydev.whykanban.board;

public class InAnalysisStory implements StoryInLane {

	private final Story story;

	private InAnalysisStory(Story story) {
		this.story = story;
	}

	public static InAnalysisStory inAnalysisStory(Story story) {
		return new InAnalysisStory(story);
	}

	@Override
	public Story story() {
		return story;
	}
}
