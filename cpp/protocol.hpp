#pragma once

#include <cerrno>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <sys/types.h>
#include <unistd.h>

#include <string>
#include "str_utils.cc"

using namespace std;

class Protocol {
public:
    virtual string getLog() = 0;
    virtual bool checksumIsValid() = 0;
    static Protocol* getProtocol(string message);

protected:
    int sumChunk(string chunk);

    string type;
    string sensorName;
    int checksum;
};
