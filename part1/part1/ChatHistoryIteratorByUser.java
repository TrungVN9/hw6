package part1;

import java.util.Iterator;
import java.util.Stack;

public class ChatHistoryIteratorByUser implements Iterator<Message> {
    private Iterator<Message> messageIterator;
    private User userToFilterBy;
    private Message nextMessage;

    public ChatHistoryIteratorByUser(ChatHistory chatHistory, User userToFilterBy) {
        this.messageIterator = chatHistory.iterator();
        this.userToFilterBy = userToFilterBy;
        this.nextMessage = findNext();
    }

    private Message findNext() {
        while (messageIterator.hasNext()) {
            Message message = messageIterator.next();
            if (message.getSender().equals(userToFilterBy) || message.getRecipients().contains(userToFilterBy)) {
                return message;
            }
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return nextMessage != null;
    }

    @Override
    public Message next() {
        Message currentMessage = nextMessage;
        nextMessage = findNext();
        return currentMessage;
    }
}
