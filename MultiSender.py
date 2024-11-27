import socket
import struct

def send_multicast_message(message, group_address, port):
    # Create the datagram socket
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
    # Set the time-to-live for messages to 1 so they do not go past the local network segment
    sock.setsockopt(socket.IPPROTO_IP, socket.IP_MULTICAST_TTL, 1)
    # Send the message
    sock.sendto(message.encode('utf-8'), (group_address, port))
    print(f"Message sent to group: {group_address}")

def main():
    group_map = {
        "Group1": "230.0.0.1",
        "Group2": "230.0.0.2"
    }
    port = 4446

    while True:
        print("1. Send Message to Group1")
        print("2. Send Message to Group2")
        print("3. Send Message to Both Groups")
        print("4. Exit")

        choice = int(input("Enter your choice: "))

        if choice in [1, 2, 3]:
            message = input("Enter message: ")

            if choice == 1 or choice == 3:
                send_multicast_message(message, group_map["Group1"], port)
            if choice == 2 or choice == 3:
                send_multicast_message(message, group_map["Group2"], port)
        elif choice == 4:
            print("Exiting...")
            break

if __name__ == "__main__":
    main()
