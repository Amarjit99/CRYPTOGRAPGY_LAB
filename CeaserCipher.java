import java.util.*;

public class CeaserCipher {
    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    // Encrypt method
    public static String encrypt(String message, int shiftKey) {
        message = message.toLowerCase();
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if (Character.isLetter(ch)) {
                int charPosition = ALPHA.indexOf(ch);
                int keyVal = (shiftKey + charPosition) % 26;
                if (keyVal < 0) {
                    keyVal += 26; // Handle negative shifts
                }
                char replaceVal = ALPHA.charAt(keyVal);
                cipherText.append(replaceVal);
            } else {
                cipherText.append(ch); // Handle non-alphabetic characters
            }
        }
        return cipherText.toString();
    }
    // Decrypt method
    public static String decrypt(String message, int shiftKey) {
        return encrypt(message, -shiftKey); // Decrypt by shifting in the opposite direction
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n*** Caesar Cipher Menu ***");
            System.out.println("1. Encrypt a Message");
            System.out.println("2. Decrypt a Message");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;
            // Ensure valid integer input for the menu choice
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid choice (1, 2, or 3).");
                    sc.next(); // Clear the invalid input
                }
            }
            switch (choice) {
                case 1:
                    // Encryption
                    System.out.print("Enter the String for Encryption: ");
                    String messageToEncrypt = sc.nextLine();
                    System.out.print("Enter the Key: ");
                    int encryptKey = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                    String encryptedMessage = encrypt(messageToEncrypt, encryptKey);
                    System.out.println("Encrypted Message: " + encryptedMessage);
                    break;
                case 2:
                    // Decryption
                    System.out.print("Enter the Encrypted String for Decryption: ");
                    String messageToDecrypt = sc.nextLine();
                    System.out.print("Enter the Key: ");
                    int decryptKey = sc.nextInt();
                    sc.nextLine(); // Consume newline character
                    String decryptedMessage = decrypt(messageToDecrypt, decryptKey);
                    System.out.println("Decrypted Message: " + decryptedMessage);
                    break;
                case 3:
                    // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    sc.close();
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }
}
