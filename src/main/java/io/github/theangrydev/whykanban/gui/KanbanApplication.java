package io.github.theangrydev.whykanban.gui;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.simulation.Backlog;
import io.github.theangrydev.whykanban.simulation.Simulation;
import io.github.theangrydev.whykanban.team.Team;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.reactfx.EventSource;

import static io.github.theangrydev.whykanban.gui.KanbanBoardPane.kanbanBoardPane;
import static io.github.theangrydev.whykanban.gui.MouseEvents.leftClicks;
import static io.github.theangrydev.whykanban.gui.StatisticTicker.statisticWithLabel;
import static io.github.theangrydev.whykanban.team.BusinessAnalyst.businessAnalyst;
import static io.github.theangrydev.whykanban.team.Developer.developer;
import static io.github.theangrydev.whykanban.team.Tester.tester;

public class KanbanApplication extends Application {

    private final KanbanBoard kanbanBoard = KanbanBoard.emptyBoard();
    private final Simulation simulation = Simulation.simulation(Backlog.backlog(2), kanbanBoard, teamWithOneOfEachSpecialist());

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
        root.setBottom(advanceDayButton());

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, visualBounds.getWidth() / 2, visualBounds.getHeight() / 2);

        primaryStage.setTitle("Why Kanban?");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Column statisticsColumn() {
        StatisticTicker leadTimeTicker = statisticWithLabel("Average Lead Time", "%.2f days", averageLeadTimes);
        StatisticTicker storiesPerDayTicker = statisticWithLabel("Stories Completed Per Day", "%.2f stories", storiesCompletedPerDay);
        StatisticTicker totalStoriesCompletedTicker = statisticWithLabel("Stories Completed", "%d stories", totalStoriesCompleted);
        StatisticTicker totalDaysCompletedTicker = statisticWithLabel("Days Completed", "%d days", totalDaysCompleted);

        return Column.column("Statistics", leadTimeTicker, storiesPerDayTicker, totalStoriesCompletedTicker, totalDaysCompletedTicker);
    }

    private Button advanceDayButton() {
        Button button = new Button("Advance Day");
        leftClicks(button).subscribe(click -> advanceOneDay());
        return button;
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
