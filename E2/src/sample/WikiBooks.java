package sample;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * The type Wiki books.
 */
public class WikiBooks extends Medium implements Serializable {

    private String url;

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = "https://de.wikibooks.org/wiki/ " + url;
    }

    private static final long serialVersionUID = 1L;

    private String Verfasser = "";
    private String sAenderungsDatum = null;
    private ArrayList<String> arr_Kapitel = new ArrayList<String>();
    private String sRegal = null;
    private boolean bIP = false;

    /**
     * Sets verfasser.
     *
     * @param _Verfasser the verfasser
     */
    @SuppressWarnings("null")
    public void setVerfasser(final String _Verfasser) {
        if ((_Verfasser != null) || (!(_Verfasser.isEmpty())))
            this.Verfasser = _Verfasser;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Gets verfasser.
     *
     * @return the verfasser
     */
    public String getVerfasser() {
        return Verfasser;
    }


    /**
     * Is ip boolean.
     *
     * @return the boolean
     */
    public boolean isIP() {
        return bIP;
    }

    /**
     * Sets ip.
     *
     * @param _bIP the b ip
     */
    public void setIP(boolean _bIP) {
        this.bIP = _bIP;
    }


    /**
     * Gets aenderungs datum.
     *
     * @return the aenderungs datum
     */
    public String getAenderungsDatum() {
        return sAenderungsDatum;
    }


    /**
     * Sets aenderungs datum.
     *
     * @param _sAenderungsDatum the s aenderungs datum
     */
    public void setAenderungsDatum(String _sAenderungsDatum) {
        this.sAenderungsDatum = _sAenderungsDatum;
    }


    /**
     * Gets kapitel.
     *
     * @return the kapitel
     */
    public ArrayList<String> getKapitel() {
        return arr_Kapitel;
    }


    /**
     * Sets kapitel.
     *
     * @param _arr_Kapitel the arr kapitel
     */
    public void setKapitel(ArrayList<String> _arr_Kapitel) {
        this.arr_Kapitel = _arr_Kapitel;
    }

    /**
     * Gets regal.
     *
     * @return the regal
     */
    public String getRegal() {
        return sRegal;
    }

    /**
     * Sets regal.
     *
     * @param _sRegal the s regal
     */
    public void setRegal(String _sRegal) {
        this.sRegal = _sRegal;
    }

    /**
     * Instantiates a new Wiki books.
     * Speicherung der übergebenen Daten mittels Setter-Methoden.
     *
     * @param _Titel            the titel
     * @param _sAenderungsdatum the s aenderungsdatum
     * @param _sRegal           the s regal
     * @param _arr_Kapitel      the arr kapitel
     * @param _Verfasser        the verfasser
     */
    public WikiBooks(String _Titel, String _sAenderungsdatum, String _sRegal, ArrayList<String> _arr_Kapitel, String _Verfasser) {
        super(_Titel);
        setRegal(_sRegal);
        setKapitel(_arr_Kapitel);
        setAenderungsDatum(_sAenderungsdatum);
        setVerfasser(_Verfasser);
    }

    /**
     * Convert time string.
     *
     * @param _sTime the s time
     * @return the string
     */
    public static String convertTime(String _sTime) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long ts = System.currentTimeMillis();
        Date localTime = new Date(ts);

        _sTime = _sTime.replace('T', ' ');
        _sTime = _sTime.replace('Z', ' ');

        try {
            Date utcTime = formatDate.parse(_sTime);

            Date gmtTime = new Date((utcTime.getTime() + TimeZone.getDefault().getOffset(localTime.getTime())));
            Date gmtDate = new Date((utcTime.getTime() + TimeZone.getDefault().getOffset(localTime.getTime())));

            return new SimpleDateFormat("dd.MM.yyyy").format(gmtDate) + " um "
                    + new SimpleDateFormat("HH:mm").format(gmtTime) + " Uhr";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Fehler";

    }

    /**
     * Calculate repraesentation string.
     * Ausgabe einer vordefinierten Stringkette
     *
     * @return the string
     * StringKette <String> (Titel+ Regal + Kapitel + Änderungungsdatum + Verfasser)
     */
    public String calculateRepraesentation() {
        StringBuilder str = new StringBuilder().append("Titel: \"" + Titel + "\" " +
                "\nRegal: " + sRegal + "\nKapitel:");
        for (String s : arr_Kapitel) {
            str.append("\n" + s);
        }

        str.append("\nLetzte Änderung am " + convertTime(sAenderungsDatum));
        if (bIP)
            str.append("\nIP: " + Verfasser);
        else
            str.append("\nUrheber: " + Verfasser);
        return str.toString();
    }

    @Override
    public String calculateRepresentation() {
        // TODO Auto-generated method stub
        return new String(Titel);
    }
}
