import socket
import struct

def receive_multicast_message(group_address, port):
    # Create the datagram socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    # Allow multiple sockets to use the same PORT number
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    # Bind to the server address
    sock.bind(('', port))

    # Tell the operating system to add the socket to the multicast group
    # on all interfaces.
    group = socket.inet_aton(group_address)
    mreq = struct.pack('4sL', group, socket.INADDR_ANY)
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, mreq)

    print(f"Listening for messages on group: {group_address}")
    while True:
        data, _ = sock.recvfrom(1024)
        print(f"Received message: {data.decode('utf-8')}")

def main():
    port = 4446
    group_addresses = ["230.0.0.1", "230.0.0.2"]

    for group_address in group_addresses:
        receive_multicast_message(group_address, port)

if __name__ == "__main__":
    main()
