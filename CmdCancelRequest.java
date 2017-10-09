
public class CmdCancelRequest extends RecordedCommand
{
	Book targetBook;
	Member cancellingMember;
	int listOrder;
	
	@Override
	public void execute(String[] cmdParts) throws ExInsufficientCommand, ExMemberNotFound, ExBookNotFound, ExRequestRecordNotFound
	{
		try 
		{
			cancellingMember = Library.getInstance().findMember(cmdParts[1]);
			targetBook = Library.getInstance().findBook(cmdParts[2]);
			
			if (cancellingMember == null)
				throw new ExMemberNotFound();
			
			if (targetBook == null)
				throw new ExBookNotFound();
			
			if (!targetBook.memberFoundInQueue(cancellingMember))
				throw new ExRequestRecordNotFound();
			
			listOrder = targetBook.listOrderInQueueList(cancellingMember);
			targetBook.removeFromQueueList(cancellingMember);
			cancellingMember.requestCancel();

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
		targetBook.addInQueueListWithIndex(listOrder,cancellingMember);
		cancellingMember.requested();
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		targetBook.removeFromQueueList(cancellingMember);
		cancellingMember.requestCancel();
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
	

}
