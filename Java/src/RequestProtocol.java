public class RequestProtocol extends Protocol {

    StringBuffer observerName = new StringBuffer(8);

    @Override
    protected StringBuffer calculateChecksum(StringBuffer... properties) {
        return null;
    }
}
