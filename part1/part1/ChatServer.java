package part1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private List<User> users = new ArrayList<>();
    private Map<User, List<User>> blockedUsers = new HashMap<>();
    private Map<User, Message> lastMessageSent = new HashMap<>();

    public void registerUser(User user) {
        users.add(user);
        blockedUsers.put(user, new ArrayList<>());
    }

    public void unregisterUser(User user) {
        users.remove(user);
        blockedUsers.remove(user);
        lastMessageSent.remove(user);
    }

    public void sendMessage(Message message) {
        lastMessageSent.put(message.getSender(), message);
        for (User recipient : message.getRecipients()) {
            if (!isBlocked(recipient, message.getSender())) {
                recipient.receiveMessage(message);
            }
        }
    }

    public void undoLastMessage(User user, MessageMemento memento) {
        Message messageToUndo = null;
        for (Message message : lastMessageSent.values()) {
            if (message.getSender().equals(user) && message.getContent().equals(memento.getContent()) && message.getTimestamp().equals(memento.getTimestamp())) {
                messageToUndo = message;
                break;
            }
        }

        if (messageToUndo != null) {
            for (User recipient : messageToUndo.getRecipients()) {
                recipient.removeMessageFromHistory(messageToUndo);
            }
            user.removeMessageFromHistory(messageToUndo);
            lastMessageSent.remove(user);
            System.out.println(user.getUsername() + " undid the last message: " + messageToUndo.getContent());
        }
    }

    public void blockUser(User blocker, User toBlock) {
        blockedUsers.get(blocker).add(toBlock);
    }

    private boolean isBlocked(User recipient, User sender) {
        return blockedUsers.containsKey(recipient) && blockedUsers.get(recipient).contains(sender);
    }
}
