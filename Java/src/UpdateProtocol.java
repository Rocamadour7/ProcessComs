public class UpdateProtocol extends Protocol {

    StringBuffer data = new StringBuffer(8);
    StringBuffer time = new StringBuffer(6);
    StringBuffer date = new StringBuffer(8);

    @Override
    protected StringBuffer calculateChecksum(StringBuffer... properties) {
        return new StringBuffer();
    }
}
