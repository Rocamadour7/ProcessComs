#pragma once

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>

#include <string>
#include "str_utils.cc"

using namespace std;

class Protocol {
public:
    virtual string getLog();
    virtual bool checksumIsValid();
    static Protocol* getProtocol(string message);

protected:
    int sumChunk(string chunk);

    string type;
    string sensorName;
    int checksum;
};
