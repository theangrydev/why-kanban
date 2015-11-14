package io.github.theangrydev.whykanban.board;

import io.github.theangrydev.whykanban.simulation.Day;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class StoryTest implements WithAssertions {

	@Test
	public void leadTimeIsTheDifferenceBetweenTheDayNumberTheStoryWasReadyToPlayAndTheDayItWasCompleted() {
		Story story = Story.aStory();

		Day dayReady = Day.firstDay();
		story.markReady(dayReady);

		Day dayCompleted = dayReady.nextDay();
		story.markComplete(dayCompleted);

		assertThat(story.leadTime()).isEqualTo(1);
	}
}
