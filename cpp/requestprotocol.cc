#include "requestprotocol.hpp"

RequestProtocol::RequestProtocol(string message) {
    type = message.substr(0, 1);
    observerName = message.substr(1, 8);
    sensorName = message.substr(9, 8);
    checksum = stoi(message.substr(17, 4));
}

bool RequestProtocol::checksumIsValid() {
    int ascii_type = sumChunk(type);
    int ascii_observerName = sumChunk(observerName);
    int ascii_sensorName = sumChunk(sensorName);
    int total = ascii_type + ascii_observerName + ascii_sensorName;

    return total == checksum;
}

string RequestProtocol::getLog() {
    return trim(observerName) + ", " + trim(sensorName);
}

