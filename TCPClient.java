import java.io.*;
import java.net.Socket;
public class TCPClient {
   public static void main(String[] args) {
       try {

           Socket clientSocket = new Socket("localhost", 9999);
           BufferedReader userInputReader = new BufferedReader(new 
           InputStreamReader(System.in));
           
           System.out.print("\nClient: Enter a message to send to the server: ");
           String userInput = userInputReader.readLine();
           BufferedWriter writer = new BufferedWriter(new 
           OutputStreamWriter(clientSocket.getOutputStream()));
           writer.write(userInput);
           writer.newLine();
           writer.flush();
           System.out.println("\nClient: Sent message to server: " + userInput);
           userInputReader.close();
           writer.close();
           clientSocket.close();
       } 
    catch (IOException e) {
        e.printStackTrace();
       }
   }
}
