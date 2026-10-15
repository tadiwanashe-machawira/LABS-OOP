public abstract class LibraryItem implements Borrowable {
    private final String id;
    private final String title;
    private boolean available = true;

    protected LibraryItem(String id, String title) {
        if (id == null || id.isBlank())       throw new IllegalArgumentException("Item id is required");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required");
        this.id = id.trim();
        this.title = title.trim();
    }

    // EXPLAIN: each subclass decides its own loan period (polymorphism)
    public abstract int loanPeriodDays();

    // EXPLAIN: used by persistence so we can rebuild the right subclass on reload
    public abstract String getType();
    public abstract String getExtra();

    @Override
    public void borrow(Member member) throws ItemNotAvailableException {
        if (!available) {
            throw new ItemNotAvailableException("'" + title + "' (" + id + ") is already on loan");
        }
        available = false;
    }

    @Override
    public void giveBack() {
        if (available) throw new IllegalStateException("'" + title + "' was not on loan");
        available = true;
    }

    public String getId()        { return id; }
    public String getTitle()     { return title; }
    public boolean isAvailable() { return available; }

    // Package-private: only the persistence layer restores state directly
    void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s (%s, %d-day loan)",
                getType(), id, title, available ? "available" : "on loan", loanPeriodDays());
    }
}
