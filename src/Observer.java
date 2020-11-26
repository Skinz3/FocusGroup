
public class Observer 
{
	private FocusGroup focusGroup;
	
	public Observer(FocusGroup focusGroup)
	{
		this.focusGroup = focusGroup;
	}
	public void step(Member current, Data solution, double value)
	{
		for (Member member : focusGroup.getMembers())
		{
			//member.setIC(current.() * member.getCIndex());
		}
		/* Modify current impact coefficient */
	}
}
