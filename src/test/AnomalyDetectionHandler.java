package test;

import test.Commands.DefaultIO;
import test.Server.ClientHandler;

import java.io.*;
import java.util.Scanner;

public class AnomalyDetectionHandler implements ClientHandler {

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        SocketIO socket = new SocketIO(outToClient, inFromClient);
        CLI cli = new CLI(socket);
        cli.start();
        cli.dio.write("bye");
    }

    public class SocketIO implements DefaultIO {

        Scanner in;
        PrintWriter out;

        public SocketIO(OutputStream outToClient, InputStream inFromClient) {
            in = new Scanner(inFromClient);
            out = new PrintWriter(outToClient);
        }

        @Override
        public String readText() {
            return in.nextLine();
        }

        @Override
        public void write(String text) {
            out.print(text);
            out.flush();
        }

        @Override
        public float readVal() {
            return in.nextFloat();
        }

        @Override
        public void write(float val) {
            out.print(val);
            out.flush();
        }
    }
}