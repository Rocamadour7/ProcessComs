class ProtocolFactory {

    Protocol getProtocol(String message) {
        Protocol protocol;

        if ((message.length() == 35) && (message.charAt(0) == 'U')) {
            protocol = new UpdateProtocol(message);
        } else if ((message.length() == 21) && (message.charAt(0) == 'R')) {
            protocol = new RequestProtocol(message);
        } else {
            protocol = null;
        }

        if (protocol != null && isProtocolValid(protocol)) {
            return protocol;
        } else {
            return null;
        }
    }

    private boolean isProtocolValid(Protocol protocol) {
        return protocol.checksumIsValid();
    }
}
