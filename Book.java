import java.util.ArrayList;

public class Book implements Comparable<Book>
{
	private String id;
	private String name;
	private Day arrivalDate;
	private BookStatus bookStatus;
	private ArrayList<Member> queueList;
	
	public Book(String id, String name,BookStatus aState)
	{
		this.id = id;
		this.name = name;
		this.arrivalDate = SystemDate.getInstance().clone();
		this.bookStatus = aState;
		queueList = new ArrayList<Member>();
	}

	public int addInQueueList(Member m)
	{
		queueList.add(m);
		return queueList.size();
	}
	
	public void addInQueueListWithIndex(int index, Member targetm)
	{
		queueList.add(index, targetm);
	}
	
	public void leaveQueueList()
	{
		queueList.remove(queueList.size()-1);
	}
	
	public void removeFromQueueList(Member m)
	{	
		queueList.remove(m);
	}
	
	public String getQueueList()
	{
		String queueListString = "";
		for (Member m : queueList)
			queueListString += m.getID()+" ";
		return queueListString;
	}
	
	public Member takeFromQueueList()
	{	
		Member targetMember = queueList.get(0);
		queueList.remove(0);
		return targetMember;
	}
	
	public int sizeOfQueueList()
	{
		return queueList.size();
	}
	
	public boolean memberFoundInQueue(Member targetm)
	{
		for (Member m : queueList)
		{
			if (m == targetm)
				return true;
		}
		return false;
	}
	
	public void setBookStatus(BookStatus newState)
	{
		this.bookStatus = newState;
	}
	
	public BookStatus getBookStatus()
	{
		return bookStatus;
	}
	
	public String getID()
	{
		return id;
	}
	
	@Override
	public int compareTo(Book another) {
		return this.id.compareTo(another.id);
	}

	public static String getListingHeader() {
		return String.format("%-5s%-20s%-12s%s", "ID","Name","Arrival","Status");
	}
	
	@Override
	public String toString()
	{
		return String.format("%-5s%-20s%-12s%s", id,name,arrivalDate,bookStatus.getStatus());
	}

	public String getName() {
		return name;
	}

	public int listOrderInQueueList(Member cancellingMember) 
	{
		return queueList.indexOf(cancellingMember);
	}

}