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

            try {
                outputStream.writeObject(messageText);
                outputStream.flush();
                //System.out.println(messageText);
            } catch (IOException e) {
                System.out.println("Cannot send the message. Please, try again.");
            }
        }
    }
}
