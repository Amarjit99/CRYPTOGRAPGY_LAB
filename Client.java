import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4999);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Start a thread to listen to server messages
        new Thread(() -> {
            try {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    System.out.println("Server-Side: " + receivedMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        while (true) {
            System.out.print("Write your message here: ");  // Prompt the user for input
            userInput = userInputReader.readLine();
            if (userInput == null || userInput.equalsIgnoreCase("exit")) {
                break;  // Exit the loop if the user inputs "exit" or null (e.g., EOF)
            }
            out.println(userInput);  // Send the user's input to the server
        }
        userInputReader.close();
        socket.close();
    }
}
