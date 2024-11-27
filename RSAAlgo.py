import base64

def gcd(a, b):
    """Compute the GCD of a and b."""
    while b:
        a, b = b, a % b
    return a

def extended_gcd(a, b):
    """Extended Euclidean Algorithm to find the GCD and the coefficients."""
    if a == 0:
        return b, 0, 1
    gcd, x1, y1 = extended_gcd(b % a, a)
    x = y1 - (b // a) * x1
    y = x1
    return gcd, x, y

def mod_inverse(e, phi):
    """Compute the modular inverse of e mod phi."""
    gcd, x, _ = extended_gcd(e, phi)
    if gcd != 1:
        raise Exception("Modular inverse does not exist.")
    else:
        return x % phi

def get_prime_input(prompt):
    """Get a prime number input from the user with error handling."""
    while True:
        try:
            num = int(input(prompt))
            if num > 1:
                return num
            else:
                print("Please enter a number greater than 1.")
        except ValueError:
            print("Invalid input. Please enter a valid integer.")

def generate_keys():
    """Generate RSA public and private keys based on user input."""
    # Get user inputs for RSA parameters with validation
    p = get_prime_input("Enter prime number p: ")
    q = get_prime_input("Enter prime number q: ")
    n = p * q
    phi = (p - 1) * (q - 1)

    # Get public exponent e with error handling
    while True:
        try:
            e = int(input("Enter public exponent e (commonly 65537): "))
            if 1 < e < phi and gcd(e, phi) == 1:
                break
            else:
                print("Please enter an integer that is co-prime with φ(n) and between 1 and φ(n).")
        except ValueError:
            print("Invalid input. Please enter a valid integer.")

    # Calculate private key d
    d = mod_inverse(e, phi)

    # Display and return keys
    print(f"\nModulus n: {n}")
    print(f"Public exponent e: {e}")
    print(f"Private exponent d: {d}")
    print(f"\nPublic Key: (e={e}, n={n})")
    print(f"Private Key: (d={d}, n={n})")
    return (e, n), (d, n)

def encrypt_message(message, n, e):
    """Encrypt message using RSA public key."""
    message_int = int.from_bytes(message.encode(), 'big')
    encrypted_int = pow(message_int, e, n)
    return encrypted_int

def decrypt_message(encrypted_int, n, d):
    """Decrypt message using RSA private key."""
    decrypted_int = pow(encrypted_int, d, n)
    decrypted_message = decrypted_int.to_bytes((decrypted_int.bit_length() + 7) // 8, 'big').decode()
    return decrypted_message

# Generate RSA keys
public_key, private_key = generate_keys()
e, n = public_key
d, _ = private_key

# User inputs the message to be encrypted
message = input("Enter a message to encrypt: ")

# Encrypt the message
encrypted_message = encrypt_message(message, n, e)

# Encode the encrypted message in base64 to ensure safe transmission or display
encoded_encrypted_message = base64.b64encode(encrypted_message.to_bytes((encrypted_message.bit_length() + 7) // 8, 'big'))
print("Encrypted message (base64):", encoded_encrypted_message.decode())

# Decrypt the message to verify
decrypted_message = decrypt_message(encrypted_message, n, d)
print("Decrypted message:", decrypted_message)
