import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SHA1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\nEnter text to hash: ");
        String text = scanner.nextLine(); // Read user input

        String sha1Hash = getSHA1Hash(text);
        System.out.println("\nSHA-1 Digest: " + sha1Hash);

        scanner.close(); // Close the scanner to avoid resource leaks
    }

    public static String getSHA1Hash(String input) {
        try {
            // Create a SHA-1 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            // Perform hashing
            byte[] hashBytes = digest.digest(input.getBytes());
            // Convert byte array to hexadecimal format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }
}
