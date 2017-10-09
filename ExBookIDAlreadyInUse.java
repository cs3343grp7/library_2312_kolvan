
public class ExBookIDAlreadyInUse extends Exception {
	public ExBookIDAlreadyInUse(Book b)
	{
		super("Book ID already in use: "+b.getID()+" "+b.getName());
	}
}
