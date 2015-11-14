package io.github.theangrydev.whykanban.simulation;

public class Day {

	private final int dayNumber;

	private Day(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public static Day firstDay() {
		return new Day(1);
	}

	public int dayNumber() {
		return dayNumber;
	}

	public Day nextDay() {
		return new Day(dayNumber + 1);
	}
}
