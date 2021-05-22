package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    volatile boolean stop;

    public interface ClientHandler {
        void handleClient(InputStream inFromClient, OutputStream outToClient);
    }

    public Server() {
        this.stop = false;
    }

    public void stop() {
        this.stop = true;
    }

    private void startServer(int port, ClientHandler ch) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (!stop) {
                server.setSoTimeout(1000);
                try {
                    Socket client = server.accept();
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();
                    ch.handleClient(in, out);
                    in.close();
                    out.close();
                    client.close();
                    stop();
                } catch (IOException E) {
                }
            }
            server.close();
        } catch (SocketException e) {
        } catch (IOException e1) {
        }
    }

    // runs the server in its own thread
    public void start(int port, ClientHandler ch) {
        new Thread(() -> this.startServer(port, ch)).start();
    }
}
