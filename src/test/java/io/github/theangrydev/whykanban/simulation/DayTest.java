package io.github.theangrydev.whykanban.simulation;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class DayTest implements WithAssertions {

	@Test
	public void firstDayNumberIsOne() {
		assertThat(Day.firstDay().dayNumber()).isEqualTo(1);
	}

	@Test
	public void nextDayIncreasesDayNumberByOne() {
		Day day = Day.firstDay();
		Day nextDay = day.nextDay();
		assertThat(nextDay.dayNumber() - day.dayNumber()).isEqualTo(1);
	}
}
