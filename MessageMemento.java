import java.time.LocalDateTime;
import java.util.*;

// MessageMemento class - stores snapshot of a message for undo functionality
class MessageMemento {
    private String content;
    private LocalDateTime timestamp;
    private List<String> recipients;
    
    public MessageMemento(String content, LocalDateTime timestamp, List<String> recipients) {
        this.content = content;
        this.timestamp = timestamp;
        this.recipients = new ArrayList<>(recipients);
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public List<String> getRecipients() {
        return new ArrayList<>(recipients);
    }
}
