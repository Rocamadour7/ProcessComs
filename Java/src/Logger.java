import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger implements Runnable {

    private final ConcurrentLinkedQueue<String> queue;
    private FileOutputStream fileOutputStream;

    public Logger(ConcurrentLinkedQueue<String> queue, FileOutputStream fileOutputStream) {
        this.queue = queue;
        this.fileOutputStream = fileOutputStream;
    }

    @Override
    public void run() {
        synchronized (queue) {
            while (true) {
                if (!queue.isEmpty()) {
                    try {
                        fileOutputStream.write(queue.poll().getBytes("UTF-8"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
