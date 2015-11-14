package io.github.theangrydev.whykanban.team;

import io.github.theangrydev.whykanban.board.KanbanBoard;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private List<TeamMember> teamMembers;

	private Team() {
		teamMembers = new ArrayList<>();
	}

	public static Team team() {
		return new Team();
	}

	public void addTeamMember(TeamMember teamMember) {
		teamMembers.add(teamMember);
	}

	public void doWork(KanbanBoard kanbanBoard) {
		teamMembers.forEach(teamMember -> teamMember.doWork(kanbanBoard));
	}
}
