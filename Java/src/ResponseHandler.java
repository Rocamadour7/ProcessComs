import java.util.concurrent.ConcurrentLinkedQueue;

public class ResponseHandler {
    private Protocol protocol;
    private ConcurrentLinkedQueue<String> queue;

    public ResponseHandler(Protocol protocol, ConcurrentLinkedQueue<String> queue) {
        this.protocol = protocol;
        this.queue = queue;
    }

    public void pushToQueue() {
        String log = protocol.getLog();
        queue.add(log);
    }
}
