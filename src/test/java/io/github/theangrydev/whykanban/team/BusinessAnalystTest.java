package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class BusinessAnalystTest implements WithAssertions, WithKanbanBoardExamples {

	@Test
	public void worksOnStoriesInAnalysis() {
		KanbanBoard kanbanBoard = boardWithOneInAnalysisStory();
		BusinessAnalyst businessAnalyst = BusinessAnalyst.businessAnalyst();

		businessAnalyst.doWork(kanbanBoard);

		assertThat(kanbanBoard.storiesInAnalysis()).isEmpty();
		assertThat(kanbanBoard.storiesInDevelopment()).hasSize(1);
	}
}
