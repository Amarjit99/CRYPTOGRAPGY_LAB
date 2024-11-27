from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import rsa, padding
from cryptography.hazmat.primitives.serialization import load_pem_private_key, load_pem_public_key
import os

def generate_keys():
    """Generate a public/private key pair."""
    private_key = rsa.generate_private_key(
        public_exponent=65537,
        key_size=2048,
        backend=default_backend()
    )
    public_key = private_key.public_key()
    return private_key, public_key

def save_key(key, filename):
    """Save a key (private or public) to a file."""
    pem = key.private_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PrivateFormat.PKCS8,
        encryption_algorithm=serialization.NoEncryption()
    ) if isinstance(key, rsa.RSAPrivateKey) else key.public_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PublicFormat.SubjectPublicKeyInfo
    )
    with open(filename, 'wb') as f:
        f.write(pem)

def load_key(filename, key_type='private'):
    """Load a key (private or public) from a file."""
    with open(filename, 'rb') as f:
        key_data = f.read()
    return load_pem_private_key(key_data, None, default_backend()) if key_type == 'private' else load_pem_public_key(key_data, default_backend())

def sign_message(private_key, message):
    """Sign a message using the private key."""
    signature = private_key.sign(
        message.encode(),
        padding.PSS(
            mgf=padding.MGF1(hashes.SHA256()),
            salt_length=padding.PSS.MAX_LENGTH
        ),
        hashes.SHA256()
    )
    return signature

def verify_signature(public_key, message, signature):
    """Verify a message signature using the public key."""
    try:
        public_key.verify(
            signature,
            message.encode(),
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return True
    except Exception:
        return False

def main():
    # User menu
    while True:
        print("\nDigital Signature Options:")
        print("1. Generate a new key pair")
        print("2. Sign a message")
        print("3. Verify a signature")
        print("4. Exit")
        choice = input("Select an option: ")
        
        if choice == '1':
            # Key Generation
            private_key, public_key = generate_keys()
            priv_filename = input("Enter a filename to save the private key (e.g., private_key.pem): ")
            pub_filename = input("Enter a filename to save the public key (e.g., public_key.pem): ")
            save_key(private_key, priv_filename)
            save_key(public_key, pub_filename)
            print("Keys generated and saved successfully.")
        
        elif choice == '2':
            # Signing a Message
            priv_filename = input("Enter the filename of the private key to use for signing: ")
            if not os.path.exists(priv_filename):
                print("Private key file not found.")
                continue
            private_key = load_key(priv_filename, 'private')
            
            message = input("Enter the message to sign: ")
            signature = sign_message(private_key, message)
            print("Message signed successfully.")
            
            sig_filename = input("Enter a filename to save the signature (e.g., signature.sig): ")
            with open(sig_filename, 'wb') as f:
                f.write(signature)
            print("Signature saved successfully.")
        
        elif choice == '3':
            # Verifying a Signature
            pub_filename = input("Enter the filename of the public key to use for verification: ")
            if not os.path.exists(pub_filename):
                print("Public key file not found.")
                continue
            public_key = load_key(pub_filename, 'public')
            
            message = input("Enter the original message to verify: ")
            sig_filename = input("Enter the filename of the signature file: ")
            if not os.path.exists(sig_filename):
                print("Signature file not found.")
                continue
            with open(sig_filename, 'rb') as f:
                signature = f.read()
            
            is_valid = verify_signature(public_key, message, signature)
            if is_valid:
                print("The signature is valid.")
            else:
                print("The signature is invalid.")
        
        elif choice == '4':
            print("Exiting program.")
            break
        else:
            print("Invalid option. Please try again.")

if __name__ == "__main__":
    main()
