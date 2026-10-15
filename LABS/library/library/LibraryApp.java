import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static final Path DATA_DIR = Path.of("data");

    public static void main(String[] args) {
        Library library;
        try {
            library = Library.load(DATA_DIR);
            System.out.println("Loaded " + library.allItems().size() + " items and "
                    + library.allMembers().size() + " members.");
        } catch (IOException | RuntimeException e) {
            System.out.println("Could not load saved data (" + e.getMessage() + "). Starting empty.");
            library = new Library();
        }

        try (Scanner in = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                String choice = in.nextLine().trim();
                try {
                    switch (choice) {
                        case "1" -> addItem(library, in);
                        case "2" -> registerMember(library, in);
                        case "3" -> borrow(library, in);
                        case "4" -> giveBack(library, in);
                        case "5" -> listOverdue(library);
                        case "6" -> search(library, in);
                        case "7" -> library.allItems().forEach(System.out::println);
                        case "8" -> library.allMembers().forEach(System.out::println);
                        case "0" -> running = false;
                        default -> System.out.println("Unknown option.");
                    }
                } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                    System.out.println("Cannot borrow: " + e.getMessage());
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        try {
            library.save(DATA_DIR);
            System.out.println("Data saved. Goodbye!");
        } catch (IOException e) {
            System.out.println("WARNING: could not save data: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("""

                === Library Management System ===
                1. Add item        5. List overdue
                2. Register member 6. Search by title
                3. Borrow          7. List all items
                4. Return          8. List members
                0. Save and exit""");
        System.out.print("Choice: ");
    }

    private static String ask(Scanner in, String prompt) {
        System.out.print(prompt + ": ");
        return in.nextLine().trim();
    }

    private static void addItem(Library lib, Scanner in) {
        String type = ask(in, "Type (book/dvd)").toLowerCase();
        String id = ask(in, "Item id");
        String title = ask(in, "Title");
        switch (type) {
            case "book" -> lib.addItem(new Book(id, title, ask(in, "Author")));
            case "dvd" -> {
                String mins = ask(in, "Running time (minutes)");
                try {
                    lib.addItem(new Dvd(id, title, Integer.parseInt(mins)));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Running time must be a whole number");
                }
            }
            default -> throw new IllegalArgumentException("Type must be 'book' or 'dvd'");
        }
        System.out.println("Item added.");
    }

    private static void registerMember(Library lib, Scanner in) {
        lib.registerMember(new Member(ask(in, "Member id"), ask(in, "Name")));
        System.out.println("Member registered.");
    }

    private static void borrow(Library lib, Scanner in) throws ItemNotAvailableException, MemberLimitExceededException {
        Loan loan = lib.borrow(ask(in, "Member id"), ask(in, "Item id"));
        System.out.println("Borrowed. Due on " + loan.getDueOn());
    }

    private static void giveBack(Library lib, Scanner in) {
        lib.giveBack(ask(in, "Item id"));
        System.out.println("Returned.");
    }

    private static void listOverdue(Library lib) {
        List<Loan> overdue = lib.listOverdue(LocalDate.now());
        if (overdue.isEmpty()) System.out.println("No overdue items.");
        else overdue.forEach(System.out::println);
    }

    private static void search(Library lib, Scanner in) {
        List<LibraryItem> found = lib.searchByTitle(ask(in, "Search text"));
        if (found.isEmpty()) System.out.println("No matches.");
        else found.forEach(System.out::println);
    }
}
