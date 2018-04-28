import asyncio
import socket
import concurrent.futures

import logger
from protocol import protocol_from_message

async def server(address, loop):
    with socket.socket() as sock:
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        sock.setblocking(False)
        sock.bind(address)
        sock.listen(5)

        while True:
            conn, _ = await loop.sock_accept(sock)
            loop.create_task(conn_handler(conn, loop))

async def conn_handler(conn, loop):
    with conn:
        data = await loop.sock_recv(conn, 1024)
        protocol = await client_handler(conn, data)
        if protocol:
            await logger.add_to_log_queue(protocol)
            if protocol.type == 'R':
                response = await logger.get_last_update(protocol.sensor_name)
                await loop.sock_sendall(conn, response.encode())

async def client_handler(conn, data):
    address, port = conn.getpeername()
    message = data.decode().strip()
    protocol = protocol_from_message(message)
    if protocol:
        print('{}:{}: {}'.format(address, port, message))
        return protocol

if __name__ == '__main__':
    loop = asyncio.get_event_loop()

    host = '127.0.0.1'
    port = 8888

    try:
        loop.create_task(logger._update_logger())
        loop.create_task(logger._request_logger())
        loop.run_until_complete(server(address=(host, port), loop=loop))
    except KeyboardInterrupt:
        pass
    
