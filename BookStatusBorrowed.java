
public class BookStatusBorrowed implements BookStatus
{
	private Member borrowingMember;
	private Book theBook;
	private Day loanDate;
	
	public String getStatus()
	{
		if (theBook.sizeOfQueueList() == 0)
			return String.format("%-12s%-4s%s%s%-3s%s", "Borrowed by",borrowingMember.getID(),borrowingMember.getName()," ","on",loanDate);
		else return String.format("%-12s%-4s%s%s%-3s%-11s%-2s%d%-13s%s", "Borrowed by",borrowingMember.getID(),borrowingMember.getName(),
									" ","on",loanDate,"+",theBook.sizeOfQueueList()," request(s):",theBook.getQueueList());
		
	}//"Borrowed by 002 jason on 8-Jan-2014 ;
	
	public void set(Member aBorrowingMember,Book aBook)
	{
		this.borrowingMember = aBorrowingMember;
		this.theBook = aBook;
		this.loanDate = SystemDate.getInstance().clone();;
	}
	public Member getMember()
	{
		return borrowingMember;
	}

	public Day getDate()
	{
		return loanDate;
	}
}
