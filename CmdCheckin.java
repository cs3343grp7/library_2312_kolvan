
public class CmdCheckin extends RecordedCommand
{
	Book checkinBook;
	Member returningMember;
	Member pickupMember;
	boolean isPickupAction = false;
	
	@Override
	public void execute(String[] cmdParts) throws ExInsufficientCommand, ExMemberNotFound, ExBookNotFound, ExNotBorrowedByThisMember
	{
		try 
		{
			isPickupAction = false;
			returningMember = Library.getInstance().findMember(cmdParts[1]);
			checkinBook = Library.getInstance().findBook(cmdParts[2]);
		
			if (returningMember == null)
				throw new ExMemberNotFound();
			
			if (checkinBook == null)
				throw new ExBookNotFound();
			
			if (checkinBook.getBookStatus().getMember()!=returningMember)
				throw new ExNotBorrowedByThisMember();
			
			
			if(checkinBook.sizeOfQueueList()!=0)
			{
				checkinBook.setBookStatus(new BookStatusOnhold());
				pickupMember = checkinBook.takeFromQueueList();
				checkinBook.getBookStatus().set(pickupMember, checkinBook);
				
				System.out.println("Book ["+checkinBook.getID()+" "+checkinBook.getName()+"] is ready for pick up by ["+pickupMember.getID()+" "
									+pickupMember.getName()+"].  On hold due on "+checkinBook.getBookStatus().getDate()+".");
				
				returningMember.returned();
				pickupMember.requestCancel();
				isPickupAction = true;
			}
			else
			{
				checkinBook.setBookStatus(new BookStatusAvailable());
				returningMember.returned();		
			}			
			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.			
			System.out.println("Done.");
				
		} 
		catch (ArrayIndexOutOfBoundsException e) 
		{
			throw new ExInsufficientCommand();
		}
	}
	
	@Override
	public void undoMe()
	{
		if(isPickupAction)
		{
			System.out.println("Sorry. "+pickupMember.getID()+" "+pickupMember.getName()+
					" please ignore the pick up notice for "+checkinBook.getID()+" "+checkinBook.getName()+".");
			checkinBook.addInQueueListWithIndex(0,pickupMember);
			pickupMember.requested();
			isPickupAction = false;
			
		}
		
		checkinBook.setBookStatus(new BookStatusBorrowed());
		checkinBook.getBookStatus().set(returningMember,checkinBook);
		returningMember.borrowed();
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		if(checkinBook.sizeOfQueueList()!=0)
		{
			checkinBook.setBookStatus(new BookStatusOnhold());
			pickupMember = checkinBook.takeFromQueueList();
			checkinBook.getBookStatus().set(pickupMember, checkinBook);
			
			System.out.println("Book ["+checkinBook.getID()+" "+checkinBook.getName()+"] is ready for pick up by ["+pickupMember.getID()+" "
								+pickupMember.getName()+"].  On hold due on "+checkinBook.getBookStatus().getDate()+".");
			
			returningMember.returned();
			pickupMember.requestCancel();
			isPickupAction = true;
		}
		else
		{
			checkinBook.setBookStatus(new BookStatusAvailable());
			returningMember.returned();
		}
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
