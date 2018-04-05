package clg.ahmedalansary.busstation;

public class Limousine extends Vehicle {
	public Limousine(String brand, String licence) {
		super(brand, licence);
		// TODO Auto-generated constructor stub
	}

	public Limousine(String brand, Driver driver, String licence) {
		super(brand, driver, licence);
		// TODO Auto-generated constructor stub
	}

	private final Integer CAPACITY = 10;

	

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return CAPACITY;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "Limousine";
	}

}
