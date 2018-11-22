import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Chat {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();

    public Chat () {
        //Chat created
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    public  void removeUser (User user) {
        users.remove(user);
    }

    public void  addOutputStream(ObjectOutputStream oos) {
        outputStreams.add(oos);
    }

    public void removeOutputStream (ObjectOutputStream oos) {
        outputStreams.remove(oos);
    }

    public int usersLength() {
        return users.size();
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public void sendMessage(String message, ObjectOutputStream outputStream) {
        ArrayList<ObjectOutputStream> closedStreams = new ArrayList<>();
        for (ObjectOutputStream oos : outputStreams) {
            if (oos != outputStream) {
                try {
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException e) {
                    // Socket was closed for that stream
                    closedStreams.add(oos);
                }
            }
        }
        outputStreams.removeAll(closedStreams);
    }
}
