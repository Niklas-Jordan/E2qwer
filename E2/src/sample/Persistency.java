package sample;

/**
 * The interface Persistency.
 * Übernahme der Klasse von Florian Eimann
 */
public interface Persistency {

    /**
     * Save.
     *
     * @param ZK        the zk
     * @param Dateiname the dateiname
     */
    public static void save(Zettelkasten ZK, String Dateiname) {
    }

    /**
     * Load zettelkasten.
     *
     * @param Dateiname the dateiname
     * @return the zettelkasten
     */
    public static Zettelkasten load(String Dateiname) {
        return null;
    }
}