package test;


import test.Commands.DefaultIO;
import test.Server.ClientHandler;

public class AnomalyDetectionHandler implements ClientHandler {

    public class SocketIO implements DefaultIO {

        @Override
        public String readText() {
            return null;
        }

        @Override
        public void write(String text) {

        }

        @Override
        public float readVal() {
            return 0;
        }

        @Override
        public void write(float val) {

        }
    }


}
