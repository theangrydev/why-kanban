package io.github.theangrydev.whykanban.gui;

import io.github.theangrydev.whykanban.board.KanbanBoard;
import io.github.theangrydev.whykanban.board.KanbanBoardState;
import io.github.theangrydev.whykanban.board.Story;
import io.github.theangrydev.whykanban.board.StoryInLane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.reactfx.util.FxTimer;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.nCopies;

public class KanbanBoardPane extends GridPane {

    private static final Duration UPDATE_INTERVAL = Duration.ofMillis(100);
    private static final int TOTAL_COLUMNS = 6;
    private static final int READY_COLUMN = 0;
    private static final int ANALYSIS_COLUMN = 1;
    private static final int DEVELOPMENT_COLUMN = 2;
    private static final int WAITING_FOR_TEST_COLUMN = 3;
    private static final int TESTING_COLUMN = 4;
    private static final int COMPLETED_COLUMN = 5;

    private Deque<KanbanBoardState> pendingSnapshots = new ArrayDeque<>();

    public static KanbanBoardPane kanbanBoardPane(KanbanBoard kanbanBoard) {
        KanbanBoardPane kanbanBoardPane = new KanbanBoardPane();
        kanbanBoardPane.getColumnConstraints().addAll(columnConstraints());
        kanbanBoard.boardChanges().subscribe(kanbanBoardPane::remember);
        FxTimer.runPeriodically(UPDATE_INTERVAL, kanbanBoardPane::pollSnapshot);
        kanbanBoardPane.update(kanbanBoard);
        return kanbanBoardPane;
    }

    public static List<ColumnConstraints> columnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100 / TOTAL_COLUMNS);
        return nCopies(TOTAL_COLUMNS, columnConstraints);
    }

    private void remember(KanbanBoardState current) {
        pendingSnapshots.addLast(current);
    }

    private void pollSnapshot() {
        if (!pendingSnapshots.isEmpty()) {
            update(pendingSnapshots.poll());
        }
    }

    private void update(KanbanBoardState current) {
        getChildren().clear();
        addHeaders();
        addStories(current);
        requestParentLayout();
    }

    private void addStories(KanbanBoardState current) {
        addStories(current, KanbanBoardState::storiesReadyToPlay, READY_COLUMN);
        addStories(current, KanbanBoardState::storiesInAnalysis, ANALYSIS_COLUMN);
        addStories(current, KanbanBoardState::storiesInDevelopment, DEVELOPMENT_COLUMN);
        addStories(current, KanbanBoardState::storiesWaitingForTest, WAITING_FOR_TEST_COLUMN);
        addStories(current, KanbanBoardState::storiesInTesting, TESTING_COLUMN);
        addStories(current, KanbanBoardState::storiesCompleted, COMPLETED_COLUMN);
    }

    private void addStories(KanbanBoardState current, Function<KanbanBoardState, List<? extends StoryInLane>> storiesOfType, int column) {
        FlowPane bucket = new FlowPane();
        bucket.setHgap(2);
        List<? extends StoryInLane> stories = storiesOfType.apply(current);
        for (StoryInLane storyInLane : stories) {
            Story story = storyInLane.story();
            bucket.getChildren().add(story(String.format("#%d", story.storyNumber())));
        }
        add(bucket, column, 1);
    }

    private void addHeaders() {
        addHeader("Ready", READY_COLUMN);
        addHeader("Analysis", ANALYSIS_COLUMN);
        addHeader("Development", DEVELOPMENT_COLUMN);
        addHeader("Waiting For Test", WAITING_FOR_TEST_COLUMN);
        addHeader("Testing", TESTING_COLUMN);
        addHeader("Completed", COMPLETED_COLUMN);
    }

    private void addHeader(String name, int readyColumn) {
        add(swimLaneHeader(name), readyColumn, 0);
    }

    private static Text swimLaneHeader(String name) {
        Text header = new Text(name);
        header.setFont(Font.font(null, FontWeight.BOLD, 15));
        return header;
    }

    private static Text story(String name) {
        Text header = new Text(name);
        header.setFont(Font.font(null, FontWeight.BOLD, 15));
        return header;
    }
}
