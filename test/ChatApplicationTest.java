package test;
import org.junit.jupiter.api.*;

import ChatHistory;
import ChatServer;
import Message;
import MessageMemento;
import User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ChatApplicationTest {
    private ChatServer chatServer;
    private User alice;
    private User bob;
    private User charlie;

    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        chatServer = new ChatServer();
        alice = new User("Alice", chatServer);
        bob = new User("Bob", chatServer);
        charlie = new User("Charlie", chatServer);
    }

    @Test
    @DisplayName("Test Message creation and getters")
    void testMessageCreation() {
        List<String> recipients = Arrays.asList("Bob", "Charlie");
        Message message = new Message("Alice", recipients, "Hello everyone!");

        assertEquals("Alice", message.getSender());
        assertEquals("Hello everyone!", message.getContent());
        assertTrue(message.getRecipients().contains("Bob"));
        assertTrue(message.getRecipients().contains("Charlie"));
        assertNotNull(message.getTimestamp());
    }

    @Test
    @DisplayName("Test Message toString format")
    void testMessageToString() {
        Message message = new Message("Alice", Arrays.asList("Bob"), "Test message");
        String messageString = message.toString();

        assertTrue(messageString.contains("Alice"));
        assertTrue(messageString.contains("Test message"));
    }

    @Test
    @DisplayName("Test undo last message removes from recipient history")
    void testUndoLastMessage() {
        alice.sendMessage("Message to undo", "Bob");
        assertEquals(1, bob.getChatHistory().getMessages().size());

        alice.undoLastMessage();

        // Message should be removed from Bob's history
        assertEquals(0, bob.getChatHistory().getMessages().size());
    }
    @Test
    @DisplayName("Test multiple undo operations")
    void testMultipleUndoOperations() {
        alice.sendMessage("Message 1", "Bob");
        alice.sendMessage("Message 2", "Bob");
        alice.sendMessage("Message 3", "Bob");

        assertEquals(3, bob.getChatHistory().getMessages().size());

        alice.undoLastMessage(); // Undo Message 3
        assertEquals(2, bob.getChatHistory().getMessages().size());

        alice.undoLastMessage(); // Undo Message 2
        assertEquals(1, bob.getChatHistory().getMessages().size());

        alice.undoLastMessage(); // Undo Message 1
        assertEquals(0, bob.getChatHistory().getMessages().size());
    }

    @Test
    @DisplayName("Test undo with multiple recipients")
    void testUndoMessageMultipleRecipients() {
        alice.sendMessage("Broadcast message", "Bob", "Charlie");

        assertEquals(1, bob.getChatHistory().getMessages().size());
        assertEquals(1, charlie.getChatHistory().getMessages().size());

        alice.undoLastMessage();

        assertEquals(0, bob.getChatHistory().getMessages().size());
        assertEquals(0, charlie.getChatHistory().getMessages().size());
    }

    @Test
    @DisplayName("Test MessageMemento stores message state")
    void testMessageMementoCreation() {
        List<String> recipients = Arrays.asList("Bob");
        MessageMemento memento = new MessageMemento("Test content",
                java.time.LocalDateTime.now(), recipients);

        assertEquals("Test content", memento.getContent());
        assertNotNull(memento.getTimestamp());
        assertTrue(memento.getRecipients().contains("Bob"));
    }

    @Test
    @DisplayName("Test ChatHistory adds messages correctly")
    void testChatHistoryAddMessage() {
        ChatHistory history = new ChatHistory();
        Message message = new Message("Alice", Arrays.asList("Bob"), "Hello");

        history.addMessage(message);

        assertEquals(1, history.getMessages().size());
        assertEquals("Hello", history.getMessages().get(0).getContent());
    }

    @Test
    @DisplayName("Test ChatHistory iterator by user")
    void testChatHistoryIteratorByUser() {
        ChatHistory history = new ChatHistory();
        Message msg1 = new Message("Alice", Arrays.asList("Bob"), "Message to Bob");
        Message msg2 = new Message("Charlie", Arrays.asList("Alice"), "Message from Charlie");
        Message msg3 = new Message("Alice", Arrays.asList("Charlie"), "Message to Charlie");

        history.addMessage(msg1);
        history.addMessage(msg2);
        history.addMessage(msg3);

        Iterator<Message> iterator = history.iterator(alice);

        int count = 0;
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            // All messages should involve Alice
            assertTrue(msg.getSender().equals("Alice") ||
                    msg.getRecipients().contains("Alice"));
            count++;
        }

        assertEquals(3, count); // All 3 messages involve Alice
    }
    @Test
    @DisplayName("Test unblocking user allows message delivery")
    void testUnblockingUser() {
        bob.blockUser("Alice");
        alice.sendMessage("Blocked message", "Bob");
        assertEquals(0, bob.getChatHistory().getMessages().size());

        bob.unblockUser("Alice");
        alice.sendMessage("Unblocked message", "Bob");

        assertEquals(1, bob.getChatHistory().getMessages().size());
        assertEquals("Unblocked message", bob.getChatHistory().getMessages().get(0).getContent());
    }

    @Test
    @DisplayName("Test blocking affects only blocked user")
    void testBlockingOnlyAffectsSpecificUser() {
        bob.blockUser("Alice");

        alice.sendMessage("To Bob (blocked)", "Bob");
        charlie.sendMessage("To Bob (allowed)", "Bob");

        assertEquals(1, bob.getChatHistory().getMessages().size());
        assertEquals("Charlie", bob.getChatHistory().getMessages().get(0).getSender());
    }
    
    @Test
    @DisplayName("Test User receives message in history")
    void testUserReceiveMessage() {
        alice.sendMessage("Hello Bob!", "Bob");

        List<Message> bobMessages = bob.getChatHistory().getMessages();
        assertEquals(1, bobMessages.size());
        assertEquals("Hello Bob!", bobMessages.get(0).getContent());
        assertEquals("Alice", bobMessages.get(0).getSender());
    }
}