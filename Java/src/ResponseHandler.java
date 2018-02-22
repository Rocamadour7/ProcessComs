import java.util.concurrent.ConcurrentLinkedQueue;

public class ResponseHandler implements Runnable {

    private Protocol protocol;
    private ConcurrentLinkedQueue<String> queue;

    ResponseHandler(Protocol protocol, ConcurrentLinkedQueue<String> queue) {
        this.protocol = protocol;
        this.queue = queue;
    }

    private void pushToQueue() {
        String log = protocol.getLog();
        queue.add(log + "\n");
    }

    @Override
    public void run() {
        pushToQueue();
    }
}
