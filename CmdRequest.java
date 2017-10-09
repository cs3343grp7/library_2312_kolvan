
public class CmdRequest extends RecordedCommand
{
	Book requestingBook;
	Member requestingMember; 
	
	@Override
	public void execute(String[] cmdParts) throws ExInsufficientCommand, ExMemberNotFound, ExBookNotFound, ExBookIsAvailable, ExBookIsBorrowedByThisMember, ExRequestQuotaExceeded, ExAlreadyRequested
	{
		try
		{
			requestingMember = Library.getInstance().findMember(cmdParts[1]);
			requestingBook = Library.getInstance().findBook(cmdParts[2]);
		
		
			if (requestingMember == null)
				throw new ExMemberNotFound();
			
			if (requestingBook == null)
				throw new ExBookNotFound();
			
			if (requestingBook.getBookStatus().getStatus().equals("Available"))
				throw new ExBookIsAvailable();
			
			if (requestingBook.getBookStatus().getMember() == requestingMember)
			{
				if (requestingBook.getBookStatus() instanceof BookStatusBorrowed)
					throw new ExBookIsBorrowedByThisMember();
				else throw new ExBookIsAvailable();
			}
			
			if (requestingMember.getRequestCounts()>2)
				throw new ExRequestQuotaExceeded();
			
			if (requestingBook.memberFoundInQueue(requestingMember))
				throw new ExAlreadyRequested();
			
							
			int queueNumber = requestingBook.addInQueueList(requestingMember);
			requestingMember.requested();
			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
			System.out.println("Done. This request is no. "+queueNumber+" in the queue.");
	
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new ExInsufficientCommand();
		}
		
		
	}
	
	@Override
	public void undoMe()
	{
		requestingBook.leaveQueueList();
		requestingMember.requestCancel();
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		int queueNumber = requestingBook.addInQueueList(requestingMember);
		requestingMember.requested();
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}

}
