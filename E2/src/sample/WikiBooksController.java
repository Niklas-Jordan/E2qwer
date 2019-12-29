package sample;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * The type Wiki books controller.
 */

public class WikiBooksController {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private VBox vboxBackground, vboxSynonym, vboxMedienBg, vboxRightBg;

    @FXML
    private HBox hboxBackground, hboxLeftBg, hboxRightBg, hboxBrowser;

    @FXML
    private GridPane gridSynomeTopBtn, gridSynonymLabel, gridSynonymList, gridMedienBtn, gridMedienLabel, gridMedienList, gridSearchBrowser, gridBrowserLabel;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuBtn;

    @FXML
    private MenuItem menuItem;

    @FXML
    private TextField tfSearch;

    @FXML
    private Separator separator;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private Button btnSearch, btnAdd, btnSort, btnDelete, btnSave, btnLoad, btnBackward, btnForward, btnSearchSynonym;

    @FXML
    private WebView browser;

    @FXML
    private Label labelSynonym, labelMedien, labelSearch, labelWorker, labelTime;

    @FXML
    private ListView<String> listSynonym, listMedien;

    @FXML
    private void initialize() {
        browser.getEngine().load("https://de.wikibooks.org/wiki/");

        browser.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            //Worker State alle Worker fangen in Ready an, perfomt Aufgaben im Hintergrund, ermöglicht einen "Blick" in die "Zukunft"
            if (Worker.State.FAILED.equals(newValue)) {
                errorWikiBooks();
                comboClear();
            }
            if (Worker.State.SCHEDULED.equals(newValue)) {
                if (!browser.getEngine().getLocation().contains("https://de.wikibooks.org/")) {
                    browser.getEngine().load("https://de.wikibooks.org/wiki/");
                }
            }

            if (Worker.State.SUCCEEDED.equals(newValue)) {
                if (browser.getEngine().getLocation().contains("https://de.wikibooks.org/wiki/")) {
                    urlName = browser.getEngine().getLocation();
                    urlName = urlName.replace("https://de.wikibooks.org/wiki/", "");
                    tfSearch.setText(urlName);
                    search();

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
        });

        tfSearch.setOnMouseClicked((event -> {
            tfSearch.selectAll();
        }));

        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        btnSearch.setOnAction((event) -> {
            search();
        });

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

        btnSearchSynonym.setOnAction(event -> {
            buttonClear();
            searchSynonym();
            tfSearch.setText(String.valueOf(synonym));
        });

        listSynonym.setOnMouseClicked(event -> {
            synonym = listSynonym.getSelectionModel().getSelectedItems().get(0);
            if (event.getClickCount() == 2) {
                tfSearch.setText(synonym);
                comboClear();
            }
        });

        combo.setOnAction(event -> {
            synonym = combo.getSelectionModel().getSelectedItem();
            tfSearch.setText(synonym);
            searchSynonym();
            combo.getSelectionModel().select(synonym);
            buttonClear();
            browser.getEngine().load("https://de.wikibooks.org/wiki/" + synonym);
        });

        btnForward.setOnAction(event -> {
            combo.getSelectionModel().select(combo.getSelectionModel().getSelectedIndex() - 1);
        });

        btnBackward.setOnAction(event -> {
            combo.getSelectionModel().select(combo.getSelectionModel().getSelectedIndex() + 1);
        });

        menuItem.setOnAction(event -> {
            showInfo();
        });

        anchorpane.setOnKeyPressed(this::btnF1);
    }

    private String synonym = null;
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
/*
    private void safe() {
        try {

        } catch (Exception e) {
            System.out.println("Fehler beim speichern!");
        }
    }

    private void load() {

    }
*/
private Synonyme synonyme = new Synonyme();

    private void searchSynonym() {
        try {
            ObservableList<String> liste = synonyme.synonymList(tfSearch.getText());
            listSynonym.getItems().clear();
            if (liste.size() < 1) {
                listSynonym.getItems().add("<keine>");
                listSynonym.setDisable(true);
                btnSearchSynonym.setDisable(true);

            } else {
                listSynonym.setDisable(false);
                btnSearchSynonym.setDisable(false);
                listSynonym.getItems().addAll(liste);
            }
        } catch (Exception e) {
            SynonymError();
        }
    }

    private void buttonClear() {
        try {
            int wortIndex = combo.getSelectionModel().getSelectedIndex();

            if (wortIndex < 1) {
                btnForward.setDisable(true);
            } else {
                btnForward.setDisable(false);
            }
            if (wortIndex >= combo.getItems().size() - 1) {
                btnBackward.setDisable(true);
            } else {
                btnBackward.setDisable(false);
            }
        } catch (Exception e) {
            BtnError();
        }
    }

    private void comboClear() {
        try {
            int wordIndex = combo.getSelectionModel().getSelectedIndex();
            combo.getItems().remove(tfSearch.getText());

            if (wordIndex > 0) {
                combo.getItems().remove(0, wordIndex);
            }
            combo.getItems().add(0, tfSearch.getText());
            combo.getSelectionModel().select(0);
        } catch (Exception e) {
            BtnError();
        }
    }

    private void errorWikiBooks() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("WikiBooks ist momentan nicht erreichbar");
        alert.showAndWait();
    }

    private void SynonymError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("Fehler beim Zugriff auf Synonyme");
        alert.showAndWait();
    }

    private void BtnError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("Fehler bei der Durchführung einer der Knöpfe");
        alert.showAndWait();
    }
    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setContentText("WikiBooks ist momentan nicht erreichbar");
        alert.setContentText("Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.\n\nDie von Wikibooks bezogenen Inhalte unterliegen der GNU Free Documentation License und damit auch" +

                "dieses Programm. Der Text der GNU FDL ist unter" +

                "http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.\n\nDie von Wortschatz (http://wortschatz.uni-leipzig.de/) bezogenen Inhalte sind urheberrechtlich geschützt." +

                "Sie werden hier für wissenschaftliche Zwecke eingesetzt und dürfen darüber hinaus in keiner Weise" +

                "genutzt werden.\nDieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht.\n\n Dieses Programm dient" +

                "der Demonstration und dem Erlernen von Prinzipien der Programmierung mit Java. Eine Verwendung" +

                "des Programms für andere Zwecke verletzt die Urheberrechte der Originalautoren der redaktionellen" +

                "Inhalte und ist daher untersagt.");
        alert.showAndWait();
    }

    private void btnF1(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F1) {
            showInfo();
        }
    }

    private void medienList() {
        listMedien.getItems().clear();
        for (Medium medium : zettelkasten.getMedium_Arr()) {
            listMedien.getItems().add(String.valueOf(medium));
        }
    }

    private void setBookInformation(Medium medium) {
        if (medium != null) {
            labelWorker.setText("Letzter Bearbeiter: " + wikiBooks.getVerfasser());
            labelTime.setText("Letzte Änderung: " + wikiBooks.getAenderungsDatum());
        } else {
            labelWorker.setText("Letzter Bearbeiter: ");
            labelTime.setText("Letzte Änderung: ");
        }
    }
}