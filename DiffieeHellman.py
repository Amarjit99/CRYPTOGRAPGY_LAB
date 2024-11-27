from cryptography.hazmat.primitives.asymmetric import dh
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
import base64

# Function to get key size input from the user
def get_key_size():
    while True:
        try:
            key_size = int(input("Enter the key size for Diffie-Hellman (e.g., 1024, 2048, 4096): "))
            if key_size in [1024, 2048, 4096]:
                return key_size
            else:
                print("Invalid key size. Please choose 1024, 2048, or 4096.")
        except ValueError:
            print("Please enter a valid integer.")

# Get key size from the user
key_size = get_key_size()

# Step 1: Generate parameters for Diffie-Hellman
parameters = dh.generate_parameters(generator=2, key_size=key_size, backend=default_backend())

# Step 2: Alice generates her key pair
print("\nGenerating keys for Alice...")
alice_private_key = parameters.generate_private_key()
alice_public_key = alice_private_key.public_key()
print("Alice's Public Key:", base64.b64encode(alice_public_key.public_bytes(
    encoding=serialization.Encoding.PEM,
    format=serialization.PublicFormat.SubjectPublicKeyInfo
)).decode())

# Step 3: Bob generates his key pair
print("\nGenerating keys for Bob...")
bob_private_key = parameters.generate_private_key()
bob_public_key = bob_private_key.public_key()
print("Bob's Public Key:", base64.b64encode(bob_public_key.public_bytes(
    encoding=serialization.Encoding.PEM,
    format=serialization.PublicFormat.SubjectPublicKeyInfo
)).decode())

# Step 4: Alice and Bob exchange public keys (simulated here)

# Step 5: Alice generates the shared secret using Bob's public key
alice_shared_secret = alice_private_key.exchange(bob_public_key)

# Step 6: Bob generates the shared secret using Alice's public key
bob_shared_secret = bob_private_key.exchange(alice_public_key)

# Step 7: Display shared secrets
print("\nAlice's Shared Secret:", base64.b64encode(alice_shared_secret).decode())
print("Bob's Shared Secret:", base64.b64encode(bob_shared_secret).decode())

# Step 8: Verify if both shared secrets are equal
if alice_shared_secret == bob_shared_secret:
    print("\nShared secrets are equal. Key exchange successful!")
else:
    print("\nShared secrets are NOT equal. Key exchange failed.")
