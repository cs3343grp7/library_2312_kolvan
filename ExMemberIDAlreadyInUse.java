
public class ExMemberIDAlreadyInUse extends Exception 
{
	public ExMemberIDAlreadyInUse(Member m)
	{
		super("Member ID already in use: "+m.getID()+" "+m.getName());
	}
}
