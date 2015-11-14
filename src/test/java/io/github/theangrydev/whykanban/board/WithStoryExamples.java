package io.github.theangrydev.whykanban.board;

import io.github.theangrydev.whykanban.simulation.WithDayExamples;

public interface WithStoryExamples extends WithDayExamples {

	default Story anyStory() {
		return Story.aStory();
	}

	default Story storyWithLeadTime(int leadTime) {
		Story story = Story.aStory();
		story.markReady(dayNumber(1));
		story.markComplete(dayNumber(1 + leadTime));
		return story;
	}
}
