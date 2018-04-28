import asyncio

from protocol import RequestProtocol, UpdateProtocol

requestQueue = asyncio.Queue()
updateQueue = asyncio.Queue()

async def add_to_log_queue(protocol):
    queues = {
        RequestProtocol: requestQueue,
        UpdateProtocol: updateQueue
    }

    queue_to_push = queues.get(type(protocol))
    await queue_to_push.put(protocol.log)

async def get_last_update(sensor_name):
    sensor_name = sensor_name.strip()
    with open('updateLog.csv') as f:
        for line in reversed(list(f)):
            file_sensor, _, _, _ = line.split(', ')
            if sensor_name == file_sensor:
                return line
        else:
            return ''

async def _update_logger():
    while True:
        with open('updateLog.csv', 'a') as f:
            message = await updateQueue.get()
            f.write(message + '\n')

async def _request_logger():
    while True:
        with open('requestLog.csv', 'a') as f:
            message = await requestQueue.get()
            f.write(message + '\n')


