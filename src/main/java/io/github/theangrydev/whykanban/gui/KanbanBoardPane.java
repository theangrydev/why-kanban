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

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import static java.util.Collections.nCopies;
import static org.reactfx.util.FxTimer.runPeriodically;

public class KanbanBoardPane extends GridPane {

    private static final Duration UPDATE_INTERVAL = Duration.ofMillis(50);
    private static final int TOTAL_COLUMNS = 6;
    private static final int READY_COLUMN = 0;
    private static final int ANALYSIS_COLUMN = 1;
    private static final int DEVELOPMENT_COLUMN = 2;
    private static final int WAITING_FOR_TEST_COLUMN = 3;
    private static final int TESTING_COLUMN = 4;
    private static final int COMPLETED_COLUMN = 5;
    private static final int STORIES_ROW = 1;
    private static final int HEADER_ROW = 0;

    private Deque<KanbanBoardState> pendingSnapshots = new ArrayDeque<>();
    private FlowPane readyColumn;
    private FlowPane analysisColumn;
    private FlowPane developmentColumn;
    private FlowPane waitingForTestColumn;
    private FlowPane testingColumn;
    private FlowPane completedColumn;

    private KanbanBoardPane(KanbanBoard kanbanBoard) {
        getColumnConstraints().addAll(columnConstraints());
        addHeaders();
        addBuckets();

        kanbanBoard.boardChanges().filter(this::notSameAsLast).subscribe(this::remember);
        runPeriodically(UPDATE_INTERVAL, this::pollSnapshot);

        update(kanbanBoard);
    }

    private void addBuckets() {
        readyColumn = addBucket(READY_COLUMN);
        analysisColumn = addBucket(ANALYSIS_COLUMN);
        developmentColumn = addBucket(DEVELOPMENT_COLUMN);
        waitingForTestColumn = addBucket(WAITING_FOR_TEST_COLUMN);
        testingColumn = addBucket(TESTING_COLUMN);
        completedColumn = addBucket(COMPLETED_COLUMN);
    }

    private boolean notSameAsLast(KanbanBoardState kanbanBoardState) {
        return pendingSnapshots.isEmpty() || !pendingSnapshots.peek().equals(kanbanBoardState);
    }

    public static KanbanBoardPane kanbanBoardPane(KanbanBoard kanbanBoard) {
        return new KanbanBoardPane(kanbanBoard);
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
        addStories(current);
        requestParentLayout();
    }

    private void addStories(KanbanBoardState current) {
        addStories(current, KanbanBoardState::storiesReadyToPlay, readyColumn);
        addStories(current, KanbanBoardState::storiesInAnalysis, analysisColumn);
        addStories(current, KanbanBoardState::storiesInDevelopment, developmentColumn);
        addStories(current, KanbanBoardState::storiesWaitingForTest, waitingForTestColumn);
        addStories(current, KanbanBoardState::storiesInTesting, testingColumn);
        addStories(current, KanbanBoardState::storiesCompleted, completedColumn);
    }

    private void addStories(KanbanBoardState current, Function<KanbanBoardState, List<? extends StoryInLane>> storiesOfType, FlowPane column) {
        column.getChildren().clear();
        List<? extends StoryInLane> stories = storiesOfType.apply(current);
        for (StoryInLane storyInLane : stories) {
            Story story = storyInLane.story();
            column.getChildren().add(story(String.format("#%d", story.storyNumber())));
        }
    }

    private FlowPane addBucket(int column) {
        FlowPane bucket = new FlowPane();
        bucket.setHgap(2);
        add(bucket, column, STORIES_ROW);
        return bucket;
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
        add(swimLaneHeader(name), readyColumn, HEADER_ROW);
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
