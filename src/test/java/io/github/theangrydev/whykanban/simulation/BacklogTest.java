package io.github.theangrydev.whykanban.simulation;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TableRunner.class)
public class BacklogTest implements WithAssertions {

	@Table({
		@Row({"1", "1"}),
		@Row({"2", "2"}),
		@Row({"3", "3"})
	})
	@Test
	public void backlog(String replenishmentRate, String numberOfStories) {
		Backlog backlog = Backlog.backlog(Integer.valueOf(replenishmentRate));

		assertThat(backlog.stories()).hasSize(Integer.valueOf(numberOfStories));
	}
}
