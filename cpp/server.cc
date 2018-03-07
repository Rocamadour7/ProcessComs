#include <cstdlib>

#include "server.hpp"

using namespace std;

int
main(int argc, char **argv)
{
    int option, port;

    port = 8080;

    Server server = Server(port);
    server.run();
}
