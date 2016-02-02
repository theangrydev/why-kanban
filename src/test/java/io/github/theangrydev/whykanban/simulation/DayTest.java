package io.github.theangrydev.whykanban.simulation;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class DayTest implements WithAssertions, WithDayExamples {

	@Test
	public void firstDayNumberIsOne() {
		assertThat(Day.firstDay().dayNumber()).isEqualTo(0);
	}

	@Test
	public void nextDayIncreasesDayNumberByOne() {
		Day day = anyDay();
		Day nextDay = day.nextDay();
		assertThat(nextDay.dayNumber() - day.dayNumber()).isEqualTo(1);
	}
}
