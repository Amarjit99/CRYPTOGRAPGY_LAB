import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class TCPServer {
   public static void main(String[] args) {
       try {
           ServerSocket serverSocket = new ServerSocket(9999);
           System.out.println("\nServer: Waiting for client to connect...");
           Socket clientSocket = serverSocket.accept();
           System.out.println("Server: Client connected!");
           BufferedReader reader = new BufferedReader(new 
InputStreamReader(clientSocket.getInputStream()));
           String clientData = reader.readLine();
           System.out.println("\nServer: Received the Message from client: " + clientData);
           reader.close();
           clientSocket.close();
           serverSocket.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
