package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class BusinessAnalystTest implements WithAssertions, WithKanbanBoardExamples {

	@Test
	public void worksOnStoriesInReadyToPlayAndInAnalysis() {
		KanbanBoard kanbanBoard = boardWithOneReadyToPlayStory();
		BusinessAnalyst businessAnalyst = BusinessAnalyst.businessAnalyst();

		businessAnalyst.doWork(kanbanBoard);

		assertThat(kanbanBoard.storiesReadyToPlay()).isEmpty();
		assertThat(kanbanBoard.storiesInAnalysis()).isEmpty();
		assertThat(kanbanBoard.storiesInDevelopment()).hasSize(1);
	}
}
