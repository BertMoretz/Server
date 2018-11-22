import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

    Socket socket;

    Chat chat;

    public Connection (Socket socket, Chat chat) {
        this.socket = socket;
        this.chat = chat;
    }

    @Override
    public void run() {
        String username = "";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            username = inputStream.readUTF();

            System.out.println(username);

            User user = new User(chat.isEmpty() ? 1 : chat.usersLength() + 1, username);

            chat.addUser(user);
            chat.addOutputStream(outputStream);

            /* starting processing messages from the user */
            processMessage(inputStream, outputStream, user);

        } catch (IOException e) {
            System.out.printf("%s\tConnection with %s was lost.   Socket is closed\n",
                    Thread.currentThread().getName(), username);
        } catch (ClassNotFoundException e) {
            System.out.printf("%s\tAn error occurred while object was read. Class was not found in the packages\n",
                    Thread.currentThread().getName());
        }
    }

    private void processMessage(ObjectInputStream inputStream, ObjectOutputStream outputStream, User user) throws IOException, ClassNotFoundException {
        String message;
        while (true) {
            message = (String) inputStream.readObject();

            if ("\\exit".equals(message)) {
                outputStream.writeObject(message);
                outputStream.flush();
                chat.removeOutputStream(outputStream);
                chat.removeUser(user);
                System.out.printf("%s\tUser %s has left the Chat \n",
                        Thread.currentThread().getName(), user.getNickname());
                return;
            } else {
                System.out.printf("%s\tMessage from %s: %s\n",
                        Thread.currentThread().getName(), user.getNickname(), message);

                chat.sendMessage(message, outputStream);
            }
        }
    }
}
