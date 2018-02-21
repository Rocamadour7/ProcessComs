public class ServerTesting {
    public static void main(String[] args) {
        ThreadPooledServer server = new ThreadPooledServer();
        new Thread(server).start();
//        System.out.println("Stopping Server");
//        server.stop();
    }
}
