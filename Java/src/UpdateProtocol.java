public class UpdateProtocol extends Protocol {

    private StringBuffer data = new StringBuffer(8);
    private StringBuffer time = new StringBuffer(6);
    private StringBuffer date = new StringBuffer(8);

    UpdateProtocol(String message) {
        String type = String.valueOf(message.charAt(0));
        String sensorName = message.substring(1, 9);
        String data = message.substring(9, 17);
        String time = message.substring(17, 23);
        String date = message.substring(23, 31);
        String checksum = message.substring(31, 35);

        setType(type);
        setSensorName(sensorName);
        setData(data);
        setTime(time);
        setDate(date);
        setChecksum(checksum);
    }

    private void setData(String data) {
        this.data = new StringBuffer(data);
    }

    private String getData() {
        return data.toString();
    }

    private void setTime(String time) {
        this.time = new StringBuffer(time);
    }

    private String getTime() {
        return time.toString();
    }

    private void setDate(String date) {
        this.date = new StringBuffer(date);
    }

    private String getDate() {
        return date.toString();
    }

    @Override
    protected Boolean checksumIsValid() {
        int ascii_type = sumChunk(type);
        int ascii_sensorName = sumChunk(sensorName);
        int ascii_data = sumChunk(data);
        int ascii_time = sumChunk(time);
        int ascii_date = sumChunk(date);
        int total = ascii_type + ascii_sensorName + ascii_data + ascii_time + ascii_date;

        return total == getChecksum();
    }

    @Override
    String getLog() {
        return getSensorName().trim() + ", " + getData().trim() + ", " + getTime().trim() + ", " + getDate().trim();
    }
}
