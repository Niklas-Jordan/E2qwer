package sample;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class WikiBooksController {
    @FXML
    private AnchorPane anchorP;

    @FXML
    private VBox vBoxBottom;

    @FXML
    private GridPane gridP;

    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private WebView browser;

    @FXML
    private void initialize() {
        browser.getEngine().load("https://de.wikibooks.org/wiki/ ");

        btnSearch.setOnAction((event) -> {
            search();
        });

        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        browser.getEngine().getLoadWorker().stateProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                if (browser.getEngine().getLocation().contains("https://de.wikibooks.org/wiki/ ")) {
                    urlName = browser.getEngine().getLocation();
                    urlName = urlName.replace("https://de.wikibooks.org/wiki/ ", "");
                    tfSearch.setText(urlName);
                    search();
                    tfSearch.clear();
                } else {
                    urlName = browser.getEngine().getLocation();
                    urlName = urlName.replace("https://de.wikibooks.org/w/index.php?search=", "");
                    if (urlName.contains("&title")) {
                        urlName = urlName.substring(0, urlName.indexOf("&title"));
                    }
                    tfSearch.setText(urlName);
                    search();
                    tfSearch.clear();
                }
            }
        }));
    }

    private String urlName;

    private void search() {
        try {
            String search = tfSearch.getText().trim().replace(" ", "_");

            if (!urlName.equals(search)) {
                browser.getEngine().load("https://de.wikibooks.org/wiki/" + search);
            }
        } catch (NullPointerException e) {
            System.out.println("Fehler beim Suchen!");
        }
    }
}


