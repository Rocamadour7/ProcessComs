public class RequestProtocol extends Protocol {

    private StringBuffer observerName = new StringBuffer(8);

    RequestProtocol(String message) {
        String type = String.valueOf(message.charAt(0));
        String observerName = message.substring(1, 9);
        String sensorName = message.substring(9, 17);
        String checksum = message.substring(17, 21);

        setType(type);
        setObserverName(observerName);
        setSensorName(sensorName);
        setChecksum(checksum);
    }

    private void setObserverName(String observerName) {
        this.observerName = new StringBuffer(observerName);
    }

    private String getObserverName() {
        return observerName.toString();
    }

    @Override
    protected Boolean checksumIsValid() {
        int ascii_type = sumChunk(type);
        int ascii_observerName = sumChunk(observerName);
        int ascii_sensorName = sumChunk(sensorName);
        int total = ascii_type + ascii_observerName + ascii_sensorName;

        return total == getChecksum();
    }

    @Override
    String getLog() {
        return getObserverName() + "," + getSensorName();
    }
}
