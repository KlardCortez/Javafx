package aclcbukidnon.com.javafxactivity.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {

    @FXML
    private Label displayLabel;

    private StringBuilder currentInput = new StringBuilder();
    private double lastResult = 0; // Store the last result for continuous calculation
    private boolean waitingForOperator = false; // To check if we are waiting for an operator

    @FXML
    public void initialize() {
        displayLabel.setText("0");
    }

    @FXML
    private void handleButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        // After pressing "=" continue calculation
        if (waitingForOperator && !buttonText.equals("=") && !buttonText.equals("CLEAR") && !buttonText.equals("BCKSPC")) {
            // Reset current input to start a new operation with the last result
            currentInput.setLength(0);
            currentInput.append(lastResult); // Use the last result as the new input
            waitingForOperator = false; // We are now ready for the next operator
        }

        switch (buttonText) {
            case "CLEAR":
                currentInput.setLength(0);
                displayLabel.setText("0");
                lastResult = 0; // Reset last result
                waitingForOperator = false; // Not waiting for an operator
                break;

            case "BCKSPC":
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                }
                displayLabel.setText(currentInput.length() == 0 ? "0" : currentInput.toString());
                break;

            case "=":
                try {
                    double result = evaluateExpression(currentInput.toString());
                    // Only use decimal if the operation is division ("/")
                    if (currentInput.toString().contains("/")) {
                        displayLabel.setText(String.valueOf(result)); // Show result with decimal if division
                    } else {
                        // Display result without decimal for whole numbers
                        displayLabel.setText(result == (int) result ? String.valueOf((int) result) : String.valueOf(result));
                    }
                    lastResult = result; // Store the last result
                    currentInput.setLength(0); // Clear input for new calculation
                    waitingForOperator = true; // Set flag to wait for the next operator
                } catch (Exception e) {
                    displayLabel.setText("Error");
                    waitingForOperator = false; // No more operations are allowed after error
                }
                break;

            default:
                currentInput.append(buttonText);
                displayLabel.setText(currentInput.toString());
                waitingForOperator = false; // After a number or operator is pressed, we're not waiting for "="
                break;
        }
    }

    private double evaluateExpression(String expression) throws Exception {
        if (expression.isEmpty()) return lastResult;

        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        double result = lastResult; // Start from the last result for continuous calculation

        if (tokens.length > 1) {
            // If expression has more than one token, calculate based on the input
            result = Double.parseDouble(tokens[0]);
        }

        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            double nextNumber = Double.parseDouble(tokens[i + 1]);

            switch (op) {
                case "+": result += nextNumber; break;
                case "-": result -= nextNumber; break;
                case "*": result *= nextNumber; break;
                case "/":
                    if (nextNumber == 0) throw new ArithmeticException("Divide by zero");
                    result /= nextNumber;
                    break;
                default: throw new Exception("Invalid operator: " + op);
            }
        }

        return result;
    }
}
