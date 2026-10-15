import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class Library {
    // EXPLAIN: declared as Map (interface), keyed by id for O(1) lookup
    private final Map<String, LibraryItem> items = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final Map<String, Loan> activeLoans = new HashMap<>();   // keyed by item id

    // ---------- adding ----------
    public void addItem(LibraryItem item) {
        if (items.containsKey(item.getId())) throw new IllegalArgumentException("Item id already exists: " + item.getId());
        items.put(item.getId(), item);
    }

    public void registerMember(Member member) {
        if (members.containsKey(member.getId())) throw new IllegalArgumentException("Member id already exists: " + member.getId());
        members.put(member.getId(), member);
    }

    // ---------- borrow / return ----------
    public Loan borrow(String memberId, String itemId) throws ItemNotAvailableException, MemberLimitExceededException {
        return borrow(memberId, itemId, LocalDate.now());
    }

    public Loan borrow(String memberId, String itemId, LocalDate today)
            throws ItemNotAvailableException, MemberLimitExceededException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        // Check the limit first so a failed borrow never changes the item's state
        if (member.getLoanCount() >= Member.MAX_LOANS) {
            throw new MemberLimitExceededException(member.getName() + " already has " + Member.MAX_LOANS + " loans");
        }
        item.borrow(member);                          // throws ItemNotAvailableException if on loan
        Loan loan = new Loan(item, member, today);
        member.addLoan(loan);
        activeLoans.put(itemId, loan);
        return loan;
    }

    public void giveBack(String itemId) {
        LibraryItem item = findItem(itemId);
        Loan loan = activeLoans.remove(itemId);
        if (loan == null) throw new IllegalStateException("'" + item.getTitle() + "' is not on loan");
        loan.getMember().removeLoan(loan);
        item.giveBack();
    }

    // ---------- queries ----------
    public List<Loan> listOverdue(LocalDate today) {
        return activeLoans.values().stream()
                .filter(l -> l.isOverdue(today))
                .sorted(Comparator.comparing(Loan::getDueOn))
                .toList();
    }

    public List<LibraryItem> searchByTitle(String text) {
        String needle = text.toLowerCase();
        return items.values().stream()
                .filter(i -> i.getTitle().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(LibraryItem::getTitle))
                .toList();
    }

    public List<LibraryItem> allItems() {
        return items.values().stream().sorted(Comparator.comparing(LibraryItem::getId)).toList();
    }

    public List<Member> allMembers() {
        return members.values().stream().sorted(Comparator.comparing(Member::getId)).toList();
    }

    private Member findMember(String id) {
        Member m = members.get(id);
        if (m == null) throw new IllegalArgumentException("No such member: " + id);
        return m;
    }

    private LibraryItem findItem(String id) {
        LibraryItem i = items.get(id);
        if (i == null) throw new IllegalArgumentException("No such item: " + id);
        return i;
    }

    // ---------- persistence (Lab 10) ----------
    public void save(Path dir) throws IOException {
        Files.createDirectories(dir);
        List<String> lines = new ArrayList<>();
        for (LibraryItem i : allItems()) lines.add(Csv.join(i.getType(), i.getId(), i.getTitle(), i.getExtra()));
        Files.write(dir.resolve("items.csv"), lines);

        lines = new ArrayList<>();
        for (Member m : allMembers()) lines.add(Csv.join(m.getId(), m.getName()));
        Files.write(dir.resolve("members.csv"), lines);

        lines = new ArrayList<>();
        for (Loan l : activeLoans.values()) {
            lines.add(Csv.join(l.getMember().getId(), l.getItem().getId(),
                    l.getBorrowedOn().toString(), l.getDueOn().toString()));
        }
        Files.write(dir.resolve("loans.csv"), lines);
    }

    /** Loads a library from dir; returns an empty library if the files do not exist yet. */
    public static Library load(Path dir) throws IOException {
        Library lib = new Library();
        Path itemsFile = dir.resolve("items.csv");
        Path membersFile = dir.resolve("members.csv");
        Path loansFile = dir.resolve("loans.csv");

        if (Files.exists(itemsFile)) {
            for (String line : Files.readAllLines(itemsFile)) {
                if (line.isBlank()) continue;
                List<String> f = Csv.split(line);
                switch (f.get(0)) {
                    case "BOOK" -> lib.addItem(new Book(f.get(1), f.get(2), f.get(3)));
                    case "DVD"  -> lib.addItem(new Dvd(f.get(1), f.get(2), Integer.parseInt(f.get(3))));
                    default -> throw new IOException("Unknown item type in items.csv: " + f.get(0));
                }
            }
        }
        if (Files.exists(membersFile)) {
            for (String line : Files.readAllLines(membersFile)) {
                if (line.isBlank()) continue;
                List<String> f = Csv.split(line);
                lib.registerMember(new Member(f.get(0), f.get(1)));
            }
        }
        if (Files.exists(loansFile)) {
            for (String line : Files.readAllLines(loansFile)) {
                if (line.isBlank()) continue;
                List<String> f = Csv.split(line);
                Member m = lib.findMember(f.get(0));
                LibraryItem item = lib.findItem(f.get(1));
                Loan loan = new Loan(item, m, LocalDate.parse(f.get(2)), LocalDate.parse(f.get(3)));
                try {
                    item.borrow(m);               // marks the item unavailable again
                    m.addLoan(loan);
                } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                    throw new IOException("Corrupt loans.csv: " + e.getMessage(), e);
                }
                lib.activeLoans.put(item.getId(), loan);
            }
        }
        return lib;
    }
}
