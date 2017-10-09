import java.util.ArrayList;
import java.util.Collections; //provides sorting

public class Library {
	private ArrayList<Member> allMembers;
	private ArrayList<Book> allBooks;
	
	private static Library instance = new Library();
	private Library()
	{
		allMembers = new ArrayList<Member>();
		allBooks = new ArrayList<Book>();
	}
	
	public static Library getInstance()
	{
		return instance;
	}
	
	public void addMember(Member m)
	{
		allMembers.add(m);
		Collections.sort(allMembers);
	}
	
	public void addBook(Book b)
	{		
		allBooks.add(b);
		Collections.sort(allBooks);
	}
	
	public void removeMember(Member m)
	{
		allMembers.remove(m);
		Collections.sort(allMembers);
	}
	
	public void removeBook(Book b)
	{
		allBooks.remove(b);
		Collections.sort(allBooks);
	}
	
	public void listLibraryMembers()
	{
		System.out.println(Member.getListingHeader());
		for (Member m:allMembers)
			System.out.println(m);
	}

	public void listLibraryBooks() {
		System.out.println(Book.getListingHeader());
		for (Book b:allBooks)
			System.out.println(b);	
	}
	
	public Member findMember(String targetID)
	{
		for (Member m:allMembers)
			if (m.getID().equals(targetID))
				return m;
		return null; //not found
	}
	
	public Book findBook(String targetID)
	{
		for (Book b:allBooks)
			if (b.getID().equals(targetID))
				return b;
		return null; //not found
	}
	
	public ArrayList<Book> getBookList()
	{
		return allBooks;
	}
	
}