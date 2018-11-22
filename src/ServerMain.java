import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerMain {
    public static void main(String[] args) {
        int port = 9001;

        if (args.length != 1) {
            System.out.printf("Using default port number: %d\n", port);
        } else {
            port = Integer.valueOf(args[0]);
        }
        System.out.printf("Using port number: %d\n", port);

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.printf("An error occurred: \n\t%s\n" +
                    "Please, try again!", e.getMessage());
            return;
        }

        Chat chat = new Chat();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New User Accepted  (" + socket + ") \n");

                Connection connection = new Connection(socket, chat);

                Thread connectionThread = new Thread(connection);
                //connectionThread.setName("Connection-");
                connectionThread.start();

            } catch (IOException e) {
                System.out.printf("An error occurred! Please, try again! \n\t%s\n", e.getMessage());
            }
        }
    }
}
