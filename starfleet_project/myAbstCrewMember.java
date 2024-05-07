package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public abstract class myAbstCrewMember implements CrewMember {

    private String name;
    private int age;
    private int years;

    public myAbstCrewMember(int a_age,int a_years,String a_name){
        super();
        this.name = a_name;
        this.age = a_age;
        this.years = a_years;
    }

    public String getName(){return this.name;}

    public int getAge(){return this.age;}

    public int getYearsInService(){return this.years;}

    public void setName(String new_name){this.name = new_name;}

    public void setAge(int new_age){this.age = new_age;}

    public void setYearsInService(int new_years){this.years= new_years;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        myAbstCrewMember that = (myAbstCrewMember) o;
        return age == that.age && years == that.years && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String toString() {
        return String.format(
                "CrewMember\n",
                "\tName=%s\n",
                "\tAge=%d\n",
                "\tYears In Service=%d\n",
                getName(),getAge(),getYearsInService());

    }
}
