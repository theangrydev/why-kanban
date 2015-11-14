package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class TeamTest implements WithAssertions, WithKanbanBoardExamples, WithTeamExamples {

	@Test
	public void teamCanWorkOnAStoryFromReadyToPlayToCompleted() {
		KanbanBoard kanbanBoard = boardWithOneReadyToPlayStory();
		Team team = teamOfSpecialists();

		team.doWork(kanbanBoard);

		assertThat(kanbanBoard.storiesReadyToPlay()).isEmpty();
		assertThat(kanbanBoard.storiesInAnalysis()).isEmpty();
		assertThat(kanbanBoard.storiesInDevelopment()).isEmpty();
		assertThat(kanbanBoard.storiesWaitingForTest()).isEmpty();
		assertThat(kanbanBoard.storiesInTesting()).isEmpty();
		assertThat(kanbanBoard.storiesCompleted()).hasSize(1);
	}
}
