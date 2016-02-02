package io.github.theangrydev.whykanban.board;

import org.junit.Test;

import static io.github.theangrydev.whykanban.board.InDevelopmentStory.inDevelopmentStory;
import static io.github.theangrydev.whykanban.board.Story.aStory;

public class WorkLaneTest {

	@Test(expected = IllegalStateException.class)
	public void workInProgressLimitIsRespectedFromTheOutset() {
		WorkLane<InDevelopmentStory> workLane = new WorkLane<>();
		workLane.setWorkInProgressLimit(1);

		workLane.add(inDevelopmentStory(aStory()));
		workLane.add(inDevelopmentStory(aStory()));
	}

	@Test(expected = IllegalStateException.class)
	public void workInProgressLimitIsRespectedIfItIsAdjustedAfterItHasBeenBroken() {
		WorkLane<InDevelopmentStory> workLane = new WorkLane<>();
		workLane.add(inDevelopmentStory(aStory()));
		workLane.add(inDevelopmentStory(aStory()));
		workLane.setWorkInProgressLimit(1);

		workLane.add(inDevelopmentStory(aStory()));
	}
}
