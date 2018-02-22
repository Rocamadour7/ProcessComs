import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer extends Thread {

    private final ExecutorService workers = Executors.newCachedThreadPool();
    private ServerSocket listenSocket;
    private volatile boolean keepRunning = true;

    ThreadPoolServer(final int port) {
        Runtime.getRuntime().addShutdownHook(new Thread(ThreadPoolServer.this::shutdown));
        try {
            listenSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("An exception occurred while creating the listen socket: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            listenSocket.setSoTimeout(10000);
        } catch (SocketException e1) {
            System.err.println("Unable to set acceptor timeout value.  The server may not shutdown gracefully.");
        }

        System.out.println("Accepting incoming connections on port " + listenSocket.getLocalPort());

        while (keepRunning) {
            try {
                final Socket clientSocket = listenSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                workers.execute(handler);

            } catch (SocketTimeoutException ignored) {

            } catch (IOException e) {
                System.err.println("Exception occurred while handling client request: " + e.getMessage());
                Thread.yield();
            }
        }
        try {
            listenSocket.close();
        }catch(IOException ignored){

        }
        System.out.println("Stopped accepting incoming connections.");
    }

    private void shutdown() {
        System.out.println("Shutting down the server.");
        keepRunning = false;
        workers.shutdownNow();
        try {
            join();
        } catch (InterruptedException ignored) {

        }
    }
}