public abstract class Protocol {
    StringBuffer type = new StringBuffer(1);
    StringBuffer sensorName = new StringBuffer(8);
    private StringBuffer checksum = new StringBuffer(4);

    void setType(String type) {
        this.type = new StringBuffer(type);
    }

    String getType() {
        return type.toString();
    }

    void setSensorName(String sensorName) {
        this.sensorName = new StringBuffer(sensorName);
    }

    String getSensorName() {
        return sensorName.toString();
    }

    void setChecksum(String checksum) {
        this.checksum = new StringBuffer(checksum);
    }

    int getChecksum() {
        return Integer.valueOf(checksum.toString());
    }

    protected abstract Boolean checksumIsValid();

    int sumChunk(StringBuffer chunk) {
        int total = 0;

        char [] _chunk = chunk.toString().toCharArray();

        for (char letter: _chunk) {
            total += (int)letter;
        }

        return total;
    }

    abstract String getLog();
}
