import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ProtocolFactory protocolFactory;
    private final ConcurrentLinkedQueue<String> updateQueue;
    private final ConcurrentLinkedQueue<String> requestQueue;

    ClientHandler(final Socket clientSocket, ProtocolFactory protocolFactory, ConcurrentLinkedQueue<String> updateQueue, ConcurrentLinkedQueue<String> requestQueue) {
        this.clientSocket = clientSocket;
        this.protocolFactory = protocolFactory;
        this.updateQueue = updateQueue;
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        BufferedReader userInput = null;
        DataOutputStream userOutput = null;
        try {
            userInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            userOutput = new DataOutputStream(clientSocket.getOutputStream());

            String origLine = userInput.readLine();

            Protocol protocol = protocolFactory.getProtocol(origLine);

            if (protocol != null) {
                ResponseHandler.pushToQueue(protocol, updateQueue, requestQueue, userOutput);
                System.out.println(clientSocket.getRemoteSocketAddress() + ": " + origLine);
            }
        } catch (IOException ignored) {

        }
        try {
            if (userInput != null) {
                userInput.close();
            }
            if (userOutput != null) {
                userOutput.close();
            }
            clientSocket.close();
        } catch (IOException ignored) {

        }
    }
}