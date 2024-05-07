package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;

public class StealthCruiser extends myAbstSpaceship{

	public static int stealth_counter = 0 ;
	public static Weapon laser_cannons= new Weapon("Laser Cannons",10,100);
	public static List<Weapon> only_laser = Collections.singletonList(laser_cannons);
	public StealthCruiser(String name, int commissionYear, float maximalSpeed,
						  Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		stealth_counter ++;
	}

	public StealthCruiser(String name, int commissionYear,
						  float maximalSpeed, Set<CrewMember> crewMembers){
		super(name,commissionYear,maximalSpeed,crewMembers,only_laser);
		stealth_counter ++;
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return super.getAnnualMaintenanceCost() + fighterCost + (int)(1000*getMaximalSpeed())
				+ 50*stealth_counter;
	}
	@Override
	public String toString(){
		return String.format("StealthCruiser\n")+ super.toString();
	}
	
}
