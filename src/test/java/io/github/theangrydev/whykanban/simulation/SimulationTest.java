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
		@Row({"2", "1"}),
		@Row({"3", "1.67"}),
		@Row({"4", "2.5"}),
		@Row({"10", "7"}),
		@Row({"11", "7.73"}),
		@Row({"12", "8.5"}),
		@Row({"13", "9.23"}),
		@Row({"14", "10"}),
		@Row({"15", "10.73"}),
		@Row({"16", "11.5"}),
		@Row({"17", "12.24"}),
		@Row({"18", "13"}),
		@Row({"19", "13.74"})
	})
	@Test
	public void averageLeadTimeIncreasesWhenTheTeamWorksSlowerThanTheBacklogRate(String numberOfDays, String averageLeadTime) {
		Simulation simulation = Simulation.simulation(Backlog.backlog(2), KanbanBoard.emptyBoard(), teamWithOneOfEachSpecialist());

		simulation.advanceDays(Integer.valueOf(numberOfDays));

		assertThat(simulation.averageLeadTime()).isCloseTo(Double.valueOf(averageLeadTime), withPercentage(1));
	}

	@Table({
		@Row({"1", "1"}),
		@Row({"2", "1"}),
		@Row({"3", "1"}),
		@Row({"4", "1"}),
		@Row({"5", "1"}),
		@Row({"6", "1"}),
		@Row({"7", "1"}),
		@Row({"8", "1"}),
		@Row({"9", "1"}),
		@Row({"10", "1"}),
	})
	@Test
	public void throughputRemainsConstantWhenTheTeamWorksSlowerThanTheBacklogRate(String numberOfDays, String averageLeadTime) {
		Simulation simulation = Simulation.simulation(Backlog.backlog(2), KanbanBoard.emptyBoard(), teamWithOneOfEachSpecialist());

		simulation.advanceDays(Integer.valueOf(numberOfDays));

		assertThat(simulation.storiesCompletedPerDay()).isCloseTo(Double.valueOf(averageLeadTime), withPercentage(1));
	}
}
