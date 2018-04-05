package clg.ahmedalansary.busstation;

public class Driver extends Employee {
private Vehicle vehicle;
public Vehicle getVehicle() {
	return vehicle;
}
public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
}
public Driver(String name, String phoneNumber, String id, int age, String workID, String workPass,byte type) {
	super(name, phoneNumber, id, age, workID, workPass, type);
		// TODO Auto-generated constructor stub
}

}
