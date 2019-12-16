package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField eingabefeld;

    @FXML
    private ListView list;

    @FXML
    private Button addButton, deleteButton;

    @FXML
    private void initialize() {
        addButton.setOnAction((event) -> {
            if (!(eingabefeld.getText().equals("") || eingabefeld.getText().equals(null)))
                list.getItems().add(eingabefeld.getText());
            eingabefeld.clear();
        });
        deleteButton.setOnAction((event) -> {
            list.getItems().clear();
        });
    }
}


