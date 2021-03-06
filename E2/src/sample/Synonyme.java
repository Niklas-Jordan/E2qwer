package sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Synonyme.
 * @author Niklas Jordan SMIB
 * In Zusammenarbeit mit Florian Eimann
 */
public class Synonyme {
    private String basisUrl = "http://api.corpora.uni-leipzig.de/ws/similarity/";
    private String corpus = "deu_news_2012_1M";
    private String request = "/coocsim/";
    private String search = "";
    private String parameter = "?minSim=0.1&limit=50";

    /**
     * Synonym list observable list.
     *
     * @param wort the wort
     * @return the observable list
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public ObservableList<String> synonymList(String wort) throws IOException, ParseException {
        search = wort;
        ObservableList<String> output = FXCollections.observableArrayList();

        JSONArray jsonResponseArr = null;
        JSONObject wordContainer = null;
        String synonym = null;

        URL myURL = new URL(basisUrl + corpus + request + search + parameter);
        JSONParser jsonParser = new JSONParser();
        String jsonResponse = streamToString(myURL.openStream());

        //parst gelieferten String in ein Array
        jsonResponseArr = (JSONArray) jsonParser.parse(jsonResponse);

        for (Object e1 : jsonResponseArr) {

            //beschafft das Element aus dem Container fuer das Synonym
            wordContainer = (JSONObject) ((JSONObject) e1).get("word");

            //beschafft das Synonym aus dem Container
            synonym = (String) wordContainer.get("word");

            output.add(synonym);
        }

        return output;
    }

    /**
     * Stream to string string.
     *
     * @param is the is
     * @return the string
     * @throws IOException the io exception
     */
    public static String streamToString(InputStream is) throws IOException {
        String result = "";

        // inputstream - to - a - string
        try (Scanner s = new Scanner(is)) {
            s.useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            is.close();
        }
        return result;
    }
}