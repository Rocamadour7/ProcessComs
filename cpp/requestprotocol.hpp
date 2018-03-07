#pragma once

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>

#include <string>

#include "protocol.hpp"

using namespace std;

class RequestProtocol : public Protocol {
public:
    RequestProtocol(string message);
    bool checksumIsValid();
    string getLog();
    
private:
    string observerName;
};
