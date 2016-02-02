package io.github.theangrydev.whykanban.gui;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.simulation.Backlog;
import io.github.theangrydev.whykanban.simulation.Simulation;
import io.github.theangrydev.whykanban.team.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.reactfx.EventSource;

import java.util.function.Supplier;

import static io.github.theangrydev.whykanban.gui.Column.column;
import static io.github.theangrydev.whykanban.gui.KanbanBoardPane.kanbanBoardPane;
import static io.github.theangrydev.whykanban.gui.MouseEvents.leftClicks;
import static io.github.theangrydev.whykanban.gui.StatisticTicker.statisticWithLabel;
import static io.github.theangrydev.whykanban.gui.SettingSpinner.settingSpinner;
import static io.github.theangrydev.whykanban.team.BusinessAnalyst.businessAnalyst;
import static io.github.theangrydev.whykanban.team.Developer.developer;
import static io.github.theangrydev.whykanban.team.Tester.tester;

public class KanbanApplication extends Application {

    private static final int DEFAULT_REPLENISHMENT_RATE = 1;
    private static final int DEFAULT_WIP_LIMIT = 99;
    private static final int DEFAULT_TEAM_SIZE = 1;

    private KanbanBoard kanbanBoard = KanbanBoard.emptyBoard().withWorkInProgressLimit(2);
    private Team team = teamWithOneOfEachSpecialist();
    private Backlog backlog = Backlog.backlog(1);
    private Simulation simulation = Simulation.simulation(backlog, kanbanBoard, team);
    private StatisticTicker leadTimeTicker;
    private StatisticTicker storiesPerDayTicker;
    private StatisticTicker totalStoriesCompletedTicker;
    private StatisticTicker totalDaysCompletedTicker;

    private Team teamWithOneOfEachSpecialist() {
        Team team = Team.team();
        team.addTeamMember(businessAnalyst());
        team.addTeamMember(developer());
        team.addTeamMember(tester());
        return team;
    }

    private final EventSource<Double> averageLeadTimes = new EventSource<>();
    private final EventSource<Double> storiesCompletedPerDay = new EventSource<>();
    private final EventSource<Integer> totalStoriesCompleted = new EventSource<>();
    private final EventSource<Integer> totalDaysCompleted = new EventSource<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setRight(statisticsColumn());
        root.setCenter(kanbanBoardPane(kanbanBoard));
        root.setBottom(controls());

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);

        primaryStage.setTitle("Why Kanban?");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public FlowPane controls() {
        FlowPane controls = new FlowPane();
        controls.setHgap(5);

        SettingSpinner replenishmentRate = settingSpinner("Replenishment Rate", DEFAULT_REPLENISHMENT_RATE);
        replenishmentRate.setting().subscribe(this::modifyReplenishmentRate);

        SettingSpinner workInProgressLimit = settingSpinner("WIP Limit", DEFAULT_WIP_LIMIT);
        workInProgressLimit.setting().subscribe(this::modifyWorkInProgressLimit);

        SettingSpinner analysts = settingSpinner("Analysts", DEFAULT_TEAM_SIZE);
        analysts.setting().subscribe(this::modifyAnalystCount);

        SettingSpinner developers = settingSpinner("Developers", DEFAULT_TEAM_SIZE);
        developers.setting().subscribe(this::modifyDeveloperCount);

        SettingSpinner testers = settingSpinner("Testers", DEFAULT_TEAM_SIZE);
        testers.setting().subscribe(this::modifyTesterCount);

        controls.getChildren().addAll(advanceDayButton(), replenishmentRate, workInProgressLimit, analysts, developers, testers);

        return controls;
    }

    private void modifyReplenishmentRate(int replenishmentRate) {
        backlog.withReplenishmentRate(replenishmentRate);
    }

    private void modifyWorkInProgressLimit(int workInProgressLimit) {
        kanbanBoard.withWorkInProgressLimit(workInProgressLimit);
    }

    private void modifyAnalystCount(int analystCount) {
        modifyTeamMemberCount(analystCount, BusinessAnalyst.class, BusinessAnalyst::businessAnalyst);
    }

    private void modifyDeveloperCount(int developerCount) {
        modifyTeamMemberCount(developerCount, Developer.class, Developer::developer);
    }

    private void modifyTesterCount(int testerCount) {
        modifyTeamMemberCount(testerCount, Tester.class, Tester::tester);
    }

    private <T extends TeamMember> void modifyTeamMemberCount(int memberCount, Class<T> type, Supplier<T> memberFactory) {
        team.removeTeamMembersOfType(type);
        for (int i = 0; i < memberCount; i++) {
            team.addTeamMember(memberFactory.get());
        }
    }

    private Column statisticsColumn() {
        leadTimeTicker = statisticWithLabel("Average Lead Time", "%.2f days", averageLeadTimes);
        storiesPerDayTicker = statisticWithLabel("Stories Completed Per Day", "%.2f stories", storiesCompletedPerDay);
        totalStoriesCompletedTicker = statisticWithLabel("Stories Completed", "%d stories", totalStoriesCompleted);
        totalDaysCompletedTicker = statisticWithLabel("Days Completed", "%d days", totalDaysCompleted);

        return column("Statistics", leadTimeTicker, storiesPerDayTicker, totalStoriesCompletedTicker, totalDaysCompletedTicker, resetStatisticsButton());
    }

    private Button advanceDayButton() {
        Button button = new Button("Advance Day");
        leftClicks(button).subscribe(click -> advanceOneDay());
        return button;
    }

    private Button resetStatisticsButton() {
        Button button = new Button("Reset Statistics");
        leftClicks(button).subscribe(click -> resetStatistics());
        return button;
    }

    private void resetStatistics() {
        backlog.resetStoryNumber();
        simulation = Simulation.simulation(backlog, kanbanBoard, team);
        leadTimeTicker.clear();
        storiesPerDayTicker.clear();
        totalStoriesCompletedTicker.clear();
        totalDaysCompletedTicker.clear();
    }

    private void advanceOneDay() {
        simulation.advanceOneDay();
        averageLeadTimes.push(simulation.averageLeadTime());
        storiesCompletedPerDay.push(simulation.storiesCompletedPerDay());
        totalStoriesCompleted.push(simulation.totalStoriesCompleted());
        totalDaysCompleted.push(simulation.currentDayNumber());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
