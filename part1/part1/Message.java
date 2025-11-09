package part1;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private User sender;
    private List<User> recipients;
    private LocalDateTime timestamp;
    private String content;

    public Message(User sender, List<User> recipients, String content) {
        this.sender = sender;
        this.recipients = recipients;
        this.timestamp = LocalDateTime.now();
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public List<User> getRecipients() {
        return recipients;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public MessageMemento saveToMemento() {
        return new MessageMemento(this.content, this.timestamp);
    }

    public void restoreFromMemento(MessageMemento memento) {
        this.content = memento.getContent();
        this.timestamp = memento.getTimestamp();
    }
}
