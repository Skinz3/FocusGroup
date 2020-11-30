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
	/*
	 * In our binary representation,
	 * I'd suggest to compute the "impact" 
	 * as a bit string (so it's in practice another Data object) 
	 * where two solutions are compared. To compute the impact PIm_i^k,
	 * for example, we compare the current solution of i to the others.
	 * In the impact Data object, the bits that are set to 1 are those 
	 * for which there exist other solutions where the value of the
	 * corresponding bit in the solution of i is different. 
	 * These other solutions can in practice "influence" i to 
	 * change the bits that are different in the other solutions: 
	 * this would be the meaning of equation (1) in binary.
	 */
	private Data computeMemberImpact(Member current)
	{
		Data impact = current.currentSolution;
		
		for (Member member : members)
		{
			if (member != current)
			{
				impact = impact.xor(member.currentSolution);
			}
		}
		
		return impact;
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
		    	
		    	Data memberImpact = computeMemberImpact(member);
		    	/*
		    	 * la partie Rnd x H se réduit à sélectionner une valeur entière de façon aléatoire
		    	 *  entre 1 et H (ou 0 si H est 0);
		    	 */
		    	int hStar = h > 0 ? 1 + R.nextInt(h) : 0;
		    
		    
		    	
		    	
		    	
		    	
		    }
		 }
	}
	public List<Member> getMembers()
	{
		return members;
	}
	
	public static void main(String[] args)
	{
		int h = 2;
		
		Random R = new Random();
		
		StringBuilder result = new StringBuilder("                                                 ");
		
		Data d = new Data(14);
		
		int num = 2;
		
	
		while (num > 0)
		{
			d.moveToNextBit();
			
			if (d.getCurrentBit() == 1 && R.nextBoolean() && result.charAt(d.getCurrentPosition()) != '1')
			{
				result.setCharAt(d.getCurrentPosition(), '1');
				num--;
			}
			else
			{
				result.setCharAt(d.getCurrentPosition(), '0');				
			}

		}
		
		System.out.println(result);
	    
		
	}

}
