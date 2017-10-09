
public class CmdCheckout extends RecordedCommand
{
	Book checkoutBook;
	Member borrowingMember; 
	boolean isOnholdMember;
	
	@Override
	public void execute(String[] cmdParts) throws ExInsufficientCommand, ExMemberNotFound, ExBookNotFound, ExBookNotAvailable, ExLoanQuotaExceeded
	{
		try 
		{
			isOnholdMember = false;
			borrowingMember = Library.getInstance().findMember(cmdParts[1]);
			checkoutBook = Library.getInstance().findBook(cmdParts[2]);
		
		
			if (borrowingMember == null)
				throw new ExMemberNotFound();
			
			if (checkoutBook == null)
				throw new ExBookNotFound();
				
			if (!(checkoutBook.getBookStatus() instanceof BookStatusAvailable))
				if (checkoutBook.getBookStatus() instanceof BookStatusBorrowed)
					throw new ExBookNotAvailable();
				else if (checkoutBook.getBookStatus().getMember() != borrowingMember)			
					throw new ExBookNotAvailable();
				else	isOnholdMember = true;
				
			
			
			if (borrowingMember.getBorrowCounts()>5)
			{
				throw new ExLoanQuotaExceeded();
			}
			
	
			borrowingMember.borrowed();
			checkoutBook.setBookStatus(new BookStatusBorrowed());
			checkoutBook.getBookStatus().set(borrowingMember,checkoutBook);

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
		if (isOnholdMember)
		{
			checkoutBook.setBookStatus(new BookStatusOnhold());
			checkoutBook.getBookStatus().set(borrowingMember, checkoutBook);
		}
		else
		{
			checkoutBook.setBookStatus(new BookStatusAvailable());
		}
		borrowingMember.returned();
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		borrowingMember.borrowed();
		checkoutBook.setBookStatus(new BookStatusBorrowed());
		checkoutBook.getBookStatus().set(borrowingMember,checkoutBook);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
