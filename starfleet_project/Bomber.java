package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myAbstSpaceship{

	private static int bomberCost = 5000;
	private static double technicianDiscount = 0.1;
	private int technicians;
	public Bomber(String name, int commissionYear, float maximalSpeed,
				  Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		this.technicians = numberOfTechnicians;
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return (int)((1-this.technicians*technicianDiscount)*super.getAnnualMaintenanceCost()+ bomberCost);
	}

	public int getNumberOfTechnicians() {
		return technicians;
	}

	@Override
	public String toString(){
		return String.format("Bomber\n")+ super.toString() +
				String.format("\n\tNumberOfTechnicians=%d",getNumberOfTechnicians());
	}

}
