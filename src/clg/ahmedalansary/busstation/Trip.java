package clg.ahmedalansary.busstation;

import java.util.ArrayList;
import java.util.Date;

public abstract class Trip implements TripInterface {
	private String origins;
	private String destination;
	private String distance;
	private String duration;
	private String departureTime;
	private String tripID;
	private ArrayList<Seat> seats = new ArrayList<>();
	private int numberOfAvailableSeats;
	private ArrayList<Stop> stopList = new ArrayList<>();
	private Date tripDate;
	public tripType tripType;
	public boolean assignedToVechicle;
	private Vehicle tripVehicle;

	public void decrementSeats() {
		this.numberOfAvailableSeats--;
	}

	public Vehicle getTripVehicle() {
		return tripVehicle;
	}

	public void setTripVehicle(Vehicle tripVehicle) {
		this.tripVehicle = tripVehicle;
	}

	public void addSeat(Seat seat) {
		this.seats.add(seat);
		if (seat.getPassenger() == null) {
			this.numberOfAvailableSeats++;
		}
	}

	public ArrayList<Seat> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<Seat> seats) {
		this.seats = seats;
	}

	public int getNumberOfAvailableSeats() {
		return numberOfAvailableSeats;
	}

	public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
		this.numberOfAvailableSeats = numberOfAvailableSeats;
	}

	public ArrayList<Stop> getStopList() {
		return stopList;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public void setStopList(ArrayList<Stop> stopList) {
		this.stopList = stopList;
	}

	public tripType getTripType() {
		return tripType;
	}

	public void setTripType(tripType tripType) {
		this.tripType = tripType;
	}

	public void setOrigins(String origins) {
		this.origins = origins;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public boolean isAssignedToVechicle() {
		return assignedToVechicle;
	}

	public void setAssignedToVechicle(boolean assignedToVechicle) {
		this.assignedToVechicle = assignedToVechicle;
	}

	public Trip(String origins, String destination, String distance, String duration, String departureTime,
			ArrayList<Stop> stopList, Date tripDate, tripType tripType, boolean assignedToVehicle, String tripID) {
		this.origins = origins;
		this.destination = destination;
		this.distance = distance;
		this.duration = duration;
		this.departureTime = departureTime;
		this.stopList = stopList;
		this.tripDate = tripDate;
		this.tripType = tripType;
		this.assignedToVechicle = assignedToVehicle;
		this.tripID = tripID;
		this.numberOfAvailableSeats = 0;
	}

	@Override
	public String getOrigins() {
		// TODO Auto-generated method stub
		return origins;
	}

	@Override
	public Date getTripDate() {
		// TODO Auto-generated method stub
		return tripDate;
	}

	@Override
	public String getDestination() {
		// TODO Auto-generated method stub
		return destination;
	}

	@Override
	public String getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}

	@Override
	public String getDuration() {
		// TODO Auto-generated method stub
		return duration;
	}

	@Override
	public String getDepartureTime() {
		// TODO Auto-generated method stub
		return departureTime;
	}

	@Override
	public ArrayList<Stop> getStops() {
		// TODO Auto-generated method stub
		return stopList;
	}

	public String getCellText() {
		return "*Origins : " + this.getOrigins() + " \n*Destination : " + this.getDestination() + "\n*Trip ID : "
				+ this.getTripID() + "\n*Trip Flavor : " + this.getTripType().toString();
	}
	public void incrementAvailableSeats() {
		this.numberOfAvailableSeats++;
	}
}
