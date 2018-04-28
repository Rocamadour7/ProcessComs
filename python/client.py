import socket

from protocol import calculate_chunk_value

def _process_request_protocol():
    observer_name = input_value('Observer Name: ', 8)
    sensor_name = input_value('Sensor Name: ', 8)

    message = 'R' + observer_name + sensor_name
    message = _add_checksum(message)
    return message

def _process_update_protocol():
    sensor_name = input_value('Sensor Name: ', 8)
    data = input_value('Data: ', 8)
    time = input_value('Time (HHMMSS): ', 6)
    date = input_value('Date (YYYYMMDD): ', 8)

    message = 'U' + sensor_name + data + time + date
    message = _add_checksum(message)
    return message

def _add_checksum(message):
    checksum = calculate_chunk_value(message)
    message += '{:0>4}'.format(checksum)
    return message

_protocol_types = {
    'R': _process_request_protocol,
    'U': _process_update_protocol
}

def input_valid(_input, max_len):
    return len(_input) <= max_len

def input_value(message, max_len):
    while True:
        value = input(message)
        if input_valid(value, max_len):
            while len(value) < max_len:
                value += ' '
            return value

def input_type():
    while True:
        protocol_type = input('Type of Protocol (R/U): ')
        protocol_type = protocol_type.upper()
        if protocol_type in _protocol_types:
            return protocol_type

def process_protocol(protocol_type):
    return _protocol_types.get(protocol_type)()

if __name__ == '__main__':
    protocol_type = input_type()
    message = process_protocol(protocol_type)
    
    with socket.socket() as sock:
        sock.connect(('localhost', 8888))
        message += '\n'
        sock.send(message.encode())
        while True:
            response = sock.recv(1024)
            if not response:
                break
            print(response.decode().strip())

    
