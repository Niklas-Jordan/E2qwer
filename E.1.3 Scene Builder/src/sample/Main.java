package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//TODO:
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GridPane root = new GridPane();
            Scene scene = new Scene(root, 400, 400);
            final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        KeyCode.ENTER.
                    }
                    if (keyEvent.getCode() == KeyCode.BACK_SPACE) {

                    }
                    keyEvent.consume();
                }
            };
            root.getChildren().addAll();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}