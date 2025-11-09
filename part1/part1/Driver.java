package part1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Driver {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        User user1 = new User("Alice", chatServer);
        User user2 = new User("Bob", chatServer);
        User user3 = new User("Charlie", chatServer);

        System.out.println("--- Sending messages ---");
        user1.sendMessage(Arrays.asList(user2, user3), "Hello everyone!");
        user2.sendMessage(Collections.singletonList(user1), "Hi Alice!");
        user1.sendMessage(Collections.singletonList(user3), "Hi Charlie!");


        System.out.println("\n--- Alice's view of chat history ---");
        user1.viewChatHistory();

        System.out.println("\n--- Charlie's view of chat history ---");
        user3.viewChatHistory();

        System.out.println("\n--- Undoing last message ---");
        user1.undoLastMessage();

        System.out.println("\n--- Alice's view of chat history after undo ---");
        user1.viewChatHistory();

        System.out.println("\n--- Charlie's view of chat history after undo ---");
        user3.viewChatHistory();

        System.out.println("\n--- Blocking messages ---");
        chatServer.blockUser(user1, user2);
        user2.sendMessage(Collections.singletonList(user1), "You won't see this message, Alice!");
        user3.sendMessage(Collections.singletonList(user1), "Alice, can you see this?");

        System.out.println("\n--- Alice's view of chat history after blocking Bob ---");
        user1.viewChatHistory();

        System.out.println("\n--- Alice's message history with Charlie ---");
        Iterator<Message> aliceCharlieIterator = user1.iterator(user3);
        while (aliceCharlieIterator.hasNext()) {
            Message message = aliceCharlieIterator.next();
            System.out.println("From: " + message.getSender().getUsername() + ", To: " + message.getRecipients().get(0).getUsername() + ", Content: " + message.getContent());
        }
    }
}