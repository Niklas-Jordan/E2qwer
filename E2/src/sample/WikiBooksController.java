package sample;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * The type Wiki books controller.
 */

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
    private Button btnSearch, btnAdd, btnSort, btnDelete, btnSave, btnLoad;

    @FXML
    private WebView browser;

    @FXML
    private Label labelAuthor, labelZeit;

    @FXML
    private void initialize() {
        browser.getEngine().load("https://de.wikibooks.org/wiki/ ");

        btnSearch.setOnAction((event) -> {
            search();
        });

        tfSearch.setOnMouseClicked((event -> {
            tfSearch.selectAll();
        }));

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

        btnAdd.setOnAction((event -> {
            add();
        }));
        btnSort.setOnAction((event -> {
            sort();
        }));
        btnDelete.setOnAction((event -> {
            delete();
            btnDelete.setDisable(true);
        }));
        btnSave.setOnAction((event -> {
            safe();
        }));
        btnLoad.setOnAction((event -> {
            load();
        }));
    }

    private String urlName;
    private WikiBooks wikiBooks = null;
    private ArrayList<Medium> data = new ArrayList<>();
    private Zettelkasten zettelkasten = new Zettelkasten();
    private Medium selectedItemBuch = null;
    private String direction = "up";

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

    private void add() {
        try {
            if (!data.contains(wikiBooks)) {
                zettelkasten.addMedium(wikiBooks);
                data.add(wikiBooks);
                System.out.println(data);
            } else {
                System.out.println("ERROR");
            }
        } catch (Exception e) {
            errorWikiBooks();
        }
    }

    private void sort() {
        zettelkasten.sort(direction);
        if (direction.equals("down")) {
            direction = "up";
        } else {
            direction = "down";
        }
    }

    private void delete() {
        try {
            zettelkasten.dropMedium("q", selectedItemBuch.getTitel());
        } catch (Exception e) {
            errorWikiBooks();
        }
    }

    private void safe() {
        try {

        } catch (Exception e) {
            System.out.println("Fehler beim speichern!");
        }
    }

    private void load() {

    }

    private void errorWikiBooks() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("WikiBooks ist momentan nicht erreichbar");
        alert.showAndWait();
    }
}