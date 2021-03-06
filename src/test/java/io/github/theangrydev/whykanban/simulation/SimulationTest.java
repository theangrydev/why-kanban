package io.github.theangrydev.whykanban.simulation;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.team.WithTeamExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.data.Percentage.withPercentage;

@RunWith(TableRunner.class)
public class SimulationTest implements WithAssertions, WithTeamExamples {

	@Table({
		@Row({"1", "0"}),
		@Row({"10", "2.5"}),
		@Row({"100", "25"}),
		@Row({"1000", "250"}),
	})
	@Test
	public void averageLeadTimeIncreasesWhenTheTeamWorksSlowerThanTheBacklogRate(String numberOfDays, String averageLeadTime) {
		Simulation simulation = Simulation.simulation(Backlog.backlog(2), KanbanBoard.emptyBoard(), teamWithOneOfEachSpecialist());

		simulation.advanceDays(Integer.parseInt(numberOfDays));

		assertThat(simulation.averageLeadTime()).isCloseTo(Double.valueOf(averageLeadTime), withPercentage(1));
	}

	@Table({
		@Row({"1", "1"}),
		@Row({"10", "1"}),
		@Row({"100", "1"}),
		@Row({"1000", "1"}),
	})
	@Test
	public void averageLeadTimeIsBoundedWhenAWorkInProgressLimitIsUsedAndUniformStoriesArePlayedInTheOrderTheyArrive(String numberOfDays, String leadTimeUpperBound) {
		Simulation simulation = Simulation.simulation(Backlog.backlog(2), KanbanBoard.emptyBoard().withUniformWorkInProgressLimit(2), teamWithOneOfEachSpecialist());

		simulation.advanceDays(Integer.parseInt(numberOfDays));

		assertThat(simulation.averageLeadTime()).isLessThan(Double.valueOf(leadTimeUpperBound));
	}

	@Table({
		@Row({"1", "1"}),
		@Row({"10", "1"}),
		@Row({"100", "1"}),
		@Row({"1000", "1"}),
	})
	@Test
	public void throughputRemainsConstantWhenTheTeamWorksSlowerThanTheBacklogRate(String numberOfDays, String averageLeadTime) {
		Simulation simulation = Simulation.simulation(Backlog.backlog(2), KanbanBoard.emptyBoard(), teamWithOneOfEachSpecialist());

		simulation.advanceDays(Integer.parseInt(numberOfDays));

		assertThat(simulation.storiesCompletedPerDay()).isEqualTo(Double.valueOf(averageLeadTime));
	}

	@Table({
		@Row({"1"}),
		@Row({"10"}),
		@Row({"100"}),
		@Row({"1000"}),
	})
	@Test
	public void throughputIsDecreasedWhenWorkInProgressLimitsAreSetTooLow(String numberOfDays) {
		Simulation simulationWithTooSmallWorkInProgress = Simulation.simulation(Backlog.backlog(20), KanbanBoard.emptyBoard().withUniformWorkInProgressLimit(1), teamWithTwoOfEachSpecialist());
		Simulation simulationWithLargeEnoughWorkInProgress = Simulation.simulation(Backlog.backlog(20), KanbanBoard.emptyBoard().withUniformWorkInProgressLimit(2), teamWithTwoOfEachSpecialist());

		simulationWithTooSmallWorkInProgress.advanceDays(Integer.parseInt(numberOfDays));
		simulationWithLargeEnoughWorkInProgress.advanceDays(Integer.parseInt(numberOfDays));

		assertThat(simulationWithTooSmallWorkInProgress.storiesCompletedPerDay()).isLessThan(simulationWithLargeEnoughWorkInProgress.storiesCompletedPerDay());
	}
}
