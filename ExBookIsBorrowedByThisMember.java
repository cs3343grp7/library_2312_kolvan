
public class ExBookIsBorrowedByThisMember extends Exception {
	public ExBookIsBorrowedByThisMember()
	{
		super("The book is already borrowed by the same member!");
	}
}
