package LPS_Niklas_Jordan_SMIB;

import javafx.application.Application;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox box = new VBox();
            Scene scene = new Scene(box, 400, 400);

            TextField textField = new TextField();
            textField.setPromptText("Schreibe hier");

            //TODO: Aufgabe 1.3 nochmal überarbeiten/neu machen
            ListView<String> listView = listView = new ListView<>();
            listView.getItems().addAll("Laborpraktikum", "Software");
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            Button addButton = new Button("Add");
            addButton.setOnAction((ActionEvent e) ->) {
                listView.addEventHandler();
            }
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> deleteButtonClicked());



            box.getChildren().addAll(textField, addButton, deleteButton, listView);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void addButtonClicked(){

    }
}
