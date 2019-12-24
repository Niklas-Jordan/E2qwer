package sample;

public class MyWebException extends Exception {

    private static final long serialVersionUID = 1L;

    public MyWebException () {
        super("Fehler beim laden von Wikibooks!");
    }
}
