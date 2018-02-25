import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Request/Update (R/U): ");
        String str = scanner.nextLine();

        if (str.toUpperCase().equals("R")) {
            System.out.print("Observer Name: ");
            String observerName = scanner.nextLine();
            // Validate observer name

            System.out.print("Sensor Name: ");
            String sensorName = scanner.nextLine();
            // Validate sensor name
            // Calculate checksum

        } else if (str.toUpperCase().equals("U")) {
            System.out.print("Sensor Name: ");
            String sensorName = scanner.nextLine();
            // Validate sensor name

            System.out.print("Data: ");
            String data = scanner.nextLine();
            // Validate data

            System.out.print("Time: ");
            String time = scanner.nextLine();
            // Validate time

            System.out.print("Date: ");
            String date = scanner.nextLine();
            // Validate date
            // Calculate checksum

        }
    }
}
