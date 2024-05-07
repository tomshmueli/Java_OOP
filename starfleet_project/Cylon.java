package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon extends myAbstCrewMember{

	private int model;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		super(age,yearsInService,name);
		this.model = modelNumber;
	}

	public double getModelNumber() {
		return this.model;
	}

	public void setModelNumber(int new_model) {
		this.model = new_model;
	}

	public String toString() {
		return super.toString() +
				String.format("Model Number: %d\n",getModelNumber());
	}
}
