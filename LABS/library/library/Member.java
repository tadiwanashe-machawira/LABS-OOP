import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    public static final int MAX_LOANS = 3;

    private final String id;
    private final String name;
    private final List<Loan> loans = new ArrayList<>();

    public Member(String id, String name) {
        if (id == null || id.isBlank())     throw new IllegalArgumentException("Member id is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        this.id = id.trim();
        this.name = name.trim();
    }

    public void addLoan(Loan loan) throws MemberLimitExceededException {
        if (loans.size() >= MAX_LOANS) {
            throw new MemberLimitExceededException(name + " already has " + MAX_LOANS + " loans");
        }
        loans.add(loan);
    }

    public void removeLoan(Loan loan) { loans.remove(loan); }

    public String getId()         { return id; }
    public String getName()       { return name; }
    public int getLoanCount()     { return loans.size(); }
    // EXPLAIN: callers get a read-only view, so they cannot bypass the loan limit
    public List<Loan> getLoans()  { return Collections.unmodifiableList(loans); }

    @Override
    public String toString() { return id + ": " + name + " (" + loans.size() + "/" + MAX_LOANS + " loans)"; }
}
