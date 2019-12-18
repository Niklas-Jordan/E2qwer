package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class WikiBooksController {
    @FXML
    private AnchorPane anchorP;

    @FXML
    private VBox vBoxBottom, vBoxTop;

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

        btnSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSearch.setOnAction((event) -> {
                    WikiBooks books = new WikiBooks(tfSearch.getText());
                    browser.getEngine().load(books.getUrl());
                });
            }
        });
        btnSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                btnSearch.setOnAction((event) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        WikiBooks books = new WikiBooks(tfSearch.getText());
                        browser.getEngine().load(books.getUrl());
                    }
                });
            }
        });
    }
}


