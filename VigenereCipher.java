import java.util.Scanner;

public class VigenereCipher {
    // Method to repeat the key to match the length of the text
    public static String repeatKey(String text, String key) {
        StringBuilder repeatedKey = new StringBuilder();
        int keyIndex = 0;
        // Repeat key until it matches the length of the text
        for (int i = 0; i < text.length(); i++) {
            // Append each character from the key
            repeatedKey.append(key.charAt(keyIndex));
            // Move to the next character in the key
            keyIndex++;
            // If the key is fully used, start from the beginning
            if (keyIndex == key.length()) {
                keyIndex = 0;
            }
        }return repeatedKey.toString();
    }
    // Method to encrypt the text
    public static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        text = text.toUpperCase(); // Convert the text to uppercase
        key = key.toUpperCase();   // Convert the key to uppercase

        // Repeat the key to match the length of the text
        String repeatedKey = repeatKey(text, key);

        // Encrypt each character of the text
        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = repeatedKey.charAt(i);

            // Calculate the new encrypted character
            char encryptedChar = (char) (((textChar - 'A') + (keyChar - 'A')) % 26 + 'A');
            encryptedText.append(encryptedChar);
        }return encryptedText.toString();
    }
    // Method to decrypt the text
    public static String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();
        text = text.toUpperCase(); // Convert the text to uppercase
        key = key.toUpperCase();   // Convert the key to uppercase

        // Repeat the key to match the length of the text
        String repeatedKey = repeatKey(text, key);

        // Decrypt each character of the text
        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = repeatedKey.charAt(i);
            // Calculate the new decrypted character
            char decryptedChar = (char) (((textChar - keyChar + 26) % 26) + 'A');
            decryptedText.append(decryptedChar);
        }

        return decryptedText.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the text to encrypt or decrypt from the user
        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

        // Get the key from the user
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        // Ask the user if they want to encrypt or decrypt
        System.out.print("Do you want to (e)ncrypt or (d)ecrypt? ");
        char choice = scanner.nextLine().toLowerCase().charAt(0);

        // Perform encryption or decryption based on the user's choice
        if (choice == 'e') {
            String encryptedText = encrypt(text, key);
            System.out.println("Encrypted text: " + encryptedText);
        } else if (choice == 'd') {
            String decryptedText = decrypt(text, key);
            System.out.println("Decrypted text: " + decryptedText);
        } else {
            System.out.println("Invalid choice. Please enter 'e' to encrypt or 'd' to decrypt.");
        }
        scanner.close();
    }
}
