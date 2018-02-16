public abstract class Protocol {
    protected StringBuffer type = new StringBuffer(1);
    protected StringBuffer sensorName = new StringBuffer(8);
    protected StringBuffer checksum = new StringBuffer(4);

    protected void setType(String type) {
        this.type = new StringBuffer(type);
    }

    String getType() {
        return type.toString();
    }

    protected void setSensorName(String sensorName) {
        this.sensorName = new StringBuffer(sensorName);
    }

    String getSensorName() {
        return sensorName.toString();
    }

    protected void setChecksum(String checksum) {
        this.checksum = new StringBuffer(checksum);
    }

    int getChecksum() {
        return Integer.valueOf(checksum.toString());
    }

    protected abstract StringBuffer calculateChecksum(StringBuffer... properties);
}