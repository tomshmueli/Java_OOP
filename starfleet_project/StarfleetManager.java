package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;


public class StarfleetManager{


	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		ArrayList<Spaceship> fleetList = new ArrayList<>(fleet);
		List<String> fleet_str = new ArrayList<>();
		Collections.sort(fleetList, new Comparator<Spaceship>() {
			@Override
			public int compare(Spaceship s1, Spaceship s2) {
				int byFire =  Integer.compare(s2.getFirePower(),s1.getFirePower());
				int byYear =  Integer.compare(s2.getCommissionYear(), s1.getCommissionYear());
				int byName = s1.getName().compareTo(s2.getName());
				if (byFire == 0){
					if (byYear == 0)
						return byName;
					else
						return byYear;
				}
				return byFire;
			}
		});
		fleetList.stream().forEach(x->fleet_str.add(x.toString()));
		return fleet_str;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		HashMap<String,Integer> fleetMap = new HashMap<>();
		for (Spaceship s:fleet){
			String type = s.getClass().getSimpleName();
			if (!fleetMap.containsKey(type))
				fleetMap.put(type,1);
			else{
				int cnt = fleetMap.get(type);
				fleetMap.replace(type,cnt+1);
			}
		}
		return fleetMap;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int tot_cost = 0;
		for (Spaceship s:fleet) {
			tot_cost += s.getAnnualMaintenanceCost();
		}
		return tot_cost;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> weapon_types = new HashSet<>();
		for (Spaceship s:fleet) {
			for (Weapon gun:s.getWeapon()) {
				String type = gun.getName();
				if (!weapon_types.contains(type))
					weapon_types.add(type);
			}
		}
		return weapon_types;

	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int tot_crew = 0;
		for (Spaceship s:fleet) {
			tot_crew += s.getCrewMembers().size();
		}
		return tot_crew;

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		float sum = 0;	// assuming there is at least one memeber in the fleet.
		int num = 0;
		for (Spaceship s:fleet) {
			for (CrewMember member:s.getCrewMembers()) {
				if (member instanceof Officer){
					sum += member.getAge();
					num++;
				}
			}
		}
		return sum/num;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer,Spaceship> shipLeaders = new HashMap<>();
		for (Spaceship s:fleet) {
			List<? extends CrewMember> membersList = new ArrayList<>(s.getCrewMembers());
			Collections.sort(membersList, new Comparator<CrewMember>() {
				@Override
				public int compare(CrewMember o1, CrewMember o2) {
					boolean by_type = o1.getClass() == o2.getClass();
					if (by_type == false){
						if (o1 instanceof Officer)
							return -1;
						else
							return 1;
						}
					if ((o1 instanceof Officer)&&(o2 instanceof Officer)){
						return ((Officer) o2).getRank().compareTo(((Officer) o1).getRank());
					}
					return 0;
				}
			});
			if (membersList.get(0) instanceof Officer)
				shipLeaders.put((Officer) membersList.get(0),s);	// @pre at least one officer on board
		}
		return shipLeaders;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank,Integer> rank_cnt = new HashMap<>();
		for (Spaceship s:fleet) {
			for (CrewMember member:s.getCrewMembers()) {
				if (member instanceof Officer){
					OfficerRank curr_rank = ((Officer) member).getRank();
					if (!rank_cnt.containsKey(curr_rank))
						rank_cnt.put(curr_rank,1);
					else{
						int cnt = rank_cnt.get(curr_rank);
						rank_cnt.replace(curr_rank,cnt+1);
					}
				}
			}
		}
		List<Map.Entry<OfficerRank, Integer>> entryList = new ArrayList<>(rank_cnt.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<OfficerRank, Integer>>() {
			@Override
			public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
				if (o1.getValue() != o2.getValue())
					return o1.getValue().compareTo(o2.getValue());
				else
					return o1.getKey().compareTo(o2.getKey());
			}
		});
		return entryList;
	}

}
