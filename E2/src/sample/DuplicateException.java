package sample;

import java.io.IOException;

/**
 * The type Duplicate exception.
 * Übernahme der Klasse von Florian Eimann
 */
public class DuplicateException extends IOException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Duplicate exception.
     *
     * @param _sMsg the s msg
     */
    public DuplicateException(String _sMsg) {
        super(_sMsg);
    }

    public String getMessage() {
        return "HINWEIS: Mehrfache Eintraege gefunden!!!";
    }
}