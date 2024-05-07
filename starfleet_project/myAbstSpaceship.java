package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class myAbstSpaceship implements Spaceship{

    public static int fighterCost = 2500;
    public static int healthCost = 500;
    private String name;
    private int commission;
    private float max_speed;
    private int fire_power;
    private Set<? extends CrewMember> members;
    private int maintenance;

    private List<Weapon> weapons;
    public static int shipCounter = 0;

    public myAbstSpaceship(String name, int commission, float max_speed,
                           Set<? extends CrewMember> members,
                           List<Weapon> weapons) {
        this.weapons = weapons;
        this.name = name;
        this.commission = commission;
        this.max_speed = max_speed;
        this.members = members;
        this.maintenance = 0;
        int weapons_power=0;
        for (Weapon gun:this.weapons) {
            weapons_power += gun.getFirePower();
        }
        this.fire_power = weapons_power + 10;

    }

    public String getName() {return name;}

    public int getCommissionYear() {return commission;}

    public float getMaximalSpeed() {return max_speed;}
    public Set<? extends CrewMember> getCrewMembers() {return members;}

    public List<Weapon> getWeapon() {return weapons;}

    public void setWeapon(List<Weapon> weapons) {this.weapons = weapons;}

    public void setName(String name){this.name = name;}

    public void setCommissionYear(int commission){this.commission = commission;}

    public void setMaximalSpeed(float max_speed){this.max_speed = max_speed;}

    public void setFirePower(int fire_power){this.fire_power = fire_power;}

    public void setCrewMembers(Set<? extends CrewMember> members){this.members = members;}

    public void setAnnualMaintenanceCost(int maintenance) {this.maintenance = maintenance;}

    public int getFirePower(){
        return this.fire_power;
    }

    public int getAnnualMaintenanceCost(){
        int weapons_cost=0;
        for (Weapon gun:this.weapons) {
            weapons_cost += gun.getAnnualMaintenanceCost();
        }
        return weapons_cost;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof myAbstSpaceship that)) return false;
        return commission == that.commission && Float.compare(that.max_speed, max_speed) == 0 && fire_power == that.fire_power && maintenance == that.maintenance && getName().equals(that.getName()) && members.equals(that.members);
    }

    public int hashCode() {
        return Objects.hash(getName());
    }

    public String toString() {
        return String.format(
                "\tName=%s\n\tCommissionYear=%d\n\tMaximalSpeed=%.1f\n\tFirePower=%d\n" +
                        "\tCrewMembers=%s\n\tAnnualMaintenanceCost=%d\n\tWeaponArray=%s",
                getName(),getCommissionYear(),getMaximalSpeed(),getFirePower(),
                getCrewMembers().size(),getAnnualMaintenanceCost(),getWeapon());
    }
}
