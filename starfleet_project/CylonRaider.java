package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends myAbstSpaceship{

	public static int raiderCost = 3500;


	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return super.getAnnualMaintenanceCost() + raiderCost +
				healthCost*getCrewMembers().size() + (int)(1200*getMaximalSpeed());
	}
	@Override
	public String toString(){
		return String.format("CylonRaider\n")+ super.toString();
	}



}
