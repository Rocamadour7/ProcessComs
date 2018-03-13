#include <utility>

#include "client.hpp"

Client::Client(string host, uint16_t port) {
    host_ = move(host);
    port_ = port;
    buflen_ = 1024;
    buf_ = new char[buflen_+1];
}

Client::~Client() = default;

void Client::run() {
    create();
    echo();
}

void Client::create() {
    struct sockaddr_in server_addr{};

    struct hostent *hostEntry;
    hostEntry = gethostbyname(host_.c_str());
    if (!hostEntry) {
        cout << "No such host name: " << host_ << endl;
        exit(-1);
    }

    memset(&server_addr,0,sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port_);
    memcpy(&server_addr.sin_addr, hostEntry->h_addr_list[0], static_cast<size_t>(hostEntry->h_length));

    server_ = socket(PF_INET,SOCK_STREAM,0);
    if (!server_) {
        perror("socket");
        exit(-1);
    }

    if (connect(server_,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
        perror("connect");
        exit(-1);
    }
}

void Client::close_socket() {
    close(server_);
}

void Client::echo() {
    string type;

    do {
        cout << "Request/Update (R/U): ";
        getline(cin, type);
        locale loc;
        type = toupper(type[0], loc);
    } while ((type != "R") && (type != "U"));

    vector<string> parts = processProtocolData(&type);
    int checksum = calculateChecksum(&parts);
    string message = createMessage(&parts, checksum);

    send_request(message);

    if (type == "R") {
        get_response();
    }

    close_socket();
}

vector<string> Client::processProtocolData(string *type) {
    vector<string> parts;
    parts.push_back(*type);

    if (*type == "R") {
        fillRequestProtocol(&parts);
    } else if (*type == "U") {
        fillUpdateProtocol(&parts);
    }

    return parts;
}

void Client::fillRequestProtocol(vector<string> *parts) {
    processUserInput("Observer Name: ", parts, 4, 8);
    processUserInput("Sensor Name: ", parts, 4, 8);
}

void Client::fillUpdateProtocol(vector<string> *parts) {
    processUserInput("Sensor Name: ", parts, 4, 8);
    processUserInput("Data: ", parts, 1, 8);
    processUserInput("Time: ", parts, 6, 6);
    processUserInput("Date: ", parts, 8, 8);
}

void Client::processUserInput(string message, vector<string> *parts, int min, int max) {
    string input;

    do {
        cout << message;
        getline(cin, input);
    } while (!(validateInput(input, min, max)));

    input = addWhitespaceIfNeeded(input, max);
    parts->push_back(input);
}

bool Client::validateInput(string input, int min, int max) {
    return input.length() <= max && input.length() >= min && input.length() != 0;
}

string Client::addWhitespaceIfNeeded(string input, int limit) {
    while (input.length() != limit) {
        input.append(" ");
    }

    return input;
}

int Client::calculateChecksum(vector<string> *parts) {
    int total = 0;

    for (auto &part : *parts) {
        for (char letter: part) {
            total += (int)letter;
        }
    }

    return total;
}

string Client::createMessage(vector<string> *parts, int checksum) {
    parts->push_back(to_string(checksum));
    string message;

    for (auto &part : *parts) {
        message.append(part);
    }
    message.append("\n");

    return message;
}

bool Client::send_request(string request) {
    const char* ptr = request.c_str();
    unsigned long nleft = request.length();
    ssize_t nwritten;

    while (nleft) {
        if ((nwritten = send(server_, ptr, nleft, 0)) < 0) {
            if (errno == EINTR) {
                continue;
            } else {
                return false;
            }
        } else if (nwritten == 0) {
            return false;
        }
        nleft -= nwritten;
        ptr += nwritten;
    }

    return true;
}

bool Client::get_response() {
    string response;

    while (response.find('\n') == string::npos) {
        ssize_t nread = recv(server_, buf_, 1024, 0);

        if (nread < 0) {
            if (errno == EINTR)
                continue;
            else
                return false;
        } else if (nread == 0) {
            return false;
        }

        response.append(buf_, static_cast<unsigned long>(nread));
    }

    cout << response;

    return true;
}
