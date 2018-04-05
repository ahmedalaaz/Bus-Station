package clg.ahmedalansary.busstation;

import java.util.ArrayList;
import java.util.Date;

public class InternalTrip extends Trip{
	public InternalTrip(String origins, String destination, String distance, String duration, 
			String departureTime, ArrayList<Stop> stopList, Date tripDate,tripType tripType,boolean assignedToVehicle,String tripID) {
		super(origins, destination, distance, duration, departureTime, stopList, tripDate,tripType,assignedToVehicle,tripID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vehicle getVechicle() {
		// TODO Auto-generated method stub
		return super.getTripVehicle();
	}

	
}
