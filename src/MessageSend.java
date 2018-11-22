import java.io.*;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MessageSend implements Runnable {

    private ObjectOutputStream outputStream;

    public MessageSend(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
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
    }
}
