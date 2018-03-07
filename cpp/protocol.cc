#include "protocol.hpp"
#include "updateprotocol.hpp"
#include "requestprotocol.hpp"

Protocol* Protocol::getProtocol(string message) {
    Protocol *protocol;

    if ((message.length() == 35) && (message[0] == 'U')) {
        protocol =  new UpdateProtocol(message);
    } else if ((message.length() == 21) && (message[0] == 'R')) {
        protocol = new RequestProtocol(message);
    } else {
        protocol = NULL;
    }

    if ((protocol != NULL) && isProtocolValid(protocol)) {
        return protocol;
    } else {
        return NULL;
    }
}

static inline bool isProtocolValid(Protocol* protocol) {
    Protocol _protocol = *protocol;
    return _protocol.checksumIsValid();
}

int Protocol::sumChunk(string chunk) {
    int total = 0;

    for (char letter: chunk) {
        total += (int)letter;
    }

    return total;
}