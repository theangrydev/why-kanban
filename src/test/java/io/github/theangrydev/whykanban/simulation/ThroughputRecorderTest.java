package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import io.github.theangrydev.whykanban.board.WithStoryExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.theangrydev.whykanban.simulation.ThroughputRecorder.throughputRecorder;

public class ThroughputRecorderTest implements WithAssertions, WithStoryExamples, WithDayExamples, WithKanbanBoardExamples {

	@Test
	public void storiesCompletedPerDayIsInitiallyZero() {
		assertThat(throughputRecorder().storiesCompletedPerDay()).isEqualTo(0);
	}

	@Test
	public void storiesCompletedPerDayIsTheNumberOfCompletedStoriesDividedByTheCurrentDayNumber() {
		ThroughputRecorder throughputRecorder = throughputRecorder();

		throughputRecorder.recordDay(dayNumber(10), boardWithCompletedStories(anyStory(), anyStory()));

		assertThat(throughputRecorder.storiesCompletedPerDay()).isEqualTo((double) 2 / 10);
	}
}
