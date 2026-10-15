import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

/** Plain-Java tests (no JUnit needed). Run: java LibraryTest */
public class LibraryTest {
    private static int passed = 0, failed = 0;

    private static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
        if (ok) passed++; else failed++;
    }

    public static void main(String[] args) throws Exception {
        Library lib = new Library();
        lib.addItem(new Book("B1", "Dune, Part 1", "Frank Herbert"));
        lib.addItem(new Book("B2", "Clean Code", "Robert Martin"));
        lib.addItem(new Book("B3", "Effective Java", "Joshua Bloch"));
        lib.addItem(new Book("B4", "Java Concurrency", "Brian Goetz"));
        lib.addItem(new Dvd("D1", "The Matrix", 136));
        lib.registerMember(new Member("M1", "Tadie"));
        lib.registerMember(new Member("M2", "Ruva"));

        // polymorphism
        check("Book loan period is 21", lib.searchByTitle("clean").get(0).loanPeriodDays() == 21);
        check("Dvd loan period is 7", lib.searchByTitle("matrix").get(0).loanPeriodDays() == 7);

        // borrow + unavailable
        LocalDate longAgo = LocalDate.now().minusDays(30);
        lib.borrow("M1", "D1", longAgo);
        try { lib.borrow("M2", "D1"); check("borrowing a loaned item throws", false); }
        catch (ItemNotAvailableException e) { check("borrowing a loaned item throws", true); }

        // member limit
        lib.borrow("M1", "B1");
        lib.borrow("M1", "B2");
        try { lib.borrow("M1", "B3"); check("4th loan throws", false); }
        catch (MemberLimitExceededException e) { check("4th loan throws", true); }
        check("failed borrow leaves item available", lib.searchByTitle("effective").get(0).isAvailable());

        // overdue
        check("only the DVD is overdue", lib.listOverdue(LocalDate.now()).size() == 1
                && lib.listOverdue(LocalDate.now()).get(0).getItem().getId().equals("D1"));

        // validation / missing ids
        try { lib.borrow("NOPE", "B3"); check("unknown member rejected", false); }
        catch (IllegalArgumentException e) { check("unknown member rejected", true); }
        try { new Dvd("D9", "Bad", -5); check("negative DVD length rejected", false); }
        catch (IllegalArgumentException e) { check("negative DVD length rejected", true); }

        // persistence round trip (includes a title with a comma)
        Path dir = Files.createTempDirectory("libtest");
        lib.save(dir);
        Library copy = Library.load(dir);
        check("items reloaded", copy.allItems().size() == 5);
        check("comma in title survives", copy.searchByTitle("dune").get(0).getTitle().equals("Dune, Part 1"));
        check("loans reloaded as unavailable", !copy.searchByTitle("matrix").get(0).isAvailable());
        check("overdue survives reload", copy.listOverdue(LocalDate.now()).size() == 1);
        try { copy.borrow("M1", "B3"); check("loan count survives reload", false); }
        catch (MemberLimitExceededException e) { check("loan count survives reload", true); }

        // return
        copy.giveBack("D1");
        check("return frees the item", copy.searchByTitle("matrix").get(0).isAvailable());
        check("return clears overdue", copy.listOverdue(LocalDate.now()).isEmpty());

        System.out.println("\n" + passed + " passed, " + failed + " failed");
        System.exit(failed == 0 ? 0 : 1);
    }
}
