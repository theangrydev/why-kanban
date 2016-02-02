package io.github.theangrydev.whykanban.board;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;

@SuppressFBWarnings(value = "SE_NO_SERIALVERSIONID", justification = "Will never be serialized")
public class WorkLane<StoryType> extends ArrayList<StoryType> {

	private int workInProgressLimit;

	public WorkLane() {
		this.workInProgressLimit = Integer.MAX_VALUE;
	}

	public void setWorkInProgressLimit(int workInProgressLimit) {
		this.workInProgressLimit = workInProgressLimit;
	}

	public boolean workInProgressLimitHasBeenReached() {
		return workInProgressLimit == size();
	}

	@Override
	public boolean add(StoryType storyType) {
		Preconditions.checkState(!workInProgressLimitHasBeenReached(), "Work in progress limit %s has been reached", workInProgressLimit);
		return super.add(storyType);
	}
}
