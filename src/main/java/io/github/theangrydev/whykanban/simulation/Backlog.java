package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.Story;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Backlog {

	private int storyNumber;
	private int replenishmentRate;

	private Backlog(int replenishmentRate) {
		this.replenishmentRate = replenishmentRate;
		resetStoryNumber();
	}

	public static Backlog backlog(int replenishmentRate) {
		return new Backlog(replenishmentRate);
	}

	public void withReplenishmentRate(int replenishmentRate) {
		this.replenishmentRate = replenishmentRate;
	}

	public void resetStoryNumber() {
		storyNumber = 1;
	}

	public List<Story> stories() {
		return IntStream.range(0, replenishmentRate).mapToObj(operand -> Story.aStory(storyNumber++)).collect(Collectors.toList());
	}
}
