import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger extends Thread {

    private final ConcurrentLinkedQueue<String> queue;
    private FileOutputStream fileOutputStream;

    Logger(ConcurrentLinkedQueue<String> queue, FileOutputStream fileOutputStream) {
        Runtime.getRuntime().addShutdownHook(new Thread(Logger.this::shutdown));
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

    private void shutdown() {
        System.out.println("Shutting down the logger.");
        try {
            join();
        } catch (InterruptedException ignored) {

        }
    }
}
