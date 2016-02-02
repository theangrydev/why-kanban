package io.github.theangrydev.whykanban.board;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import io.github.theangrydev.whykanban.simulation.Day;

public class Story {

	private int storyNumber;
	private Optional<Day> dayReady = Optional.absent();
	private Optional<Day> dayComplete = Optional.absent();

	private Story(int storyNumber) {
		this.storyNumber = storyNumber;
	}

	public static Story aStory() {
		return aStory(0);
	}

	public static Story aStory(int storyNumber) {
		return new Story(storyNumber);
	}

	public void markReady(Day dayReady)	{
		this.dayReady = Optional.of(dayReady);
	}

	public void markComplete(Day dayComplete) {
		this.dayComplete = Optional.of(dayComplete);
	}

	public int leadTime() {
		Preconditions.checkState(dayReady.isPresent(), "Has not been marked ready");
		Preconditions.checkState(dayComplete.isPresent(), "Has not been marked complete");
		return dayComplete.get().dayNumber() - dayReady.get().dayNumber();
	}

	public int storyNumber() {
		return storyNumber;
	}
}
