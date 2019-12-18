package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        btnSearch.setOnAction((event) -> {
            WikiBooks books = new WikiBooks(tfSearch.getText());
            browser.getEngine().load(books.getUrl());
        });
    }
}
