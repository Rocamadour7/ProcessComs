#include <vector>

#include "protocol.hpp"

using namespace std;

class ResponseHandler {
public:
    static void log(Protocol *protocol);
    static string response(string sensorName);
};