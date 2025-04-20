package aclcbukidnon.com.javafxactivity.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TrafficLightController {

    private enum TrafficLightColor {
        STOP, GO, HOLD
    }

    private TrafficLightColor currentColor = TrafficLightColor.STOP;
    private Timeline timeline;

    @FXML
    private Circle btnStop;

    @FXML
    private Circle btnGo;

    @FXML
    private Circle btnHold;

    @FXML
    public void initialize() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> onTimerChange())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        updateLight(); // Set initial state
    }

    public void startCycle() {
        timeline.play();
    }

    public void stopCycle() {
        timeline.stop();
    }

    public void onTimerChange() {
        switch (currentColor) {
            case STOP -> currentColor = TrafficLightColor.GO;
            case GO -> currentColor = TrafficLightColor.HOLD;
            case HOLD -> currentColor = TrafficLightColor.STOP;
        }
        updateLight();
    }

    private void updateLight() {
        // Reset all to gray (off)
        btnStop.setFill(Color.web("#575757"));
        btnGo.setFill(Color.web("#575757"));
        btnHold.setFill(Color.web("#575757"));

        // Turn on the current light
        switch (currentColor) {
            case STOP -> btnStop.setFill(Color.RED);
            case GO -> btnGo.setFill(Color.GREEN);
            case HOLD -> btnHold.setFill(Color.YELLOW);
        }
    }

    @FXML
    protected void onStartClick() {
        startCycle();
    }

    @FXML
    protected void onStopClick() {
        stopCycle();
        // Turn all lights off (gray)
        btnStop.setFill(Color.web("#575757"));
        btnGo.setFill(Color.web("#575757"));
        btnHold.setFill(Color.web("#575757"));
    }

}
