package io.github.theangrydev.whykanban.board;

import io.github.theangrydev.whykanban.simulation.Day;

public class Story {

	private Day dayReady;
	private Day dayComplete;

	public static Story aStory() {
		return new Story();
	}

	public void markReady(Day dayReady)	{
		this.dayReady  = dayReady;
	}

	public void markComplete(Day dayComplete) {
		this.dayComplete = dayComplete;
	}

	public int leadTime() {
		return dayComplete.dayNumber() - dayReady.dayNumber();
	}
}
