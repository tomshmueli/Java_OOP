package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myAbstSpaceship{

	public Fighter(String name, int commission, float max_speed,
				   Set<? extends CrewMember> members,List<Weapon> weaponList){
		super(name,commission,max_speed,members, weaponList);
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return super.getAnnualMaintenanceCost() + fighterCost + (int)(1000*getMaximalSpeed());
	}
	@Override
	public String toString(){
		return String.format("Fighter\n")+ super.toString();
	}

}
