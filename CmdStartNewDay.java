
public class CmdStartNewDay implements Command
{

			@Override
			public void execute(String[] cmdParts)
			{	
				SystemDate.createTheInstance(cmdParts[1]);
				
				for (Book b:Library.getInstance().getBookList())
				{
					if (b.getBookStatus() instanceof BookStatusOnhold)
					{
						if (b.getBookStatus().getDate().datePassed(SystemDate.getInstance()))
						{
							System.out.println("On hold period is over for "+b.getID()+" "+b.getName()+".");
							if(b.sizeOfQueueList()!=0)
							{
								b.setBookStatus(new BookStatusOnhold());
								Member pickupMember = b.takeFromQueueList();
								b.getBookStatus().set(pickupMember, b);
								
								System.out.println("Book ["+b.getID()+" "+b.getName()+"] is ready for pick up by ["+pickupMember.getID()+" "
													+pickupMember.getName()+"].  On hold due on "+b.getBookStatus().getDate()+".");
								
								pickupMember.requestCancel();
							}
							else
							{
								b.setBookStatus(new BookStatusAvailable());
							}
						}
					}
				}
				System.out.println("Done.");
			}
		

}
