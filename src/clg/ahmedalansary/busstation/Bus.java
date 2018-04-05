package clg.ahmedalansary.busstation;

public class Bus extends Vehicle {
public Bus(String brand, String licence) {
		super(brand, licence);
		// TODO Auto-generated constructor stub
}
public Bus(String brand,Driver driver, String licence) {
	super(brand,driver, licence);
	// TODO Auto-generated constructor stub
}

private final int CAPACITY = 40;

@Override
public int getCapacity() {
	// TODO Auto-generated method stub
	return CAPACITY;
}
@Override
public String getClassName() {
	// TODO Auto-generated method stub
	return "Bus";
}

}
