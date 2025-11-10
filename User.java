import java.time.LocalDateTime;
import java.util.*;

// User class - represents a chat user
class User implements IterableByUser {
    private String username;
    private ChatServer mediator;
    private ChatHistory chatHistory;
    
    public User(String username, ChatServer mediator) {
        this.username = username;
        this.mediator = mediator;
        this.chatHistory = new ChatHistory();
        mediator.registerUser(this);
    }
    
    public String getUsername() {
        return username;
    }
    
    public ChatHistory getChatHistory() {
        return chatHistory;
    }
    
    // Send message through mediator
    public void sendMessage(String content, String... recipientNames) {
        List<String> recipients = Arrays.asList(recipientNames);
        
        // Create memento before sending
        MessageMemento memento = new MessageMemento(content, LocalDateTime.now(), recipients);
        chatHistory.saveMessageMemento(memento);
        
        // Send message through mediator
        mediator.sendMessage(this, content, recipients);
    }
    
    // Receive message from mediator
    public void receiveMessage(Message message) {
        chatHistory.addMessage(message);
        System.out.println("[RECEIVED] " + username + " received: " + message);
    }
    
    // Undo last sent message using Memento pattern
    public void undoLastMessage() {
        MessageMemento memento = chatHistory.getLastMessageMemento();
        if (memento != null) {
            System.out.println("[UNDO] " + username + " is undoing message: \"" + memento.getContent() + "\"");
            mediator.undoMessage(this, memento);
        } else {
            System.out.println(username + " has no messages to undo.");
        }
    }
    
    // Block a specific user
    public void blockUser(String blockedUsername) {
        mediator.blockUser(this.username, blockedUsername);
        System.out.println("[BLOCKED] " + username + " has blocked " + blockedUsername);
    }
    
    // Unblock a specific user
    public void unblockUser(String blockedUsername) {
        mediator.unblockUser(this.username, blockedUsername);
        System.out.println("[UNBLOCKED] " + username + " has unblocked " + blockedUsername);
    }
    
    // Display chat history
    public void displayChatHistory() {
        System.out.println("\n=== Chat History for " + username + " ===");
        List<Message> messages = chatHistory.getMessages();
        if (messages.isEmpty()) {
            System.out.println("No messages in history.");
        } else {
            for (Message msg : messages) {
                System.out.println(msg);
            }
        }
        System.out.println("=================================\n");
    }
    
    // wrapper to call ChatHistory's iterator method
    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return chatHistory.iterator(userToSearchWith);
    }
    
    // Display messages by a specific user
    public void displayMessagesByUser(User otherUser) {
        System.out.println("\n=== Messages involving " + username + " and " + otherUser.getUsername() + " ===");
        Iterator<Message> iterator = this.iterator(otherUser);
        
        if (!iterator.hasNext()) {
            System.out.println("No messages found.");
        } else {
            while (iterator.hasNext()) {
                Message message = iterator.next();
                System.out.println(message);
            }
        }
        System.out.println("=================================================\n");
    }
}
