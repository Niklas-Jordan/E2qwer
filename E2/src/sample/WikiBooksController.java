package sample;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.util.ArrayList;

/**
 * The type Wiki books controller.
 * @author Niklas Jordan SMIB
 * In Zusammenarbeit mit Florian Eimann
 */
public class WikiBooksController {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private MenuItem menuItem;

    @FXML
    private TextField tfSearch;


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
        // lädt die Website Wikibooks
        browser.getEngine().load("https://de.wikibooks.org/wiki/");
        // übernimmt State des Workloaders von der Webview Engine
        browser.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            //Worker State alle Worker beginnen mit Ready, läuft im Hintergrund, überprüft das Ergebnis vor der Ausführung
            if (Worker.State.FAILED.equals(newValue)) {
                errorWikiBooks();
                comboClear();
            }
            // Umleitung bei Link, welcher von Wikibooks weglenkt -> Zurückleitung zur Startseite
            if (Worker.State.SCHEDULED.equals(newValue)) {
                if (!browser.getEngine().getLocation().contains("https://de.wikibooks.org/")) {
                    browser.getEngine().load("https://de.wikibooks.org/wiki/");
                }
            }
            // setzt aktuellen Begriff in die Suchleiste bei Verbindungsaufbau
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                if (browser.getEngine().getLocation().contains("https://de.wikibooks.org/wiki/")) {
                    urlName = browser.getEngine().getLocation();
                    urlName = urlName.replace("https://de.wikibooks.org/wiki/", "");
                    tfSearch.setText(urlName);
                    search();
                    // setzt den Titel in das Suchfeld, wenn er als Buchtitel erkannt wird
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
        // Doppelklick auf Synonym -> lässt Synonym suchen
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
        // setzt den Index in der Combobox um eine Stelle zurück, wenn der Knopf gedrückt wird
        btnForward.setOnAction(event -> {
            combo.getSelectionModel().select(combo.getSelectionModel().getSelectedIndex() - 1);
        });
        // setzt den Index in der Combobox eine Stelle vorran, wenn der Knopf gedrückt wird
        btnBackward.setOnAction(event -> {
            combo.getSelectionModel().select(combo.getSelectionModel().getSelectedIndex() + 1);
        });

        menuItem.setOnAction(event -> {
            showInfo();
        });
        btnSave.setOnAction(event -> {
            save();
        });
        btnLoad.setOnAction(event -> {
            load();
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
                searchSynonym();
                comboClear();
                wikiBooks.calculateRepraesentation();
            }
            WikiBooksParser books = new WikiBooksParser();
            wikiBooks = (WikiBooks) books.parse(search);
            setBookInformation(wikiBooks);
        } catch (NullPointerException e) {
            System.out.println("Fehler beim Suchen!");
        }
    }

    // vergleicht Medien vom zettelkasten mit data und fügt es dem Zettelkasten hinzu wenn noch nicht vorhanden
    private void add() {
        try {
            zettelkasten.addMedium(wikiBooks);
            for (Medium medium : data) {
                if (!data.contains(wikiBooks)) {
                    zettelkasten.addMedium(medium);
                } else {
                    System.out.println("ERROR");
                }
            }
        } catch (Exception e) {
            errorWikiBooks();
        }
    }

    private void sort() {
        try {
            zettelkasten.sort(direction);
            medienList();
            if (!direction.equals("ab")) {
                direction = "ab";
            } else {
                direction = "auf";
            }
        } catch (Exception e) {
            errorWikiBooks();
        }
    }

    // laut Aufgabenstellung soll nichts passieren
    private void delete() {
        try {
            zettelkasten.dropMedium("q", selectedItemBuch.getTitel());
            medienList();
        } catch (Exception e) {
            errorWikiBooks();
        }
    }

    //TODO: Safe and Load function missing; Buttons in fxml need to be added too
    private void save() {
        try {

        } catch (Exception e) {
            System.out.println("Fehler beim speichern!");
        }
    }

    private void load() {
        try {

        } catch (Exception e) {
            System.out.println("Fehler beim laden!");
        }
    }

    private Synonyme synonyme = new Synonyme();

    private void searchSynonym() {
        try {
            ObservableList<String> liste = synonyme.synonymList(tfSearch.getText());
            liste.sort(String::compareTo);
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

    // überprüft den int wordIndex, wenn > 0 ersetzt er 0 mit dem gefundenen wordIndex, wordIndex = tfSearch
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

    // Errormeldung für den Zugriff auf Wikibooks
    private void errorWikiBooks() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("WikiBooks ist momentan nicht erreichbar");
        alert.showAndWait();
    }

    // Errormeldung für Synonyme
    private void SynonymError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("Fehler beim Zugriff auf Synonyme");
        alert.showAndWait();
    }

    // ErrorMeldung für die Knöpfe
    private void BtnError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setContentText("Fehler bei der Durchführung einer der Knöpfe");
        alert.showAndWait();
    }

    /**
     * Show info.
     * gibt die Info Meldung aus
     */
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

    // KeyEvent für F1
    private void btnF1(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F1) {
            showInfo();
        }
    }

    // vergleicht medium mit dem Array und fügt diesen zur Liste hinzu
    private void medienList() {
        listMedien.getItems().clear();
        for (Medium medium : zettelkasten.getMedium_Arr()) {
            listMedien.getItems().add(String.valueOf(medium));
        }
    }

    // greift auf WikiBooks zu und setzt letzten Bearbeiter und Änderungszeit, wenn das Medium existiert
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