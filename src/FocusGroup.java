import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
			Member member = new Member(new Data(1),this);
			members.add(member);
		}
		
		facilitator = new Facilitator(objective);
		
		observer = new Observer(this);
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
		return false; // ??
	}
	
	/*
	 * Apply Focus group optimization
	 * k : iteration
	 */
	private void internalOptimize(int k)
	{
	
		
		for (int i = 0;i < members.size();i++)
		{
			Member current = members.get(i); 

			computeSolutionsFitness(current);
		   
			
			
		}
	}
	private Data computeMemberImpact(Member current)
	{
		return null;
	}
	private void computeSolutionsFitness(Member current)
	{	
		Random R = new Random();
	
		for (int y = 0;y < members.size();y++)
		{
			 Member member = members.get(y);
			 	
		    if (member != current)
		    {
		    	Data bestSolution = member.bestSolution;
		    	  
		    	/*
		    	 *  le calcul de PBI^k_j - PI^k_i peut se faire en calculant la distance de Hamming entre
		    	 *   les deux solutions (faire une différence bit par bit n'a pas trop de sens);
		    	 */
		    	int h = bestSolution.hammingDistanceTo(current.currentSolution); 
		    	
		    	/*
		    	 * la partie Rnd x H se réduit à sélectionner une valeur entière de façon aléatoire
		    	 *  entre 1 et H (ou 0 si H est 0);
		    	 */
		    	int hStar = h > 0 ? 1 + R.nextInt(h) : 0;
		    
		    	Data memberImpact = computeMemberImpact(member);
		    	
		    	int n = hStar;
		    	
		    	Data result = new Data(0);
		    	
		    	while (n > 0)
		    	{
		    		if (memberImpact.getCurrentBit() == 1 && R.nextBoolean())
		    		{
		    			//result.setBit(memberImpact.getBitIndex(),1);
		    			n--;
		    		}
		    		else
		    		{
		    		//	result.setBit(memberImpact.getBitIndex(),0);
		    		}
		    		
		    		memberImpact.moveToNextBit();
		    		
		    	}
		    	
		    }
		 }
	}
	public List<Member> getMembers()
	{
		return members;
	}

}
