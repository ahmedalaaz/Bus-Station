package clg.ahmedalansary.busstation;

import java.util.Date;
import java.util.HashMap;

public abstract class Vehicle {
	private String brand;
	private Driver driver;
	private String licence;
	private String driverId;
	private HashMap<String, Trip> currentTrips =  new HashMap<>();
	private HashMap<Date,Boolean> busyDates = new HashMap<>(); 
	private int capacity = 0;
	public int getCapacity() {
		return capacity;
	}
	public void addBusyDate(Date d) {
		busyDates.put(d, true);
	}
	public void removeBusyDate(Date d ) {
		busyDates.remove(d);
	}
	public boolean isDateOk(Date date) {
		return !busyDates.containsKey(date);
	}
	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getDriverId() {
		return driverId;
	}

	@Override
	public String toString() {
		return "Vehicle [brand=" + brand + ", driver=" + driver + ", licence=" + licence + ", driverId=" + driverId
				+ ", currentTrips=" + currentTrips + "]";
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}


	public Vehicle(String brand, Driver driver, String licence) {
		this.brand = brand;
		this.driver = driver;
		this.licence = licence;
		this.driver = null;
		this.driverId = null;
	}

	public Vehicle(String brand, String licence) {
		this.brand = brand;
		this.licence = licence;
		this.driver = null;
		this.driverId = null;
	}

	public HashMap<String, Trip> getCurrentTrips() {
		return currentTrips;
	}

	protected void setCurrentTrips(HashMap<String, Trip> currentTrips) {
		this.currentTrips = currentTrips;
	}

	protected void addNewTrip(Trip trip) {
		this.currentTrips.put(trip.getTripID(), trip);
	}

	public String getBrand() {
		return brand;
	}

	protected void setBrand(String brand) {
		this.brand = brand;
	}

	public Driver getDriver() {
		return driver;
	}

	protected void setDriver(Driver driver) {
		this.driver = driver;
	}
	public String getClassName() {
		return "Vehicle";
	}
}
