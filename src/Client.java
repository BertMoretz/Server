import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            System.out.println("Connection with the Server can not be established. Please, try again");
            return;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

            System.out.println("Welcome to the Chat System!");
            System.out.println("Type \\auth for authorization using existing account\n" +
                    "Type \\reg for the registration");


            Scanner in = new Scanner(System.in);

            System.out.print("Your action: ");
            String username = in.next();
            outputStream.writeUTF(username);

            System.out.println("Redirecting you to the chat...");


            String messageText = "";

            while (!"\\exit".equals(messageText)) {
                messageText = in.nextLine();

                //message = new Message(messageText, chatID, user);

                try {
                    outputStream.writeObject(messageText);
                    outputStream.flush();
                    //send(message, outputStream);
                    System.out.println(messageText);

//                    if ("\\settings".equals(messageText)) {
//                        synchronized (monitor) {
//                            monitor.setStatus(Status.SETTINGS_ON);
//                            Thread settingThread = new Thread(settings);
//                            settingThread.start();
//                            try {
//                                settingThread.join();
//                            } catch (InterruptedException e) {
//                                System.out.println("An error occurred while getting to the Settings Module");
//                            }
//                            monitor.setStatus(Status.SETTINGS_OFF);
//                            System.out.printf("======== Chat %d ========\n", chatID);
//                        }
//                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while sending your message. " +
                            "Please, try to send it again.");
                }
            }
            //paste code here


            /* part of the settings task */
            //messageSender.setSettings(new Settings(inputStream, outputStream, user));

            //Thread sender = new Thread(messageSender);
            //Thread receiver = new Thread(messageReceiver);

            System.out.printf("======== Chat ========\n");
            System.out.println("> type \\settings to change your registration information\n" +
                    "> type \\exit to close the application");

//            sender.start();
//            receiver.start();
//
//            sender.join();
//            receiver.join();
        } catch (IOException e) {
            System.out.println("An error occurred while communication with Server. Connection is lost");
        }
    }
}
