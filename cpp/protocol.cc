#include "protocol.hpp"

int Protocol::sumChunk(string chunk) {
    int total = 0;

    for (char letter: chunk) {
        total += (int)letter;
    }

    return total;
}