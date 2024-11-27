import java.util.Scanner;

public class HillCipher {
    static int[][] keyMatrix;
    static int[][] inverseKeyMatrix;
    static int blockSize;
    // Function to find modular inverse of a number in mod 26
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    // Function to find determinant of the matrix
    public static int determinant(int[][] matrix, int n) {
        int det;
        if (n == 2) {
            det = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        } else {
            det = 0;
            for (int i = 0; i < n; i++) {
                det = det + (int) (Math.pow(-1, i) * matrix[0][i] * determinant(cofactor(matrix, 0, i), n - 1));
            }
        }
        return det;
    }

    // Function to find cofactor of matrix
    public static int[][] cofactor(int[][] matrix, int row, int col) {
        int[][] cofactorMatrix = new int[matrix.length - 1][matrix.length - 1];
        int p = 0, q = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == row) continue;
            for (int j = 0; j < matrix.length; j++) {
                if (j == col) continue;
                cofactorMatrix[p][q++] = matrix[i][j];
                if (q % (matrix.length - 1) == 0) {
                    p++;
                    q = 0;
                }
            }
        }
        return cofactorMatrix;
    }

    // Function to find inverse of the matrix in mod 26
    public static int[][] inverseMatrix(int[][] matrix, int n) {
        int det = determinant(matrix, n) % 26;
        det = (det + 26) % 26;
        int detInverse = modInverse(det, 26);
        int[][] adj = new int[n][n];
        int[][] invMatrix = new int[n][n];
        if (n == 2) {
            adj[0][0] = matrix[1][1];
            adj[0][1] = -matrix[0][1];
            adj[1][0] = -matrix[1][0];
            adj[1][1] = matrix[0][0];
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    adj[i][j] = (int) Math.pow(-1, i + j) * determinant(cofactor(matrix, i, j), n - 1);
                    adj[i][j] = adj[i][j] % 26;
                    if (adj[i][j] < 0) adj[i][j] += 26;
                }
            }
        }

        // Find the inverse matrix mod 26
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invMatrix[i][j] = (detInverse * adj[j][i]) % 26;
                if (invMatrix[i][j] < 0) invMatrix[i][j] += 26;
            }
        }
        return invMatrix;
    }

    // Function to multiply matrix with vector and return result mod 26
    public static int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int[] result = new int[blockSize];
        for (int i = 0; i < blockSize; i++) {
            result[i] = 0;
            for (int j = 0; j < blockSize; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] = result[i] % 26;
        }
        return result;
    }

    // Function to encrypt plaintext
    public static String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        while (plaintext.length() % blockSize != 0) {
            plaintext += "X";  // Padding with X
        }
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += blockSize) {
            int[] block = new int[blockSize];
            for (int j = 0; j < blockSize; j++) {
                block[j] = plaintext.charAt(i + j) - 'A';
            }
            int[] result = multiplyMatrixVector(keyMatrix, block);
            for (int r : result) {
                ciphertext.append((char) (r + 'A'));
            }
        }
        return ciphertext.toString();
    }

    // Function to decrypt ciphertext
    public static String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += blockSize) {
            int[] block = new int[blockSize];
            for (int j = 0; j < blockSize; j++) {
                block[j] = ciphertext.charAt(i + j) - 'A';
            }
            int[] result = multiplyMatrixVector(inverseKeyMatrix, block);
            for (int r : result) {
                plaintext.append((char) (r + 'A'));
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Input block size and key matrix
        System.out.print("Enter block size (e.g., 2 for 2x2 or 3 for 3x3 matrix): ");
        blockSize = sc.nextInt();
        sc.nextLine();  // Consume the leftover newline
        keyMatrix = new int[blockSize][blockSize];
        System.out.println("Enter the key matrix as a string (e.g., 'GYBNQKURP' for a 3x3 matrix):");
        String keyString = sc.nextLine().toUpperCase();
        // Convert key string into matrix form
        int index = 0;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                keyMatrix[i][j] = keyString.charAt(index++) - 'A';
            }
        }
        // Validate that the matrix has an inverse
        int det = determinant(keyMatrix, blockSize) % 26;
        if (det == 0 || modInverse(det, 26) == -1) {
            System.out.println("The key matrix is not invertible. Choose a different matrix.");
            return;
        }
        inverseKeyMatrix = inverseMatrix(keyMatrix, blockSize);

        // Input plaintext
        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine();
        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext);
        System.out.println("Encrypted text: " + ciphertext);
        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
