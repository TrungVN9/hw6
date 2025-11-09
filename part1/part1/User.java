package part1;

import java.util.Iterator;
import java.util.List;

public class User {
    private String username;
    private ChatServer chatServer;
    private ChatHistory chatHistory;
    private MessageMemento lastMessageMemento;

    public User(String username, ChatServer chatServer) {
        this.username = username;
        this.chatServer = chatServer;
        this.chatHistory = new ChatHistory();
        chatServer.registerUser(this);
    }

    public void sendMessage(List<User> recipients, String content) {
        Message message = new Message(this, recipients, content);
        this.lastMessageMemento = message.saveToMemento();
        chatServer.sendMessage(message);
        chatHistory.addMessage(message);
    }

    public void receiveMessage(Message message) {
        System.out.println(username + " received a message from " + message.getSender().getUsername() + ": " + message.getContent());
        chatHistory.addMessage(message);
    }

    public void undoLastMessage() {
        chatServer.undoLastMessage(this, lastMessageMemento);
    }

    public void removeMessageFromHistory(Message message) {
        chatHistory.removeMessage(message);
    }

    public String getUsername() {
        return username;
    }

    public void viewChatHistory() {
        System.out.println("--- " + username + "'s Chat History ---");
        chatHistory.viewChatHistory();
    }

    public Iterator<Message> iterator(User user) {
        return chatHistory.iterator(user);
    }
}