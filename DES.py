from Crypto.Cipher import DES
from Crypto.Random import get_random_bytes
import base64

# Padding function to make sure the plaintext length is a multiple of 8 (required by DES)
def pad(text):
    while len(text) % 8 != 0:
        text += ' '
    return text

# Encrypt function
def encrypt(plaintext, key):
    des = DES.new(key, DES.MODE_ECB)  # Create a new DES cipher in ECB mode
    padded_text = pad(plaintext)      # Pad plaintext to make its length a multiple of 8
    encrypted_text = des.encrypt(padded_text.encode('utf-8'))  # Encrypt the padded plaintext
    return base64.b64encode(encrypted_text).decode('utf-8')  # Return base64 encoded encrypted text

# Decrypt function
def decrypt(ciphertext, key):
    des = DES.new(key, DES.MODE_ECB)  # Create a new DES cipher in ECB mode
    encrypted_text = base64.b64decode(ciphertext)  # Decode base64 to get encrypted bytes
    decrypted_text = des.decrypt(encrypted_text).decode('utf-8')  # Decrypt the ciphertext
    return decrypted_text.rstrip()  # Return decrypted text without padding

def main():
    key = get_random_bytes(8)  # Generate a random 8-byte (64-bit) key for DES
    print("Key for DES (in base64):", base64.b64encode(key).decode('utf-8'))

    while True:
        # Menu
        print("\nMenu:")
        print("1. Encryption")
        print("2. Decryption")
        print("3. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:  # Encryption
            plaintext = input("Enter the plaintext: ")
            encrypted_text = encrypt(plaintext, key)
            print("Encrypted text (in base64):", encrypted_text)

        elif choice == 2:  # Decryption
            ciphertext = input("Enter the ciphertext (in base64): ")
            try:
                decrypted_text = decrypt(ciphertext, key)
                print("Decrypted text:", decrypted_text)
            except Exception as e:
                print("Decryption failed. Make sure the ciphertext is valid.")
        
        elif choice == 3:  # Exit
            print("Exiting...")
            break

        else:
            print("Invalid choice. Please select 1, 2, or 3.")

if __name__ == "__main__":
    main()
