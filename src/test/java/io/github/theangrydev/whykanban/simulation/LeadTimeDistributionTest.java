package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import io.github.theangrydev.whykanban.board.WithStoryExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.theangrydev.whykanban.simulation.LeadTimeDistribution.leadTimeDistribution;

public class LeadTimeDistributionTest implements WithAssertions, WithKanbanBoardExamples, WithDayExamples, WithStoryExamples {

	@Test
	public void averageLeadTimeIsInitiallyZero() {
		assertThat(leadTimeDistribution().averageLeadTime()).isEqualTo(0);
	}

	@Test
	public void averageLeadTimeIsTheAverageOfTheLeadTimesOfAllTheCompletedStories() {
		LeadTimeDistribution leadTimeDistribution = LeadTimeDistribution.leadTimeDistribution();

		leadTimeDistribution.recordDay(anyDay(), boardWithCompletedStories(storyWithLeadTime(5), storyWithLeadTime(7)));

		assertThat(leadTimeDistribution.averageLeadTime()).isEqualTo((5 + 7) / 2);
	}
}
