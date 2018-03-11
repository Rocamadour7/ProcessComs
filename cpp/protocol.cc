#include "protocol.hpp"
#include "updateprotocol.hpp"
#include "requestprotocol.hpp"

static inline bool isProtocolValid(Protocol* protocol) ;

Protocol* Protocol::getProtocol(string message) {
    Protocol *protocol;

    if ((message.length() == 35) && (message[0] == 'U')) {
        protocol =  new UpdateProtocol(message);
    } else if ((message.length() == 21) && (message[0] == 'R')) {
        protocol = new RequestProtocol(message);
    } else {
        protocol = nullptr;
    }

    if ((protocol != nullptr) && isProtocolValid(protocol)) {
        return protocol;
    } else {
        return nullptr;
    }
}

static inline bool isProtocolValid(Protocol* protocol) {
    return protocol->checksumIsValid();
}

int Protocol::sumChunk(string chunk) {
    int total = 0;

    for (char letter: chunk) {
        total += (int)letter;
    }

    return total;
}

char Protocol::getType() {
    return type[0];
}

string Protocol::getSensorName() {
    return sensorName;
}