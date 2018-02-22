import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    public static void main(String[] args) throws FileNotFoundException {

        final ProtocolFactory protocolFactory = new ProtocolFactory();

        File updateLogFile = new File("./updateLog.csv");
        File requestLogFile = new File("./requestLog.csv");

        FileOutputStream updateLogOutputStream = new FileOutputStream(updateLogFile, true);
        FileOutputStream requestLogOutputStream = new FileOutputStream(requestLogFile, true);

        ConcurrentLinkedQueue<String> updateQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<String> requestQueue = new ConcurrentLinkedQueue<>();

        Thread updateLogger = new Thread(new Logger(updateQueue, updateLogOutputStream));
        Thread requestLogger = new Thread(new Logger(requestQueue, requestLogOutputStream));

        updateLogger.start();
        requestLogger.start();

        final ThreadPoolServer server = new ThreadPoolServer(8080, protocolFactory, updateQueue, requestQueue);

        server.start();

        try {
            updateLogger.join();
            requestLogger.join();
            server.join();
            System.out.println("Completed shutdown.");
        } catch (InterruptedException e) {
            System.err.println("Interrupted before accept thread completed.");
            System.exit(1);
        }

    }
}