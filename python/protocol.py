def protocol_from_message(message: str):
    message = message.strip()
    if message.startswith('R') or message.startswith('U'):
        protocols = {21: RequestProtocol, 35: UpdateProtocol}
        if len(message) in protocols:
            protocol = protocols.get(len(message))(message)
            if checksum_is_valid(protocol):
                return protocol
    return None

def calculate_chunk_value(chunk):
    total = 0

    for letter in chunk:
        total += ord(letter)

    return total

def checksum_is_valid(protocol):
    protocol_parts = protocol.__dict__.copy()
    del protocol_parts['checksum']
    checksum_calculated = 0

    for part in protocol_parts.values():
        checksum_calculated += calculate_chunk_value(part)

    return checksum_calculated == int(protocol.checksum)
    

class RequestProtocol:
    def __init__(self, message):
        self.type = ''
        self.observer_name = ''
        self.sensor_name = ''
        self.checksum = ''
        self._process_message(message)
    
    def _process_message(self, message):
        self.type = message[0]
        self.observer_name = message[1:9]
        self.sensor_name = message[9:17]
        self.checksum = message[17:21]
    
    @property
    def log(self):
        return ', '.join([self.observer_name.strip(), self.sensor_name.strip()])
    

class UpdateProtocol:
    def __init__(self, message):
        self.type = ''
        self.sensor_name = ''
        self.data = ''
        self.time = ''
        self.date = ''
        self.checksum = ''
        self._process_message(message)
    
    def _process_message(self, message):
        self.type = message[0]
        self.sensor_name = message[1:9]
        self.data = message[9:17]
        self.time = message[17:23]
        self.date = message[23:31]
        self.checksum = message[31:35]
    
    @property
    def log(self):
        return ', '.join([self.sensor_name.strip(), self.data.strip(), self.time.strip(), self.date.strip()])
