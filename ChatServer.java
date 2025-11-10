import java.util.*;


// ChatServer class - Mediator that manages all communication
class ChatServer {
    private Map<String, User> users;
    private Map<String, Set<String>> blockedUsers; // key: username, value: set of blocked usernames
    
    public ChatServer() {
        this.users = new HashMap<>();
        this.blockedUsers = new HashMap<>();
    }
    
    public void registerUser(User user) {
        users.put(user.getUsername(), user);
        blockedUsers.put(user.getUsername(), new HashSet<>());
        System.out.println("User " + user.getUsername() + " registered with chat server.");
    }
    
    public void unregisterUser(String username) {
        users.remove(username);
        blockedUsers.remove(username);
        System.out.println("User " + username + " unregistered from chat server.");
    }
    
    public void sendMessage(User sender, String content, List<String> recipientNames) {
        Message message = new Message(sender.getUsername(), recipientNames, content);
        
        System.out.println("\n[SERVER] Processing message from " + sender.getUsername());
        
        for (String recipientName : recipientNames) {
            User recipient = users.get(recipientName);
            
            if (recipient == null) {
                System.out.println("[SERVER] User " + recipientName + " not found.");
                continue;
            }
            
            // Check if recipient has blocked the sender
            if (isBlocked(recipientName, sender.getUsername())) {
                System.out.println("[SERVER] Message blocked: " + recipientName + 
                    " has blocked " + sender.getUsername());
                continue;
            }
            
            recipient.receiveMessage(message);
        }
    }
    
    public void undoMessage(User sender, MessageMemento memento) {
        System.out.println("[SERVER] Processing undo request from " + sender.getUsername());
        
        // Remove message from recipients' histories
        for (String recipientName : memento.getRecipients()) {
            User recipient = users.get(recipientName);
            if (recipient != null) {
                ChatHistory history = recipient.getChatHistory();
                history.removeMessage(memento, sender.getUsername());
                
                System.out.println("[SERVER] Message removed from " + recipientName + "'s history.");
            }
        }
    }
    
    public void blockUser(String username, String blockedUsername) {
        Set<String> blocked = blockedUsers.get(username);
        if (blocked != null) {
            blocked.add(blockedUsername);
            System.out.println("[SERVER] " + username + " blocked " + blockedUsername);
        }
    }
    
    public void unblockUser(String username, String blockedUsername) {
        Set<String> blocked = blockedUsers.get(username);
        if (blocked != null) {
            blocked.remove(blockedUsername);
            System.out.println("[SERVER] " + username + " unblocked " + blockedUsername);
        }
    }
    
    private boolean isBlocked(String username, String potentialBlockedUser) {
        Set<String> blocked = blockedUsers.get(username);
        return blocked != null && blocked.contains(potentialBlockedUser);
    }
}
