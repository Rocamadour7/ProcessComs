import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        Scanner scanner = new Scanner(System.in);

        String type;
        do {
            System.out.print("Request/Update (R/U): ");
            type = scanner.nextLine();
        } while (!validateInput(type, 1, 1));

        ArrayList<String> parts = processProtocolData(type, scanner);
        int checksum = calculateChecksum(parts);
        String message = createMessage(parts, checksum);

        try {
            sendToServer(host, port, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> processProtocolData(String type, Scanner scanner) {
        ArrayList<String> parts = new ArrayList<>();
        type = type.toUpperCase();
        parts.add(type);

        if (type.equals("R")) {
            fillRequestProtocol(scanner, parts);
        } else if (type.equals("U")) {
            fillUpdateProtocol(scanner, parts);
        }

        return parts;
    }

    private static void fillRequestProtocol(Scanner scanner, ArrayList<String> parts) {
        processUserInput("Observer Name: ", scanner, parts, 4, 8);
        processUserInput("Sensor Name: ", scanner, parts, 4, 8);
    }

    private static void fillUpdateProtocol(Scanner scanner, ArrayList<String> parts) {
        processUserInput("Sensor Name: ", scanner, parts, 4, 8);
        processUserInput("Data: ", scanner, parts, 1, 8); // Validate double
        processUserInput("Time: ", scanner, parts, 6, 6);
        processUserInput("Date: ", scanner, parts, 8, 8);
    }

    private static void processUserInput(String message, Scanner scanner, ArrayList<String> parts, int min, int max) {
        String input;

        do {
            System.out.print(message);
            input = scanner.nextLine();
        } while (!validateInput(input, min, max));

        input = addWhitespaceIfNeeded(input, max);
        parts.add(input);

    }

    private static boolean validateInput(String input, int min, int max) {
        return input.length() <= max && input.length() >= min && input.length() != 0;
    }

    private static String addWhitespaceIfNeeded(String input, int limit) {
        StringBuilder inputBuilder = new StringBuilder(input);

        while (inputBuilder.length() != limit) {
            inputBuilder.append(' ');
        }

        input = inputBuilder.toString();

        return input;
    }

    private static int calculateChecksum(ArrayList<String> parts) {
        int total = 0;

        for (String part: parts) {
            for (char letter: part.toCharArray()) {
                total += (int)letter;
            }
        }

        return total;
    }

    private static void sendToServer(String host, int port, String message) throws IOException {
        Socket socket = new Socket(host, port);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.write(message.getBytes());

        if (message.charAt(0) == 'R') {
            listenToServer(socket);
        }

        dataOutputStream.close();
        socket.close();
    }

    private static void listenToServer(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createMessage(ArrayList<String> parts, Integer checksum) {
        StringBuilder message = new StringBuilder();
        parts.add(checksum.toString());

        for (String part: parts) {
            message.append(part);
        }
        message.append("\n");

        return message.toString();
    }
}
