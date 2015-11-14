package io.github.theangrydev.whykanban.simulation;

import com.google.common.base.Preconditions;

public interface WithDayExamples {
	default Day anyDay() {
		return Day.firstDay();
	}

	default Day dayNumber(int dayNumber) {
		Preconditions.checkArgument(dayNumber >= 1, "Day number must be at least 1");
		Day day = Day.firstDay();
		for (int i = 1; i < dayNumber; i++) {
			day = day.nextDay();
		}
		return day;
	}
}
