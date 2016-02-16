package io.github.theangrydev.whykanban.gui;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.simulation.Backlog;
import io.github.theangrydev.whykanban.simulation.FlowHistory;
import io.github.theangrydev.whykanban.simulation.Simulation;
import io.github.theangrydev.whykanban.team.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.reactfx.EventSource;

import java.util.Collections;
import java.util.function.Supplier;

import static io.github.theangrydev.whykanban.gui.Column.column;
import static io.github.theangrydev.whykanban.gui.PercentageConstraints.percentageColumnConstraints;
import static io.github.theangrydev.whykanban.gui.KanbanBoardPane.kanbanBoardPane;
import static io.github.theangrydev.whykanban.gui.MouseEvents.leftClicks;
import static io.github.theangrydev.whykanban.gui.StatisticTicker.statisticWithLabel;
import static io.github.theangrydev.whykanban.gui.SettingSpinner.settingSpinner;
import static io.github.theangrydev.whykanban.simulation.FlowHistory.flowHistory;
import static io.github.theangrydev.whykanban.team.BusinessAnalyst.businessAnalyst;
import static io.github.theangrydev.whykanban.team.Developer.developer;
import static io.github.theangrydev.whykanban.team.Tester.tester;
import static java.util.Collections.emptyList;

public class KanbanApplication extends Application {

    private static final int DEFAULT_REPLENISHMENT_RATE = 1;
    private static final int DEFAULT_TEAM_SIZE = 1;

    private KanbanBoard kanbanBoard = KanbanBoard.emptyBoard().withUniformWorkInProgressLimit(2);
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

    private final EventSource<FlowHistory> flowHistories = new EventSource<>();
    private final EventSource<Double> averageLeadTimes = new EventSource<>();
    private final EventSource<Double> storiesCompletedPerDay = new EventSource<>();
    private final EventSource<Integer> totalStoriesCompleted = new EventSource<>();
    private final EventSource<Integer> totalDaysCompleted = new EventSource<>();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setMinSize(0, 0);
        root.setPadding(new Insets(10));
        root.setRight(statisticsColumn());
        KanbanBoardPane kanbanBoardPane = kanbanBoardPane(kanbanBoard, flowHistories);
        kanbanBoardPane.readyWorkInProgressLimit().subscribe(kanbanBoard::withReadyToPlayWorkInProgressLimit);
        kanbanBoardPane.analysisWorkInProgressLimit().subscribe(kanbanBoard::withAnalysisWorkInProgressLimit);
        kanbanBoardPane.developmentWorkInProgressLimit().subscribe(kanbanBoard::withDevelopmentWorkInProgressLimit);
        kanbanBoardPane.waitingForTestWorkInProgressLimit().subscribe(kanbanBoard::withWaitingForTestWorkInProgressLimit);
        kanbanBoardPane.testingWorkInProgressLimit().subscribe(kanbanBoard::withTestingWorkInProgressLimit);

        root.setCenter(kanbanBoardPane);
        root.setBottom(controls());

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);
        scene.getRoot().styleProperty().bind(Bindings.concat("-fx-font-size: ", scene.widthProperty().divide(65)));

        primaryStage.setTitle("Why Kanban?");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Node controls() {
        GridPane controls = new GridPane();
        controls.setMinWidth(0);
        controls.setHgap(10);
        controls.getColumnConstraints().addAll(percentageColumnConstraints(5));

        controls.add(advanceDayButton(), 0, 0);

        SettingSpinner replenishmentRate = settingSpinner("Replenishment Rate", DEFAULT_REPLENISHMENT_RATE);
        replenishmentRate.setting().subscribe(backlog::withReplenishmentRate);
        controls.add(replenishmentRate, 1, 0);

        SettingSpinner analysts = settingSpinner("Analysts", DEFAULT_TEAM_SIZE);
        analysts.setting().subscribe(this::modifyAnalystCount);
        controls.add(analysts, 2, 0);

        SettingSpinner developers = settingSpinner("Developers", DEFAULT_TEAM_SIZE);
        developers.setting().subscribe(this::modifyDeveloperCount);
        controls.add(developers, 3, 0);

        SettingSpinner testers = settingSpinner("Testers", DEFAULT_TEAM_SIZE);
        testers.setting().subscribe(this::modifyTesterCount);
        controls.add(testers, 4, 0);

        return controls;
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

        return column("Statistics", leadTimeTicker, storiesPerDayTicker, totalStoriesCompletedTicker, totalDaysCompletedTicker, resetButton());
    }

    private Button advanceDayButton() {
        Button button = new Button("Advance Day");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        leftClicks(button).subscribe(click -> advanceOneDay());
        return button;
    }

    private Button resetButton() {
        Button button = new Button("Reset");
        leftClicks(button).subscribe(click -> reset());
        return button;
    }

    private void reset() {
        backlog.resetStoryNumber();
        kanbanBoard.clear();
        simulation = Simulation.simulation(backlog, kanbanBoard, team);
        leadTimeTicker.clear();
        storiesPerDayTicker.clear();
        totalStoriesCompletedTicker.clear();
        totalDaysCompletedTicker.clear();
        flowHistories.push(flowHistory(emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));
    }

    private void advanceOneDay() {
        simulation.advanceOneDay();
        flowHistories.push(simulation.flowHistory());
        averageLeadTimes.push(simulation.averageLeadTime());
        storiesCompletedPerDay.push(simulation.storiesCompletedPerDay());
        totalStoriesCompleted.push(simulation.totalStoriesCompleted());
        totalDaysCompleted.push(simulation.currentDayNumber());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
