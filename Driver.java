// Driver class to demonstrate the features
public class Driver {
    public static void main(String[] args) {
        System.out.println("=== Chat Application Demo ===\n");
        
        // Create the mediator (chat server)
        ChatServer chatServer = new ChatServer();
        
        // Create three users
        User alice = new User("Alice", chatServer);
        User bob = new User("Bob", chatServer);
        User charlie = new User("Charlie", chatServer);
        
        System.out.println("\n--- TEST PART 1: Basic messaging ---");

        // Alice sends a message to Bob
        String recipientNameBob = "Bob";
        System.out.println("[SENDING] " + alice.getUsername() + " is sending message to " + recipientNameBob);
        alice.sendMessage("Hi Bob! How are you?", recipientNameBob);
        
        // Bob sends a message to Alice and Charlie
        System.out.println("[SENDING] " + bob.getUsername() + " is sending message to Alice, Charlie" );
        bob.sendMessage("Hello everyone! I'm doing great!", "Alice", "Charlie");
        
        // Charlie sends a message to Alice
        System.out.println("[SENDING] " + charlie.getUsername() + " is sending message");
        charlie.sendMessage("Hey Alice, want to grab lunch?", "Alice");
        
        System.out.println("\n--- TEST PART 2: Viewing chat histories ---");
        alice.displayChatHistory();
        bob.displayChatHistory();
        charlie.displayChatHistory();
        
        System.out.println("\n--- TEST PART 3: Undo last message ---");
        // Charlie wants to undo his last message
        charlie.undoLastMessage();
        
        // Check Alice's history to confirm the message was removed
        alice.displayChatHistory();
        
        System.out.println("\n--- TEST PART 4: Blocking users ---");
        // Bob blocks Alice
        bob.blockUser("Alice");
        
        // Alice tries to send a message to Bob (should be blocked)
        System.out.println("Alice tries to send a message to Bob");
        alice.sendMessage("Bob, did you get my previous message?", "Bob");
        
        // Check Bob's history (should not have the blocked message)
        bob.displayChatHistory();
        
        System.out.println("\n--- TEST PART 5: Unblocking and sending again ---");
        // Bob unblocks Alice
        bob.unblockUser("Alice");
        
        // Alice sends another message (should go through now)
        alice.sendMessage("Great! We can chat again!", "Bob");
        bob.displayChatHistory();
        
        System.out.println("\n--- TEST PART 6: Multiple recipients and undo ---");
        // Alice sends a message to both Bob and Charlie
        alice.sendMessage("Go Boba at 3 PM today!", "Bob", "Charlie");
        
        // Display everyone's history
        bob.displayChatHistory();
        charlie.displayChatHistory();
        
        // Alice realizes she made a mistake and undoes the message
        alice.undoLastMessage();
        
        // Verify the message was removed from both Bob's and Charlie's history
        System.out.println("After undo:");
        bob.displayChatHistory();
        charlie.displayChatHistory();
        
        System.out.println("\n--- TEST PART 7: Iterating messages by user ---");
        // Alice wants to see her message history with only Bob
        alice.displayMessagesByUser(bob);
        alice.displayMessagesByUser(charlie);
        bob.displayMessagesByUser(charlie);
    }
}