package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransportShip extends myAbstSpaceship{

	private int cargo;
	private int passengers;

	public static int transporterCost = 3000;
	private static List<Weapon> no_weapons = new ArrayList<>(0);
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
						 int cargoCapacity, int passengerCapacity){
		super(name,commissionYear,maximalSpeed, crewMembers, no_weapons);
		this.cargo = cargoCapacity;
		this.passengers = passengerCapacity;
	}

	public int getCargoCapacity() {
		return cargo;
	}

	public void setCargoCapacity(int cargo) {
		this.cargo = cargo;
	}

	public int getPassengerCapacity() {
		return passengers;
	}

	public void setPassengerCapacity(int passengers) {
		this.passengers = passengers;
	}

	@Override
	public int getAnnualMaintenanceCost(){
		return transporterCost + 5*cargo + 3*passengers;
	}
	@Override
	public String toString(){
		return String.format(
				"TransportShip\n\tName=%s\n\tCommissionYear=%d\n\tMaximalSpeed=%.1f\n\tFirePower=%d\n" +
						"\tCrewMembers=%s\n\tAnnualMaintenanceCost=%d\n\tCargoCapacity=%d\n\tPassengerCapacity=%d",
				getName(),getCommissionYear(),getMaximalSpeed(),getFirePower(),
				getCrewMembers().size(),getAnnualMaintenanceCost(),getCargoCapacity(),getPassengerCapacity());

	}


}
