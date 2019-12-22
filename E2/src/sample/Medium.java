package sample;

import java.io.Serializable;

/**
 * The type Medium.
 * Übernahme der Klasse von Florian Eimann
 */
public abstract class Medium implements Comparable<Medium>, Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * The Titel.
     * Feld Definition
     */
    protected String Titel = "";

    /**
     * Instantiates a new Medium.
     *
     * @param title the title
     */
    public Medium(final String title) {
        setTitel(title);
    }

    /**
     * Gets titel.
     *
     * @return the titel
     */
    public String getTitel() {
        return Titel;
    }

    /**
     * Sets titel.
     *
     * @param _Titel the titel
     */
    @SuppressWarnings("null")
    public void setTitel(final String _Titel) {
        if ((_Titel != null) || (!(_Titel.isEmpty())))
            this.Titel = _Titel;
        else
            throw new IllegalArgumentException("Geben Sie den Titel des Mediums an.");
    }

    /**
     * Calculate representation string.
     *
     * @return the string
     * Abstrakte Ausgabe Methode
     */
    public abstract String calculateRepresentation();

    @Override
    public int compareTo(final Medium x) {
        if (this.getTitel().equals(x.getTitel())) {
            return this.getClass().getName().compareTo(x.getClass().getName());
        }
        return this.getTitel().compareTo(x.getTitel());
    }
}