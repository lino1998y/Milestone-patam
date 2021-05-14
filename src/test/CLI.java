package test;

import java.util.ArrayList;
import java.util.Scanner;

import test.Commands.Command;
import test.Commands.DefaultIO;

public class CLI {

    //    ArrayList<Command> commands;
    DefaultIO dio;
    Commands c;

    public CLI(DefaultIO dio) {
        this.dio = dio;
        c = new Commands(dio);
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadTrainFileCommand());
        c.commands.add(c.new UploadTestFileCommand());
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new AlgorithmSettingsCommand());
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new AnomalyDetectionCommand());
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new DisplayResultsCommand());
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadAndAnalyzeResultsCommand(0.0, 0.01));
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadAndAnalyzeResultsCommand(0.5, 0.005));
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadAndAnalyzeResultsCommand(1.0, 0.005));
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadAndAnalyzeResultsCommand(1.0, 0.00));
        c.commands.add(c.new WelcomeMenuCommand());
        c.commands.add(c.new UploadAndAnalyzeResultsCommand(0.666, 0.00));
        c.commands.add(c.new WelcomeMenuCommand());
    }

    public void start() {
        Command line = c.new LineSeparatorCommand();
        for (Command c : c.commands) {
            c.execute();
            line.execute();

        }
//        System.out.println("Welcome to the Anomaly Detection Server.\n" +
//                "Please choose an option:");
//        int counter = 0;
//        for (Command c : this.commands) {
//            System.out.println(String.format("%d" + c.description, counter));
//            counter++;
//        }
//        Scanner myObj = new Scanner(System.in);
//        String optionText = myObj.nextLine();
//        int optionNumber = Integer.parseInt(optionText);
//        this.commands.get(optionNumber).execute();
    }
}
