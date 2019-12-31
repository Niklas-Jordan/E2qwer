package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * The type Tmp file.
 * @author Niklas Jordan SMIB
 * In Zusammenarbeit mit Florian Eimann
 */
public class TmpFile {

    /**
     * Create tmp file file.
     *
     * @param _tmp the tmp
     * @return the file
     * @throws IOException the io exception
     * erstellt tmpFile
     * geht die Strings durch aus der ArrayList
     */
    public File createTmpFile(ArrayList<String> _tmp) throws IOException {
        File datei = File.createTempFile("temp", ".xml");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(datei), "UTF8"));
        datei.deleteOnExit();

        for (String line : _tmp) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();

        BufferedReader br = new BufferedReader(new FileReader(datei));
        br.close();
        return datei;
    }
}