package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Commands {

    ArrayList<Command> commands;

    // Default IO interface
    public interface DefaultIO {
        public String readText();

        public void write(String text);

        public float readVal();

        public void write(float val);

        // you may add default methods here
    }

    // the default IO to be used in all commands
    DefaultIO dio;

    public Commands(DefaultIO dio) {
        this.dio = dio;
        this.commands = new ArrayList<>();
    }

    // you may add other helper classes here


    // the shared state of all commands
    private class SharedState {
        // implement here whatever you need

    }

    private SharedState sharedState = new SharedState();


    // Command abstract class
    public abstract class Command {
        protected String description;
        //DefaultIO dio;

        public Command(String description) {
            this.description = description;
        }

        public abstract void execute();
    }

    // Command class for example:
    public class ExampleCommand extends Command {

        public ExampleCommand() {
            super("this is an example of command");
        }

        @Override
        public void execute() {
            dio.write(description);
        }
    }

    public class WelcomeMenuCommand extends Command {

        public WelcomeMenuCommand() {
            super("WelcomeMenuCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("Welcome to the Anomaly Detection Server.\n" +
                    "Please choose an option:\n" +
                    "1. upload a time series csv file\n" +
                    "2. algorithm settings\n" +
                    "3. detect anomalies\n" +
                    "4. display results\n" +
                    "5. upload anomalies and analyze results\n" +
                    "6. exit");
            dio.write(sb.toString());
        }
    }

    public class LineSeparatorCommand extends Command {

        public LineSeparatorCommand() {
            super("LineSeparatorCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("\n");
            dio.write(sb.toString());
        }
    }

    public class UploadTrainFileCommand extends Command {

        public UploadTrainFileCommand() {
            super("UploadTrainFileCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("Please upload your local train CSV file.\n" +
                    "Upload complete.");
            dio.write(sb.toString());
        }
    }

    public class UploadTestFileCommand extends Command {

        public UploadTestFileCommand() {
            super("UploadTestFileCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("Please upload your local test CSV file.\n" +
                    "Upload complete.");
            dio.write(sb.toString());
        }
    }

    public class AlgorithmSettingsCommand extends Command {

        public AlgorithmSettingsCommand() {
            super("AlgorithmSettingsCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("The current correlation threshold is 0.9\n" +
                    "Type a new threshold");
            dio.write(sb.toString());
        }
    }

    public class AnomalyDetectionCommand extends Command {

        public AnomalyDetectionCommand() {
            super("AnomalyDetectionCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("anomaly detection complete.");
            dio.write(sb.toString());
        }
    }

    public class DisplayResultsCommand extends Command {

        public DisplayResultsCommand() {
            super("DisplayResultsCommand");
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("73\t A-B\n" +
                    "74\t A-B\n" +
                    "75\t A-B\n" +
                    "76\t A-B\n" +
                    "133\t C-D\n" +
                    "134\t C-D\n" +
                    "135\t C-D\n" +
                    "Done.");
            dio.write(sb.toString());
        }
    }


    public class UploadAndAnalyzeResultsCommand extends Command {
        double falsePositive, truePositive;

        public UploadAndAnalyzeResultsCommand(double truePositive, double falsePositive) {
            super("UploadAndAnalyzeResultsCommand");
            this.falsePositive = falsePositive;
            this.truePositive = truePositive;
        }

        @Override
        public void execute() {
            StringBuffer sb = new StringBuffer();
            sb.append("Please upload your local anomalies file.\n" +
                    "Upload complete.\n" +
                    "True Positive Rate: " + this.truePositive + "\n" +
                    "False Positive Rate: " + this.falsePositive);
            dio.write(sb.toString());
        }
    }
}
