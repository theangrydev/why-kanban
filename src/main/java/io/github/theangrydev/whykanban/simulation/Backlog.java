package io.github.theangrydev.whykanban.simulation;

import io.github.theangrydev.whykanban.board.Story;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Backlog {

	private int replenishmentRate;

	private Backlog(int replenishmentRate) {
		this.replenishmentRate = replenishmentRate;
	}

	public static Backlog backlog(int replenishmentRate) {
		return new Backlog(replenishmentRate);
	}

	public List<Story> stories() {
		return IntStream.range(0, replenishmentRate).mapToObj(operand -> Story.aStory()).collect(Collectors.toList());
	}
}
