package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends myAbstSpaceship{

	public static int viperCost = 4000;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers,
			List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return super.getAnnualMaintenanceCost() + viperCost +
				healthCost*getCrewMembers().size() + (int)(500*getMaximalSpeed());
	}
	@Override
	public String toString(){
		return String.format("ColonialViper\n")+ super.toString();
	}

}
