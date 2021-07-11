package automata;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

/**
 * A JavaFX controller for the Conway's Game of Live Application.
 *
 * @author Sam Wu
 *
 */
public class GameOfLifeController {

    @FXML
    private Button buttonTick;

    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonReset;

    @FXML
    private GridPane containerGame;

    private GameOfLife model;
    private Timeline timeline;
    private Boolean isPlaying;

    public GameOfLifeController() {
        model = new GameOfLife();
        timeline = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            model.tick();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        isPlaying = false;
    }

    @FXML
    void handleTickBtn(MouseEvent event) {
        buttonTick();
    }

    @FXML
    void handlePlayBtn(MouseEvent event) {
        if (isPlaying) 
            gameStop();
        else
            gamePlay();
    }

    @FXML
    void handleResetBtn(MouseEvent event) {
        reset();
    }

    @FXML
    private void buttonTick() {
        model.tick();
    }

    @FXML
    private void gamePlay() {
        timeline.play();
        isPlaying = true;
        buttonPlay.setText("Pause");
    }

    @FXML
    private void gameStop() {
        timeline.stop();
        isPlaying = false;
        buttonPlay.setText("Play");
    }

    @FXML
    private void reset() {
        gameStop();
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                model.ensureDead(j, i);
            }
        }
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                CheckBox c = new CheckBox();
                c.selectedProperty().bindBidirectional(model.cellProperty(j, i));
                containerGame.add(c, i, j);
            }
        }
    }
}

