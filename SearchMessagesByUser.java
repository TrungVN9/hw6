import java.util.*;


// SearchMessagesByUser class - Iterator for filtering messages by user
class SearchMessagesByUser implements Iterator<Message> {
    private List<Message> messages;
    private User userToSearchWith;
    private int currentIndex;
    private Message nextMessage;
    
    public SearchMessagesByUser(List<Message> messages, User userToSearchWith) {
        this.messages = new ArrayList<>(messages); // Create a copy for iteration
        this.userToSearchWith = userToSearchWith;
        this.currentIndex = 0;
        this.nextMessage = findNextMessage();
    }
    
    private Message findNextMessage() {
        while (currentIndex < messages.size()) {
            Message message = messages.get(currentIndex);
            currentIndex++;
            
            // Check if message is from or to the user we're searching for
            if (message.getSender().equals(userToSearchWith.getUsername()) ||
                message.getRecipients().contains(userToSearchWith.getUsername())) {
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
        if (!hasNext()) {
            throw new NoSuchElementException("No more messages from/to " + userToSearchWith.getUsername());
        }
        
        Message messageToReturn = nextMessage;
        nextMessage = findNextMessage();
        return messageToReturn;
    }
}
