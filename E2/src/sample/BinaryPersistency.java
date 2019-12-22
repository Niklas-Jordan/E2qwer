package sample;

import java.io.*;
import java.util.ArrayList;

/**
 * The type Binary persistency.
 * Übernahme der Klasse von Florian Eimann
 */
public class BinaryPersistency implements Persistency, Serializable {


    private static final long serialVersionUID = 1L;
    private ArrayList<Zettelkasten> Binaere = new ArrayList<Zettelkasten>();

    /**
     * Save.
     *
     * @param ZK        the zk
     * @param Dateiname the dateiname
     */
    public void save(final Zettelkasten ZK, final String Dateiname) {

        ObjectOutputStream OOu = null;
        FileOutputStream FOu = null;

        try {

            FOu = new FileOutputStream(Dateiname);
            OOu = new ObjectOutputStream(FOu);
            OOu.writeObject(ZK);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                assert OOu != null;
                OOu.flush();
                OOu.close();
                FOu.flush();
                FOu.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load zettelkasten.
     *
     * @param Dateiname the dateiname
     * @return the zettelkasten
     */
    public Zettelkasten load(final String Dateiname) {

        Zettelkasten ZK = null;
        FileInputStream FIn = null;
        ObjectInputStream OIn = null;

        try {
            FIn = new FileInputStream(Dateiname);
            OIn = new ObjectInputStream(FIn);
            ZK = (Zettelkasten) OIn.readObject();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            //System.out.println("Class not found");
            c.printStackTrace();
        } finally {
            try {
                assert OIn != null;
                OIn.close();
                FIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ZK;
    }

    /**
     * Gets binaere.
     *
     * @return the binaere
     */
    public ArrayList<Zettelkasten> getBinaere() {
        return Binaere;
    }

    /**
     * Sets binaere.
     *
     * @param binaere the binaere
     */
    public void setBinaere(ArrayList<Zettelkasten> binaere) {
        Binaere = binaere;
    }
}