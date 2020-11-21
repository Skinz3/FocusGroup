import java.util.ArrayList;

public class FocusGroup extends binMeta
{
	private final short MemberCount = 10;
	
	private final double InertiaWeigth = 1d;
	
	private int iterations;
	
	private long maxTime;
	
	private Facilitator facilitator;
	
	private ArrayList<Member> members;
	
	private Observer observer;
	
	private Objective objective;
	
	public FocusGroup(Objective objective,int iterations,long maxTime)
	{
		this.objective = objective;
		this.iterations = iterations;
		this.maxTime = maxTime;
		this.initialize();
	}
	
	private void initialize()
	{
		members = new ArrayList<Member>();
		
		for (int i = 0;i < MemberCount;i++)
		{
			Member member = new Member(1d,this);
			members.add(member);
		}
		
		facilitator = new Facilitator(objective);
		
		observer = new Observer();
	}
	
	@Override
	public void optimize() 
	{
	    long startime = System.currentTimeMillis();
	    
	    int k = 0;
	    
	    while (System.currentTimeMillis() - startime < this.maxTime || terminationCriterion())
	    {
	    	internalOptimize(k);
	    	k++;
	    }
	}
	
	private Boolean terminationCriterion()
	{
		return false;
	}
	
	/*
	 * Apply Focus group optimization
	 * k : iteration
	 */
	private void internalOptimize(int k)
	{
		
		for (int i = 0;i < members.size();i++)
		{
			/*
			 *  Retreive the ith member.
			 */
			Member current = members.get(i); 
			/*
			 *  Generate the solution of the ith member
			 */
			Data solution = current.generateSolution();  
			/*
			 *  Hestimate the quality of the given solution  (fitness function)
			 */
			double value = objective.value(solution); 
			/*
			 *  Apply impact limits
			 */
			observer.step(current,solution,value);
			/*
			 * Apply limits on current solution by facilitator based on the predefined framework
			 */
			facilitator.validate(solution);
			
		}
	}

}
