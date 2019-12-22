package sample;

import java.io.Serializable;

/**
 * The type Zeitschrift.
 * Übernahme der Klasse von Florian Eimann
 */
public class Zeitschrift extends Medium implements Serializable {


    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new Zeitschrift.
     *
     * @param _Titel  the titel
     * @param _ISSN   the issn
     * @param _Volume the volume
     * @param _Nummer the nummer
     */
    public Zeitschrift(final String _Titel, final String _ISSN, final int _Volume, final int _Nummer) {
        super(_Titel);
        setISSN(_ISSN);
        setVolume(_Volume);
        setNummer(_Nummer);

    }

    public String calculateRepresentation() {
        StringBuilder sb = new StringBuilder();

        if (!getTitel().equals("")) {
            sb.append("Titel: ").append(this.getTitel());
        }
        if (!getISSN().equals("")) {
            sb.append("\nErscheinungsjahr: ").append(this.getISSN());
        }
        if (getVolume() != 0) {
            sb.append("\nVerlag: ").append(this.getVolume());
        }
        if (getNummer() != 0) {
            sb.append("\nISBN: ").append(this.getNummer());
        }
        return sb.toString();
    }

    private String ISSN;
    private int Volume = 0;
    private int Nummer = 0;


    /**
     * Gets issn.
     *
     * @return the issn
     */
    public String getISSN() {
        return ISSN;
    }

    /**
     * Sets issn.
     *
     * @param _ISSN the issn
     */
    @SuppressWarnings("null")
    public void setISSN(final String _ISSN) {
        if ((_ISSN != null) || (!(_ISSN.isEmpty())))
            ISSN = _ISSN;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public int getVolume() {
        return Volume;
    }

    /**
     * Sets volume.
     *
     * @param _Volume the volume
     */
    public void setVolume(final int _Volume) {
        if (_Volume != 0)
            this.Volume = _Volume;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Gets nummer.
     *
     * @return the nummer
     */
    public int getNummer() {
        return Nummer;
    }

    /**
     * Sets nummer.
     *
     * @param _Nummer the nummer
     */
    public void setNummer(final int _Nummer) {
        if (_Nummer != 0)
            this.Nummer = _Nummer;
        else
            throw new IllegalArgumentException();
    }
}