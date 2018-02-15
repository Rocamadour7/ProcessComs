# Process Communication

This is a class project actually, that is going to be build across the semester. It is going to be built using at least 5 programming languages.

![Architecture](images/001.png)

It consist on communicating processes simulating different clients and a server, also using a pair of custom protocols, one for requests and one for updates. Also the server must be logging every action on CSV files.

#### Update Protocol
![Update Protocol](images/002.png)
* Type: 1-byte
* Sensor Name: 8-bytes
* Data: 8-bytes
* Time: 6-bytes
* Date: 8-bytes
* Checksum: 4-bytes

#### Request Protocol
![Request Protocol](images/003.png)
* Type: 1-byte
* Observer Name: 8-bytes
* Sensor Name: 8-bytes
* Checksum: 4-bytes
