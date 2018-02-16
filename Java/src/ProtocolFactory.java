public class ProtocolFactory {

    public Protocol getProtocol(String message) {
        if ((message.length() == 35) && (message.charAt(0) == 'U')) {
            return new UpdateProtocol(message);
        } else if ((message.length() == 21) && (message.charAt(0) == 'R')) {
            return new RequestProtocol(message);
        } else {
            return null;
        }
    }
}