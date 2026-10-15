public class Book extends LibraryItem {
    private final String author;

    public Book(String id, String title, String author) {
        super(id, title);
        if (author == null || author.isBlank()) throw new IllegalArgumentException("Author is required");
        this.author = author.trim();
    }

    @Override public int loanPeriodDays() { return 21; }
    @Override public String getType()     { return "BOOK"; }
    @Override public String getExtra()    { return author; }
    public String getAuthor()             { return author; }

    @Override
    public String toString() { return super.toString() + " by " + author; }
}
