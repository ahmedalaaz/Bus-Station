package clg.ahmedalansary.busstation;

import java.util.ArrayList;
import java.util.Date;

public interface TripInterface {
	public enum tripType{
		non_stop,
		one_stop,
		many_stops
	};
	public String getOrigins();
	public String getDestination();
	public String getDistance();
	public String getDuration();
	public String getDepartureTime();
	public Vehicle getVechicle();
	public ArrayList<Stop> getStops();
	public Date getTripDate();
}
