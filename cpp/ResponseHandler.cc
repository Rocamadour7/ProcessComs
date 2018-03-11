#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>

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
    string last = nullptr;
    while(getline(logFile, line)) {
        vector<string> values = splitStringByCommas(line);
        if (values.at(0) == sensorName) {
            last = line;
        }
    }

    logFile.close();

    return last;
}

vector<string> ResponseHandler::splitStringByCommas(string str) {
    stringstream ss(str);
    vector<string> result;

    while( ss.good() )
    {
        string substring;
        getline( ss, substring, ',' );
        result.push_back( substring );
    }

    return result;
}