
public class CmdListBooks implements Command
{
	@Override
	public void execute(String[] cmdParts)
	{
		Library.getInstance().listLibraryBooks();
	}
}
