package clg.ahmedalansary.busstation;

public class MiniBus extends Vehicle {
	public MiniBus(String brand, String licence) {
		super(brand, licence);
		// TODO Auto-generated constructor stub
	}

	public MiniBus(String brand, Driver driver, String licence) {
		super(brand, driver, licence);
		// TODO Auto-generated constructor stub
	}

	private final Integer CAPACITY = 32;


	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return CAPACITY;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "MiniBus";
	}

}
