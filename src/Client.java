import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        int portNumber = 9001;
        if (args.length == 0) {
            System.out.printf("Port number is not specified. Using default one: %d\n", portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]);
            System.out.printf("Using port number: %d\n", portNumber);
        }

        Socket socket;
        try {
            socket = new Socket("127.0.0.1", portNumber);
        } catch (IOException e) {
            System.out.println("Cannot connect to Server. Please, try again");
            return;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            System.out.println("Welcome to the Public Chat Application");

            Scanner in = new Scanner(System.in);

            System.out.print("Enter your Nickname: ");
            String username = in.next();
            outputStream.writeUTF(username);

            System.out.println("Connecting to the chat...");

            MessageSend ms = new MessageSend(outputStream);
            MessageReceive mr = new MessageReceive(inputStream);
            Thread sender = new Thread(ms);
            Thread receiver = new Thread(mr);

            System.out.print("======== Welcome to Chat ========\n");
            System.out.println("–– type \\exit to exit from chat and close the application. \n");

            sender.start();
            receiver.start();

            sender.join();
            receiver.join();

        } catch (IOException e) {
            System.out.println("Can't communicate with Server. Connection was lost");
        } catch (InterruptedException e) {

        }
    }
}
