package clg.ahmedalansary.busstation;

public class Ticket {
private String passengerName;
private String passengerID;
private String vehicleLicence;
private String tripID;
private String seatNumber;
private double price;
public Ticket(String tripID,String vehicleLicence, String seatNumber) {
	super();
	this.tripID = tripID;
	this.seatNumber = seatNumber;
	this.vehicleLicence =vehicleLicence;
}
public void setPrice(double price) {
	this.price = price;
}
public double getPrice() {
	return this.price;
}
public String getPassengerID() {
	return passengerID;
}
public void setPassengerID(String passengerID) {
	this.passengerID = passengerID;
}
public String getTripID() {
	return tripID;
}
public void setTripID(String tripID) {
	this.tripID = tripID;
}
public String getPassengerName() {
	return passengerName;
}
public void setPassengerName(String passengerName) {
	this.passengerName = passengerName;
}
public String getVehicleLicence() {
	return vehicleLicence;
}
public void setVehicleLicence(String vehicleLicence) {
	this.vehicleLicence = vehicleLicence;
}
public String getSeatNumber() {
	return seatNumber;
}
public void setSeatNumber(String seatNumber) {
	this.seatNumber = seatNumber;
}


}
