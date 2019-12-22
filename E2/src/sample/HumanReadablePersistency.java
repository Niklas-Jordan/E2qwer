package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * The type Human readable persistency.
 * Übernahme der Klasse von Florian Eimann
 */
public class HumanReadablePersistency implements Persistency {

    /**
     * Save.
     *
     * @param ZK        the zk
     * @param Dateiname the dateiname
     */
    public void save(Zettelkasten ZK, String Dateiname) {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Dateiname), StandardCharsets.UTF_8));

            for (Medium output : ZK) {
                out.write(output.calculateRepresentation());
            }
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            System.out.print("Error: File: \"" + Dateiname + "\" Not Found:");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Load zettelkasten.
     *
     * @param Dateiname the dateiname
     * @return the zettelkasten
     * @throws UnsupportedOperationException the unsupported operation exception
     */
    public Zettelkasten load(String Dateiname) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}