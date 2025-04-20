package aclcbukidnon.com.javafxactivity.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TodoController {

    @FXML
    private ListView<String> todoList;

    private ObservableList<String> items;

    @FXML
    public void initialize() {
        items = FXCollections.observableArrayList("Remove Me");
        todoList.setItems(items);
        todoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        todoList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        onListEdit();
                    }
                }
        );
    }

    private void onTodoListItemClick(String value) {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle("Update Todo");
        dialog.setHeaderText("Edit your todo item:");
        dialog.setContentText("Todo:");

        var result = dialog.showAndWait();
        result.ifPresent(text -> {
            int selectedIndex = todoList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                items.set(selectedIndex, text);
            }
        });
    }

    @FXML
    protected void onCreateClick() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create New Todo");
        dialog.setHeaderText("Add a new todo item:");
        dialog.setContentText("Todo:");

        var result = dialog.showAndWait();
        result.ifPresent(text -> items.add(text));
    }

    @FXML
    protected void onDeleteClick() {
        int selectedIndex = todoList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation Dialog");
            confirm.setHeaderText("Are you sure you want to delete this todo?");
            confirm.setContentText("This action cannot be undone.");

            var result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                items.remove(selectedIndex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Todo Selected");
            alert.setContentText("Please select a todo item to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onListEdit() {
        String selectedItem = todoList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            onTodoListItemClick(selectedItem);
        }
    }
}
