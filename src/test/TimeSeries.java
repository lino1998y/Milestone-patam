package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TimeSeries {
    String myCsvName;
    private Columns[] cols;
    Path pathOfFile;

    public TimeSeries(String csvFileName) {
        this.myCsvName = csvFileName;
        this.pathOfFile = Paths.get(this.myCsvName);
        this.readCsvFile();

    }

    public void readCsvFile() {
        String line ;
        String splitBy = ",";

        try {
            // initialize columns array
            BufferedReader br = new BufferedReader((new FileReader(this.myCsvName)));
            line = br.readLine();
            String[] value = line.split(splitBy);
            this.cols = new Columns[value.length];
            for (int j = 0; j < value.length; j++) {
                this.cols[j] = new Columns(value[j]);
            }

            // go over the numbers in each column
            line = br.readLine();
            while (line != null) {
                 value = line.split(splitBy);
                for (int j = 0; j < value.length; j++) {
                   cols[j].getFloats().add(Float.parseFloat(value[j]));
                }
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class Columns {
        private String name;// A B
        private ArrayList<Float> floats;

        public Columns(String name) {
            this.name = name;
            this.floats = new ArrayList<Float>();
        }

        public String getName() {
            return this.name;
        }

        public void setName(String newName) {
            this.name = newName;
        }

        public void setFloats(ArrayList<Float> floats) {
            this.floats = floats;
        }

        public ArrayList<Float> getFloats() {
            return this.floats;
        }
    }
}
