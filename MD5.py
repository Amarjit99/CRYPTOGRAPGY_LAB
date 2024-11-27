import hashlib

def generate_md5_hash(input_string):
    # Create an MD5 hash object
    md5_hash = hashlib.md5()

    # Update the hash object with the bytes of the input string
    md5_hash.update(input_string.encode('utf-8'))

    # Return the hexadecimal representation of the hash
    return md5_hash.hexdigest()

def main():
    # Prompt the user for input
    user_input = input("\nEnter text to hash with MD5: ")

    # Generate and display the MD5 hash
    md5_hash = generate_md5_hash(user_input)
    print(f'\nMD5 Hash of "{user_input}": {md5_hash}')

if __name__ == "__main__":
    main()
