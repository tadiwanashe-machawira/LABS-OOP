import java.util.*;

public class Lab9Main {

    enum Level {
        LOW, MEDIUM, HIGH
    }

    record Student(String name, int age) {}

    static class Book {
        final String title;
        final String isbn;

        Book(String title, String isbn) {
            this.title = title;
            this.isbn = isbn;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Book book
                    && isbn.equals(book.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hash(isbn);
        }

        @Override
        public String toString() {
            return title + " [" + isbn + "]";
        }
    }

    static class SimpleLinkedList<T> implements Iterable<T> {

        private static class Node<T> {
            T value;
            Node<T> next;

            Node(T value) {
                this.value = value;
            }
        }

        private Node<T> head;
        private Node<T> tail;

        void add(T value) {
            Node<T> node = new Node<>(value);

            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }

            tail = node;
        }

        public Iterator<T> iterator() {
            return new Iterator<>() {
                Node<T> current = head;

                public boolean hasNext() {
                    return current != null;
                }

                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    T value = current.value;
                    current = current.next;
                    return value;
                }
            };
        }
    }

    enum Coin {
        PENNY(1),
        NICKEL(5),
        DIME(10),
        QUARTER(25);

        final int value;

        Coin(int value) {
            this.value = value;
        }

        static Map<Coin, Integer> fewestCoins(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException(
                    "Amount cannot be negative"
                );
            }

            Map<Coin, Integer> result = new LinkedHashMap<>();
            Coin[] coins = values();

            for (int i = coins.length - 1; i >= 0; i--) {
                int count = amount / coins[i].value;

                if (count > 0) {
                    result.put(coins[i], count);
                }

                amount %= coins[i].value;
            }

            if (amount != 0) {
                throw new IllegalArgumentException(
                    "Amount cannot be represented"
                );
            }

            return result;
        }
    }

    public static void main(String[] args) {

        for (Level level : Level.values()) {
            switch (level) {
                case LOW ->
                    System.out.println("LOW: proceed carefully");
                case MEDIUM ->
                    System.out.println("MEDIUM: stay alert");
                case HIGH ->
                    System.out.println("HIGH: take immediate action");
            }
        }

        Student s1 = new Student("Tariro", 20);
        Student s2 = new Student("Tariro", 20);

        System.out.println(s1);
        System.out.println("Equal records? " + s1.equals(s2));
        System.out.println("Student name: " + s1.name());

        Set<Book> books = new HashSet<>();

        books.add(new Book("Java Basics", "ISBN-1"));
        books.add(new Book("Another title", "ISBN-1"));

        System.out.println("Unique books: " + books.size());

        SimpleLinkedList<String> list = new SimpleLinkedList<>();

        list.add("first");
        list.add("second");

        for (String item : list) {
            System.out.println(item);
        }

        System.out.println("Coins for 87 cents: "
                + Coin.fewestCoins(87));
    }
}