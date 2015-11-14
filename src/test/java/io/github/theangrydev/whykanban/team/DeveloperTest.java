package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.WithKanbanBoardExamples;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class DeveloperTest implements WithAssertions, WithKanbanBoardExamples {

	@Test
	public void worksOnStoriesInDevelopment() {
		KanbanBoard kanbanBoard = boardWithOneInDevelopmentStory();
		Developer developer = Developer.developer();

		developer.doWork(kanbanBoard);

		assertThat(kanbanBoard.storiesInDevelopment()).isEmpty();
		assertThat(kanbanBoard.storiesWaitingForTest()).hasSize(1);
	}
}
