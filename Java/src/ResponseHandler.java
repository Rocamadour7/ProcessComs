import javax.xml.crypto.Data;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

class ResponseHandler {

    static void pushToQueue(Protocol protocol, ConcurrentLinkedQueue<String> updateQueue, ConcurrentLinkedQueue<String> requestQueue, DataOutputStream userOutput) {
        String log = protocol.getLog();
        switch (protocol.getType()) {
            case "U":
                updateQueue.add(log + "\n");
                break;
            case "R":
                requestQueue.add(log + "\n");
                try {
                    result(userOutput);
                } catch (IOException ignored) {
                }
                break;
        }
    }

    private static void result(DataOutputStream userOutput) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader("./updateLog.csv"));

        String last = null;
        String line;

        while ((line = input.readLine()) != null) {
            last = line;
        }

        if (last != null) {
            userOutput.write(last.getBytes("UTF-8"));
            userOutput.flush();
        }
    }
}
