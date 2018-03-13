#include <algorithm>
#include <string>
#include <vector>
#include <sstream>

using namespace std;

static inline string &ltrim(string &s) {
    s.erase(s.begin(), find_if(s.begin(), s.end(),
            not1(ptr_fun<int, int>(isspace))));
    return s;
}

static inline string &rtrim(string &s) {
    s.erase(find_if(s.rbegin(), s.rend(),
            not1(ptr_fun<int, int>(isspace))).base(), s.end());
    return s;
}

static inline string &trim(string &s) {
    return ltrim(rtrim(s));
}

static inline vector<string> split(string &s) {
    vector<string> result;
    stringstream ss(s);
    string token;

    while (getline(ss, token, ',')) {
        result.push_back(trim(token));
    }

    return result;
}