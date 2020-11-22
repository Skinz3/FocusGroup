
public class Member 
{
	private Data currentSolution;
	
	private FocusGroup group;
	
	private double impactCoefficient; // IC
	
	public Member(double initialCoeff,FocusGroup group)
	{
		this.impactCoefficient = initialCoeff;
		this.group = group;
	}
	
	public double getCIndex()
	{
		return impactCoefficient;
	}
	public Data generateSolution() // other parameters !
	{
		return null;
	}

	public void setIC(double ic) 
	{
		this.impactCoefficient = ic;
	}
	
	
	
	
	
}
