import java.time.LocalDate;

public class Loan {
    private final LibraryItem item;
    private final Member member;
    private final LocalDate borrowedOn;
    private final LocalDate dueOn;

    public Loan(LibraryItem item, Member member, LocalDate borrowedOn) {
        this(item, member, borrowedOn, borrowedOn.plusDays(item.loanPeriodDays()));
    }

    // Used when reloading a saved loan with its original due date
    public Loan(LibraryItem item, Member member, LocalDate borrowedOn, LocalDate dueOn) {
        if (item == null || member == null) throw new IllegalArgumentException("Item and member are required");
        this.item = item;
        this.member = member;
        this.borrowedOn = borrowedOn;
        this.dueOn = dueOn;
    }

    public boolean isOverdue(LocalDate today) { return today.isAfter(dueOn); }

    public LibraryItem getItem()    { return item; }
    public Member getMember()       { return member; }
    public LocalDate getBorrowedOn() { return borrowedOn; }
    public LocalDate getDueOn()     { return dueOn; }

    @Override
    public String toString() {
        return item.getTitle() + " -> " + member.getName() + " (due " + dueOn + ")";
    }
}
