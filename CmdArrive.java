
public class CmdArrive extends RecordedCommand
{
	String id;
	String name;
	BookStatus bs;
	Book b;
	
	@Override
	public void execute(String[] cmdParts) throws ExInsufficientCommand, ExBookIDAlreadyInUse
	{
		try 
		{
			id = cmdParts[1];
			name = cmdParts[2];
			bs = new BookStatusAvailable();
			b = new Book(id,name,bs);
			Book bChecking = Library.getInstance().findBook(id);
			
			
			if (bChecking != null)
				throw new ExBookIDAlreadyInUse(bChecking);
				
			Library.getInstance().addBook(b);
			addUndoCommand(this); //<====== store this command (addUndoCommand is implemented in RecordedCommand.java)
			clearRedoList(); //<====== There maybe some commands stored in the redo list.  Clear them.
			System.out.println("Done.");
				
		} 
		
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new ExInsufficientCommand();
		}
	}
	
	@Override
	public void undoMe()
	{
		Library.getInstance().removeBook(b);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		Library.getInstance().addBook(b);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
