package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class TesterTest implements WithAssertions, WithKanbanBoardExamples {

	@Test
	public void worksOnStoriesInWaitingForTestAndInTesting() {
		KanbanBoard kanbanBoard = boardWithOneWaitingForTestStory();
		Tester tester = Tester.tester();

		tester.doWork(kanbanBoard);

		assertThat(kanbanBoard.storiesWaitingForTest()).isEmpty();
		assertThat(kanbanBoard.storiesInTesting()).isEmpty();
		assertThat(kanbanBoard.storiesCompleted()).hasSize(1);
	}
}
