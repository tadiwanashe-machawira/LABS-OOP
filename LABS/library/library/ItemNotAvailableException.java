/** Checked exception: the item is already on loan. */
public class ItemNotAvailableException extends Exception {
    public ItemNotAvailableException(String message) { super(message); }
}
