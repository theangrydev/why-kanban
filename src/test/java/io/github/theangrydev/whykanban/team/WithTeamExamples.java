package io.github.theangrydev.whykanban.team;

public interface WithTeamExamples {

	default Team teamWithOneOfEachSpecialist() {
		return teamWithANumberOfEachSpecialist(1);
	}

	default Team teamWithTwoOfEachSpecialist() {
		return teamWithANumberOfEachSpecialist(2);
	}

	default Team teamWithANumberOfEachSpecialist(int number) {
		Team team = Team.team();
		for (int i = 0; i < number; i++) {
			team.addTeamMember(BusinessAnalyst.businessAnalyst());
			team.addTeamMember(Developer.developer());
			team.addTeamMember(Tester.tester());
		}
		return team;
	}
}
