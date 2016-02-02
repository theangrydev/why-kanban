package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import io.github.theangrydev.whykanban.board.WithStoryExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.theangrydev.whykanban.simulation.CompletedStoriesRecorder.completedStoriesRecorder;

public class CompletedStoriesRecorderTest implements WithAssertions, WithStoryExamples, WithDayExamples, WithKanbanBoardExamples {

	@Test
	public void storiesCompletedPerDayIsInitiallyZero() {
		assertThat(completedStoriesRecorder().totalStoriesCompleted()).isEqualTo(0);
	}

	@Test
	public void totalStoriesCompletedIsTheTotalNumberOfCompletedStoriesAcrossAllDays() {
		CompletedStoriesRecorder completedStoriesRecorder = completedStoriesRecorder();

		for (int i = 1; i <= 10; i++) {
			completedStoriesRecorder.recordDay(dayNumber(i), boardWithCompletedStories(anyStory(), anyStory()));
		}

		assertThat(completedStoriesRecorder.totalStoriesCompleted()).isEqualTo(20);
	}
}
