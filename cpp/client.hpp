#pragma once

#include <cerrno>
#include <netdb.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <fstream>
#include <iostream>
#include <string>
#include <locale>
#include <vector>

#include "protocol.hpp"

using namespace std;

class Client {
public:
    Client(string host, uint16_t port);
    ~Client();

    void run();

private:
    virtual void create();
    virtual void close_socket();
    void echo();
    bool send_request(string);
    bool get_response();
    vector<string> processProtocolData(string *type);
    void fillRequestProtocol(vector<string> *parts);
    void fillUpdateProtocol(vector<string> *parts);
    void processUserInput(string message, vector<string> *parts, int min, int max);
    bool validateInput(string input, int min, int max);
    string addWhitespaceIfNeeded(string input, int limit);
    int calculateChecksum(vector<string> *parts);
    string createMessage(vector<string> *parts, int checksum);

    string host_;
    uint16_t port_;
    int server_;
    int buflen_;
    char* buf_;


};
