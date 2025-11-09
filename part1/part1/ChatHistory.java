package part1;

import java.util.Iterator;
import java.util.Stack;

public class ChatHistory implements Iterable<Message> {
    private Stack<Message> history = new Stack<>();

    public void addMessage(Message message) {
        history.push(message);
    }

    public Message getLastMessage() {
        if (!history.isEmpty()) {
            return history.peek();
        }
        return null;
    }

    public Message removeLastMessage() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }

    public void removeMessage(Message messageToRemove) {
        history.remove(messageToRemove);
    }


    public void viewChatHistory() {
        for (Message message : history) {
            System.out.println("[" + message.getTimestamp() + "] " + message.getSender().getUsername() + ": " + message.getContent());
        }
    }

    @Override
    public Iterator<Message> iterator() {
        return history.iterator();
    }

    public Iterator<Message> iterator(User user) {
        return new ChatHistoryIteratorByUser(this, user);
    }
}