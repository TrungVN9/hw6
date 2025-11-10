import java.util.*;

// ChatHistory class - maintains history of messages for a user
class ChatHistory implements IterableByUser {
    private List<Message> messages;
    private Stack<MessageMemento> sentMessageHistory;
    
    public ChatHistory() {
        this.messages = new ArrayList<>();
        this.sentMessageHistory = new Stack<>();
    }
    
    public void addMessage(Message message) {
        messages.add(message);
    }
    
    public void saveMessageMemento(MessageMemento memento) {
        sentMessageHistory.push(memento);
    }
    
    public MessageMemento getLastMessageMemento() {
        if (!sentMessageHistory.isEmpty()) {
            return sentMessageHistory.pop();
        }
        return null;
    }
    
    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }
    
    public Message getLastMessage() {
        if (!messages.isEmpty()) {
            return messages.get(messages.size() - 1);
        }
        return null;
    }
    
    public void removeLastMessage() {
        if (!messages.isEmpty()) {
            messages.remove(messages.size() - 1);
        }
    }
    
    public void removeMessage(MessageMemento memento, String senderUsername) {
        messages.removeIf(msg -> 
            msg.getSender().equals(senderUsername) &&
            msg.getContent().equals(memento.getContent()) &&
            Math.abs(msg.getTimestamp().compareTo(memento.getTimestamp())) < 2
        );
    }
    
    // Iterator pattern implementation
    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessagesByUser(messages, userToSearchWith);
    }
}
