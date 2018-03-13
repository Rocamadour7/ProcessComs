#include <iostream>
#include <fstream>
#include <vector>

#include "ResponseHandler.hpp"

using namespace std;

void ResponseHandler::log(Protocol *protocol) {
    string log = protocol->getLog();
    char type = protocol->getType();
    string filename;
    ofstream logFile;

    switch (type) {
        case 'U':
            filename = "updateLog.csv";
            break;
        case 'R':
            filename = "requestLog.csv";
            break;
        default:break;
    }

    logFile.open(filename, ios::app);
    logFile << log << endl;
    logFile.close();
}

string ResponseHandler::response(string sensorName) {
    string filename = "updateLog.csv";
    ifstream logFile;
    logFile.open(filename);

    string line;
    string last;
    while(getline(logFile, line)) {
        vector<string> values = split(line);
        if (values.at(0) == sensorName) {
            last = line;
        }
    }

    logFile.close();

    return last;
}