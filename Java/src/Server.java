public class Server {

    public static void main(String[] args) {

        final ThreadPoolServer server = new ThreadPoolServer(8080);

        server.start();

        try {
            server.join();
            System.out.println("Completed shutdown.");
        } catch (InterruptedException e) {
            System.err.println("Interrupted before accept thread completed.");
            System.exit(1);
        }

    }
}