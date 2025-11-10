import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Message class representing a chat message
class Message {
    private String sender;
    private List<String> recipients;
    private LocalDateTime timestamp;
    private String content;
    
    public Message(String sender, List<String> recipients, String content) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.timestamp = LocalDateTime.now();
        this.content = content;
    }
    
    public String getSender() {
        return sender;
    }
    
    public List<String> getRecipients() {
        return new ArrayList<>(recipients);
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getContent() {
        return content;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("[%s] Sending Message From %s: %s", 
            timestamp.format(formatter), sender, content);
    }
}
