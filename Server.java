import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(4999);
        System.out.println("Server waiting for client on port 4999...");
        
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket.getInetAddress());
        
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        // Start a thread to listen to client messages
        new Thread(() -> {
            try {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    System.out.println("Client-Side: " + receivedMessage);
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
            out.println(userInput);  // Send the server operator's input to the client
        }
        userInputReader.close();
        clientSocket.close();
        serverSocket.close();
    }
}
