#pragma once

#include <cerrno>
#include <netinet/in.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>

#include <string>

using namespace std;

class Server {
public:
    explicit Server(int port);
    ~Server();

    void run();
    
private:
    void create();
    void close_socket();
    void serve();
    void handle(int);
    string get_request(int);
    bool send_response(int, string);

    uint16_t port_;
    int server_;
    int buflen_;
    char* buf_;
};
