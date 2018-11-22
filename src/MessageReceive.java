import java.io.*;
import java.io.ObjectInputStream;

public class MessageReceive implements Runnable {

    private ObjectInputStream inputStream;

    public MessageReceive(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = (String) inputStream.readObject();
                if ("\\exit".equals(message)) {
                    return;
                }
                System.out.println(message);
            } catch (EOFException e) {
                System.out.println("Connection with the Server is lost.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    System.out.println("An error occurred");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while receiving the message.");
            } catch (ClassNotFoundException e) {
                System.out.println("An error occurred. Class Message is not found. Message was not received.");
            }
        }
    }
}
