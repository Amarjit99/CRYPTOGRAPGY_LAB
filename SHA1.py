import hashlib

def get_sha1_hash(text):
    # Create SHA-1 hash object
    sha1_hash = hashlib.sha1()
    # Update the hash object with the bytes of the input text
    sha1_hash.update(text.encode())
    # Return the hexadecimal digest
    return sha1_hash.hexdigest()

# Get user input
user_input = input("Enter text to hash: ")
sha1_digest = get_sha1_hash(user_input)

print(f"SHA-1 Digest: {sha1_digest}")
