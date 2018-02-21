import java.io.*;
import java.net.Socket;

public class Worker implements Runnable{

    private Socket clientSocket = null;

    Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            output.write(("Testing...").getBytes());
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}