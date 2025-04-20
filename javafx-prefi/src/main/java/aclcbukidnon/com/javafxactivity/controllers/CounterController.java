package aclcbukidnon.com.javafxactivity.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterController {

    @FXML
    private Label labelCount;

    private int count = 0; // Initialize counter

    @FXML
    protected void onPlusClick() {
        count++;
        updateLabel();
    }

    @FXML
    protected void onMinusClick() {
        count--;
        updateLabel();
    }

    private void updateLabel() {
        labelCount.setText(String.valueOf(count));
    }
}
