public class RequestProtocol extends Protocol {

    StringBuffer observerName = new StringBuffer(8);

    RequestProtocol(String message) {
        String type = String.valueOf(message.charAt(0));
        String observerName = message.substring(1, 8);
        String sensorName = message.substring(9, 16);
        String checksum = message.substring(17, 20);

        setType(type);
        setObserverName(observerName);
        setSensorName(sensorName);
        setChecksum(checksum);
    }

    private void setObserverName(String observerName) {
        this.observerName = new StringBuffer(observerName);
    }

    public String getObserverName() {
        return observerName.toString();
    }

    @Override
    protected StringBuffer calculateChecksum(StringBuffer... properties) {
        return null;
    }
}
