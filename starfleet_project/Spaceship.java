package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Spaceship{

    public String getName();
    public int getCommissionYear();
    public float getMaximalSpeed();
    public int getFirePower();
    public Set<? extends CrewMember> getCrewMembers();

    public int getAnnualMaintenanceCost();

    public default List<Weapon> getWeapon(){return new ArrayList<>();}

}
