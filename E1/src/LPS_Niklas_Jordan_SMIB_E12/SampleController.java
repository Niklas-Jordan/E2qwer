package LPS_Niklas_Jordan_SMIB_E12;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

/**
 * The type Sample controller.
 */
public class SampleController {
    /**
     * The Hello world.
     */
    public Label helloWorld;

    /**
     * Say hello world.
     *
     * @param actionEvent the action event
     */
    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    }
}
