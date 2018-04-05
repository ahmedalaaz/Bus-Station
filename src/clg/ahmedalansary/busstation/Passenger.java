package clg.ahmedalansary.busstation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Passenger extends Person {
private String email;
private String pass;
private String userName;
private ArrayList<Seat> seats = new ArrayList<>();
public ArrayList<Seat> getSeats() {
	return seats;
}
public void setSeats(ArrayList<Seat> seats) {
	this.seats = seats;
}
public HashMap<Date, Boolean> getBusyDates() {
	return busyDates;
}
public void setBusyDates(HashMap<Date, Boolean> busyDates) {
	this.busyDates = busyDates;
}
public void addBusyDate(Date date) {
	busyDates.put(date, true);
}
private HashMap<Date,Boolean> busyDates = new HashMap<>(); 
	public Passenger(String name, String phoneNumber, String id, int age,String email,String pass,String userName) {
		super(name, phoneNumber, id, age);
		// TODO Auto-generated constructor stub
		this.email = email;
		this.pass = pass;
		this.userName = userName;
		}
	@Override
	public String toString() {
		return "Passenger [email=" + email + ", pass=" + pass + ", userName=" + userName + ", seats=" + seats
				+ ", busyDates=" + busyDates + "]";
	}
	public Passenger(String name, String phoneNumber,String email,String pass,String userName) {
		super(name, phoneNumber);
		// TODO Auto-generated constructor stub
		this.email = email;
		this.pass = pass;
		this.userName = userName;
		}
	public void addSeat(Seat seat) {
		seats.add(seat);
	}
	public String getEmail() {
		return email;
	}
	public boolean isDateOk(Date date) {
		return !busyDates.containsKey(date);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void removeBusyDate(Date tripDate) {
		// TODO Auto-generated method stub
		busyDates.remove(tripDate);
	}

}
