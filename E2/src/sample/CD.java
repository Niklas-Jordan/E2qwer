package sample;

import java.io.Serializable;

/**
 * The type Cd.
 * Übernahme der Klasse von Florian Eimann
 */
public class CD extends Medium implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Cd.
     *
     * @param _Titel     the titel
     * @param _Label     the label
     * @param _Kuenstler the kuenstler
     * Speicherung der Daten über Setter-Methoden
     * Ausgabe einer vordefinierten Stringkette
     */

    public CD(final String _Titel, final String _Label, final String _Kuenstler) {
        super(_Titel);
        setLabel(_Label);
        setKuenstler(_Kuenstler);
    }

    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();

        if (!getTitel().equals("")) {
            sb.append("Titel: ").append(this.getTitel());
        }
        if (!getLabel().equals("")) {
            sb.append("\nErscheinungsjahr: ").append(this.getLabel());
        }
        if (!getKuenstler().equals("")) {
            sb.append("\nVerlag: ").append(this.getKuenstler());
        }

        return sb.toString();
    }

    //Feld-Definition
    private String Label = "";
    private String Kuenstler = "";

    /**
     * Gets label.
     *
     * @return the label
     */

    public String getLabel() {
        return Label;
    }

    /**
     * Sets label.
     *
     * @param _Label the label
     */
    @SuppressWarnings("null")
    public void setLabel(final String _Label) {
        if ((_Label != null) || (!(_Label.isEmpty())))
            this.Label = _Label;
        else
            throw new IllegalArgumentException("Ein Label muss anggeben werden.");
    }

    /**
     * Gets kuenstler.
     *
     * @return the kuenstler
     */
    public String getKuenstler() {
        return Kuenstler;
    }

    /**
     * Sets kuenstler.
     *
     * @param _Kuenstler the kuenstler
     */
    @SuppressWarnings("null")
    public void setKuenstler(final String _Kuenstler) {
        if ((_Kuenstler != null) || (!(_Kuenstler.isEmpty())))
            this.Kuenstler = _Kuenstler;
        else
            throw new IllegalArgumentException("Geben Sie einen Kuenstlername an.");
    }
}