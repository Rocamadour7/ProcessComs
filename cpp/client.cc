#include <cstdlib>
#include <iostream>

#include "client.hpp"

using namespace std;

int main(int argc, char **argv) {
    uint16_t port = 8080;
    string host = "localhost";

    Client client = Client(host, port);
    client.run();
}

