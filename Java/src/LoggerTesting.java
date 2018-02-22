import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggerTesting {

    public static void main(String[] args) throws FileNotFoundException {
        String updateMessage = "U" + "sensor11" + "123456.8" + "150245" + "26041992" + "1982";
        String requestMessage = "R" + "observer" + "sensor11" + "1718";

        ProtocolFactory protocolFactory = new ProtocolFactory();

        File file = new File("./logger.csv");

        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            new Thread(new ResponseHandler(protocolFactory.getProtocol(updateMessage), queue)).start();
        }

        new Thread(new Logger(queue, fileOutputStream)).start();
    }
}
