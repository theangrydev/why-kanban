package io.github.theangrydev.whykanban.team;

public interface WithTeamExamples {

	default Team teamWithOneOfEachSpecialist() {
		Team team = Team.team();
		team.addTeamMember(BusinessAnalyst.businessAnalyst());
		team.addTeamMember(Developer.developer());
		team.addTeamMember(Tester.tester());
		return team;
	}
}
