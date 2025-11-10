import java.util.*;

// IterableByUser interface - allows passing a user argument to iterator
interface IterableByUser {
    Iterator<Message> iterator(User userToSearchWith);
}