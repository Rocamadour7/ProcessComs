#include "updateprotocol.hpp"

UpdateProtocol::UpdateProtocol(string message) {
    type = message.substr(0, 1);
    sensorName = message.substr(1, 8);
    data = message.substr(9, 8);
    time = message.substr(17, 6);
    date = message.substr(23, 8);
    checksum = stoi(message.substr(31, 4));
}

bool UpdateProtocol::checksumIsValid() {
    int ascii_type = sumChunk(type);
    int ascii_sensorName = sumChunk(sensorName);
    int ascii_data = sumChunk(data);
    int ascii_time = sumChunk(time);
    int ascii_date = sumChunk(date);
    int total = ascii_type + ascii_sensorName + ascii_data + ascii_time + ascii_date;

    return total == checksum;
}

string UpdateProtocol::getLog() {
    return trim(sensorName) + ", " + trim(data) + ", " + trim(time) + ", " + trim(date);
}

