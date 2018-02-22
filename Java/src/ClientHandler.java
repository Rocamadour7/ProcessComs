import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSock;

    ClientHandler(final Socket clientSocket) {
        clientSock = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader userInput = null;
        DataOutputStream userOutput = null;
        try {
            userInput = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            userOutput = new DataOutputStream(clientSock.getOutputStream());
            String origLine;
            while ((origLine = userInput.readLine()) != null) {
                System.out.println(origLine);
                String upperLine = origLine.toUpperCase() + "\n";
                userOutput.write(upperLine.getBytes("UTF-8"));
                userOutput.flush();
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
            clientSock.close();
            System.err.println("Lost connection to " + clientSock.getRemoteSocketAddress());
        } catch (IOException ignored) {

        }
    }
}