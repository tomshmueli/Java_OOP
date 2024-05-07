package il.ac.tau.cs.sw1.ex9.starfleet;

public class Officer extends CrewWoman {

	private OfficerRank rank;

	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(age,yearsInService,name);
		this.rank = rank;

	}

	public OfficerRank getRank(){return this.rank;}

	public void setRank(OfficerRank new_rank) {this.rank = new_rank;}

	public String toString() {
		return super.toString() +
				String.format("Rank: %d\n",getRank());
	}


}
