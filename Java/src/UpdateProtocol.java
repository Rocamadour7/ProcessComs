public class UpdateProtocol extends Protocol {

    StringBuffer data = new StringBuffer(8);
    StringBuffer time = new StringBuffer(6);
    StringBuffer date = new StringBuffer(8);

    UpdateProtocol(String message) {
        String type = String.valueOf(message.charAt(0));
        String sensorName = message.substring(1, 8);
        String data = message.substring(9, 16);
        String time = message.substring(17, 22);
        String date = message.substring(23, 30);
        String checksum = message.substring(31, 34);

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

    public String getData() {
        return data.toString();
    }

    private void setTime(String time) {
        this.time = new StringBuffer(time);
    }

    public String getTime() {
        return time.toString();
    }

    private void setDate(String date) {
        this.date = new StringBuffer(date);
    }

    public String getDate() {
        return date.toString();
    }

    @Override
    protected StringBuffer calculateChecksum(StringBuffer... properties) {
        return new StringBuffer();
    }
}
