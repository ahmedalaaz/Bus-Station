package clg.ahmedalansary.busstation;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import clg.ahmedalansary.busstation.TripInterface.tripType;

public class Database {
	// Make it a singleton class
	public Database() {

	}

	private HashMap<String, Employee> authenticatedUsers = new HashMap<>();
	private ArrayList<Trip> currentTrips = new ArrayList<>();
	private HashMap<String, Trip> checkForTripID = new HashMap<>();
	private ArrayList<Vehicle> currentVehicles = new ArrayList<>();
	private HashMap<String, Vehicle> checkForVehicleID = new HashMap<>();
	private HashMap<String, Passenger> passengersUserNameKey = new HashMap<>();
	private HashMap<String, Passenger> passengersEmailKey = new HashMap<>();

	public void readPassengers(String source) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(source));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray passengers = (JSONArray) jsonObject.get("passengers");
			for (Object iterator : passengers) {
				JSONObject passengerObj = (JSONObject) iterator;
				JSONObject passenger = (JSONObject) passengerObj.get("passenger");
				String name = (String) passenger.get("name");
				String phoneNumber = (String) passenger.get("phoneNumber");
				String email = (String) passenger.get("email");
				String pass = (String) passenger.get("pass");
				String userName = (String) passenger.get("userName");
				Passenger tempPassenger = new Passenger(name, phoneNumber, email, pass, userName);
				passengersUserNameKey.put(tempPassenger.getUserName(), tempPassenger);
				passengersEmailKey.put(tempPassenger.getEmail(), tempPassenger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPassenger(Passenger newPassenger) {
		passengersUserNameKey.put(newPassenger.getUserName(), newPassenger);
		passengersEmailKey.put(newPassenger.getEmail(), newPassenger);
		writePassenger(MainView.PASSENGERS_FILE);
	}

	@SuppressWarnings("unchecked")
	public void writePassenger(String source) {
		JSONObject allPassengers = new JSONObject();
		JSONArray passengers = new JSONArray();
		Iterator<?> iterator = passengersUserNameKey.entrySet().iterator();
		while (iterator.hasNext()) {
			HashMap.Entry<?, ?> pair = (HashMap.Entry<?, ?>) iterator.next();
			Passenger tempPassenger = (Passenger) pair.getValue();
			JSONObject obj = new JSONObject();
			JSONObject passengerObj = new JSONObject();
			passengerObj.put("name", tempPassenger.getName());
			passengerObj.put("phoneNumber", tempPassenger.getPhoneNumber());
			passengerObj.put("email", tempPassenger.getEmail());
			passengerObj.put("pass", tempPassenger.getPass());
			passengerObj.put("userName", tempPassenger.getUserName());
			obj.put("passenger", passengerObj);
			passengers.add(obj);
		}
		allPassengers.put("passengers", passengers);
		try (FileWriter file = new FileWriter(source)) {
			file.write(allPassengers.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + allPassengers);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void readAccounts(String source) throws org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(source));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray accounts = (JSONArray) jsonObject.get("employees");
			for (Object iterator : accounts) {
				JSONObject accountObj = (JSONObject) iterator;
				JSONObject account = (JSONObject) accountObj.get("employee");
				String name = (String) account.get("name");
				String phoneNumber = (String) account.get("phoneNumber");
				String id = (String) account.get("id");
				int age = Integer.parseInt(account.get("age").toString());
				String workId = (String) account.get("workId");
				String workPass = (String) account.get("workPass");
				byte type = Byte.parseByte((String) account.get("type"));
				Employee tempEmployee = null;
				if (type == 1)
					tempEmployee = new Manager(name, phoneNumber, id, age, workId, workPass, type);
				else
					tempEmployee = new Driver(name, phoneNumber, id, age, workId, workPass, type);
				authenticatedUsers.put(workId, tempEmployee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addAccount(Employee newEmployee) {
		authenticatedUsers.put(newEmployee.getWorkID(), newEmployee);
		writeAccounts(MainView.ACCOUNTS_FILE);
	}

	@SuppressWarnings("unchecked")
	public void writeAccounts(String source) {
		JSONObject allEmployees = new JSONObject();
		JSONArray employees = new JSONArray();
		Iterator<?> iterator = authenticatedUsers.entrySet().iterator();
		while (iterator.hasNext()) {
			HashMap.Entry<?, ?> pair = (HashMap.Entry<?, ?>) iterator.next();
			Employee tempEmployee = (Employee) pair.getValue();
			JSONObject obj = new JSONObject();
			JSONObject empObj = new JSONObject();
			empObj.put("name", tempEmployee.getName());
			empObj.put("phoneNumber", tempEmployee.getPhoneNumber());
			empObj.put("id", tempEmployee.getId());
			empObj.put("age", Integer.toString(tempEmployee.getAge()));
			empObj.put("workId", tempEmployee.getWorkID());
			empObj.put("workPass", tempEmployee.getWorkPass());
			empObj.put("type", Byte.toString(tempEmployee.getType()));
			obj.put("employee", empObj);
			employees.add(obj);
		}
		allEmployees.put("employees", employees);
		try (FileWriter file = new FileWriter(source)) {
			file.write(allEmployees.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + allEmployees);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addTrip(Trip trip) {
		currentTrips.add(trip);
		writeTrips(MainView.TRIPS_FILE);
		checkForTripID.put(trip.getTripID(), trip);
	}

	@SuppressWarnings("unchecked")
	public void writeTrips(String source) {
		JSONObject allTrips = new JSONObject();
		JSONArray trips = new JSONArray();
		for (int i = 0; i < currentTrips.size(); i++) {
			Trip trip = currentTrips.get(i);
			trips.add(getTripJSONObject(trip));
		}
		allTrips.put("trips", trips);
		try (FileWriter file = new FileWriter(source)) {
			file.write(allTrips.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + allTrips);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject getTripJSONObject(Trip trip) {
		JSONObject obj = new JSONObject();
		JSONObject tripObj = new JSONObject();
		tripObj.put("origins", trip.getOrigins());
		tripObj.put("destination", trip.getDestination());
		tripObj.put("distance", trip.getDistance());
		tripObj.put("duration", trip.getDuration());
		tripObj.put("departureTime", trip.getDepartureTime());
		tripObj.put("assignedToVehicle", trip.isAssignedToVechicle());
		tripObj.put("tripID", trip.getTripID());
		JSONArray stopsArray = new JSONArray();
		for (Stop stop : trip.getStops()) {
			JSONObject tempStopObjs = new JSONObject();
			JSONObject stopObj = new JSONObject();
			tempStopObjs.put("distanceToStop", stop.getDistanceToStop());
			tempStopObjs.put("durationToStop", stop.getDurationToStop());
			tempStopObjs.put("name", stop.getName());
			stopObj.put("stop", tempStopObjs);
			stopsArray.add(stopObj);
		}
		tripObj.put("date", new SimpleDateFormat("yyyy-MM-dd").format(trip.getTripDate()));
		tripObj.put("tripFlavor", trip.getTripType().toString());
		if (trip instanceof InternalTrip)
			tripObj.put("tripType", "internal");
		else
			tripObj.put("tripType", "external");
		tripObj.put("stops", stopsArray);
		obj.put("trip", tripObj);
		return obj;
	}

	public void readTrips(String source) throws org.json.simple.parser.ParseException {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(source));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray trips = (JSONArray) jsonObject.get("trips");
			for (Object iterator : trips) {
				JSONObject tripObj = (JSONObject) iterator;
				JSONObject trip = (JSONObject) tripObj.get("trip");
				Trip tempTrip = getTripFromJSONObject(trip);
				currentTrips.add(tempTrip);
				checkForTripID.put(tempTrip.getTripID(), tempTrip);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Trip getTripFromJSONObject(JSONObject trip) throws ParseException {
		ArrayList<Stop> tripStops = new ArrayList<>();
		String origins = (String) trip.get("origins");
		String destination = (String) trip.get("destination");
		String distance = (String) trip.get("distance");
		String duration = (String) trip.get("duration");
		String departureTime = (String) trip.get("departureTime");
		// System.out.println(trip.toJSONString());
		JSONArray stops = (JSONArray) trip.get("stops");
		Boolean assignedToVehicle = (Boolean) trip.get("assignedToVehicle");
		String tripID = (String) trip.get("tripID");
		String temp = (String) trip.get("tripFlavor");
		if (stops != null) {
			for (Object stopIterator : stops) {
				JSONObject stopObj = (JSONObject) stopIterator;
				JSONObject stop = (JSONObject) stopObj.get("stop");
				String distanceToStop = (String) stop.get("distanceToStop");
				String durationToStop = (String) stop.get("durationToStop");
				String name = (String) stop.get("name");
				tripStops.add(new Stop(distanceToStop, durationToStop, name));
			}
		}
		// System.out.println(tripStops.size());
		String date = (String) trip.get("date");
		tripType tripType = null;
		switch (temp) {
		case "non_stop":
			tripType = clg.ahmedalansary.busstation.TripInterface.tripType.non_stop;
			break;
		case "one_stop":
			tripType = clg.ahmedalansary.busstation.TripInterface.tripType.one_stop;

			break;
		case "many_stops":
			tripType = clg.ahmedalansary.busstation.TripInterface.tripType.many_stops;

		}
		String type = (String) trip.get("tripType");
		if (type.equals("internal")) {
			Trip tempTrip = new InternalTrip(origins, destination, distance, duration, departureTime, tripStops,
					new SimpleDateFormat("yyy-MM-dd").parse(date), tripType, assignedToVehicle, tripID);
			return tempTrip;
		} else {
			Trip tempTrip = new ExternalTrip(origins, destination, distance, duration, departureTime, tripStops,
					new SimpleDateFormat("yyy-MM-dd").parse(date), tripType, assignedToVehicle, tripID);
			tempTrip.setStopList(tripStops);
			return tempTrip;
		}

	}

	public void addVehicle(Vehicle vehicle) {
		currentVehicles.add(vehicle);
		writeVehicles(MainView.VEHICLES_FILE);
		checkForVehicleID.put(vehicle.getLicence(), vehicle);
	}

	@SuppressWarnings("unchecked")
	public void writeVehicles(String source) {
		JSONObject allVehicles = new JSONObject();
		JSONArray vehicles = new JSONArray();
		for (int i = 0; i < currentVehicles.size(); i++) {
			Vehicle vehicle = currentVehicles.get(i);
			JSONObject obj = new JSONObject();
			JSONObject vehicleObj = new JSONObject();
			vehicleObj.put("brand", vehicle.getBrand());
			vehicleObj.put("driverID", vehicle.getDriverId());
			vehicleObj.put("licence", vehicle.getLicence());
			vehicleObj.put("type", vehicle.getClass().toString());
			JSONArray tripsArray = new JSONArray();
			HashMap<String, Trip> vehicleTrips = vehicle.getCurrentTrips();
			if (vehicleTrips != null) {
				Iterator<?> iterator = vehicleTrips.entrySet().iterator();
				while (iterator.hasNext()) {
					HashMap.Entry<String, Trip> pair = (HashMap.Entry<String, Trip>) iterator.next();
					Trip trip = pair.getValue();
					tripsArray.add(getTripJSONObject(trip));
				}
				vehicleObj.put("trips", tripsArray);
			}
			obj.put("vehicle", vehicleObj);
			vehicles.add(obj);
		}
		allVehicles.put("vehicles", vehicles);
		try (FileWriter file = new FileWriter(source)) {
			file.write(allVehicles.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + allVehicles);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean checkVehicleLicence(String licence) {
		return !checkForVehicleID.containsKey(licence);
	}

	public void readVehicles(String source) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(source));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray vehicles = (JSONArray) jsonObject.get("vehicles");
			for (Object it : vehicles) {
				HashMap<String, Trip> vehicleTrips = new HashMap<>();
				JSONObject vehicleObj = (JSONObject) it;
				JSONObject vehicle = (JSONObject) vehicleObj.get("vehicle");
				String brand = (String) vehicle.get("brand");
				String driverID = (String) vehicle.get("driverID");
				String licence = (String) vehicle.get("licence");
				String type = (String) vehicle.get("type");
				Vehicle tempVehicle;
				if (type.equals(Bus.class.toString())) {
					tempVehicle = new Bus(brand, licence);
				} else if (type.equals(MiniBus.class.toString())) {
					tempVehicle = new MiniBus(brand, licence);

				} else {
					tempVehicle = new Limousine(brand, licence);
				}

				// System.out.println(vehicle.toJSONString());
				JSONArray trips = (JSONArray) vehicle.get("trips");
				if (trips != null) {
					for (Object iterator : trips) {
						JSONObject tripObj = (JSONObject) iterator;
						JSONObject trip = (JSONObject) tripObj.get("trip");
						Trip tempTrip = getTripFromJSONObject(trip);
						tempVehicle.addBusyDate(tempTrip.getTripDate());
						tempVehicle.addBusyDate(MainView.addDayToDate(tempTrip.getTripDate()));
						vehicleTrips.put(tempTrip.getTripID(), tempTrip);
						Trip t = checkForTripID.get(tempTrip.getTripID());
						t.setTripVehicle(tempVehicle);
					}
				}
				if (trips != null)
					tempVehicle.setCurrentTrips(vehicleTrips);
				if (driverID != null) {
					tempVehicle.setDriverId(driverID);
					Driver driver = (Driver) authenticatedUsers.get(driverID);
					// System.out.println(driver.toString());
					tempVehicle.setDriver(driver);
					driver.setVehicle(tempVehicle);
				}
				currentVehicles.add(tempVehicle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void writeSeats(String source) {
		JSONObject allSeats = new JSONObject();
		JSONArray seats = new JSONArray();
		for (Trip trip : currentTrips) {
			ArrayList<Seat> currentSeats = trip.getSeats();
			for (Seat seat : currentSeats) {
				JSONObject seatObj = new JSONObject();
				JSONObject object = new JSONObject();
				Ticket ticket = seat.getTicket();
				seatObj.put("seatNumber", ticket.getSeatNumber());
				seatObj.put("tripID", ticket.getTripID());
				seatObj.put("vehicleLicence", ticket.getVehicleLicence());
				seatObj.put("passengerID", ticket.getPassengerID());
				object.put("seat", seatObj);
				seats.add(object);
			}
		}
		allSeats.put("seats", seats);
		try (FileWriter file = new FileWriter(source)) {
			file.write(allSeats.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + allSeats);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void readSeats(String source) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(source));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray seats = (JSONArray) jsonObject.get("seats");
			for (Object it : seats) {
				JSONObject seatObj = (JSONObject) it;
				JSONObject seat = (JSONObject) seatObj.get("seat");
				String seatNumber = (String) seat.get("seatNumber");
				String tripID = (String) seat.get("tripID");
				String vehicleLicence = (String) seat.get("vehicleLicence");
				String passengerID = (String) seat.get("passengerID");
				Ticket ticket = new Ticket(tripID, vehicleLicence, seatNumber);
				Seat newSeat = new Seat(ticket);
				if (passengerID != null) {
					ticket.setPassengerID(passengerID);
					// TODO Get passenger after reading them from their hash map
					Passenger p = passengersUserNameKey.get(passengerID);
					// System.out.println(p.toString());
					newSeat.setPassenger(p);
					p.addSeat(newSeat);
					ticket.setPassengerName(p.getName());

				}
				Trip trip = checkForTripID.get(tripID);
				ticket.setPrice(MainView.getPrice(trip.getDistance()));
				trip.addSeat(newSeat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Employee authenticateUser(String userID, String userPass) {
		if (authenticatedUsers.containsKey(userID)) {
			if (authenticatedUsers.get(userID).getWorkPass().equals(userPass)) {
				// MainViewController.showErrorDialogue("Login Successful!");
				return authenticatedUsers.get(userID);
			} else {
				MainView.showErrorDialogue("Wrong Password!");
				return null;
			}
		}
		MainView.showErrorDialogue("Wrong ID and Password!");
		return null;
	}

	public Passenger authenticatePassenger(String userID, String userPass) {
		if (passengersUserNameKey.containsKey(userID)) {
			if (passengersUserNameKey.get(userID).getPass().equals(userPass)) {
				// MainViewController.showErrorDialogue("Login Successful!");
				return passengersUserNameKey.get(userID);
			} else {
				MainView.showErrorDialogue("Wrong Password!");
				return null;
			}
		} else if (passengersEmailKey.containsKey(userID)) {
			if (passengersEmailKey.get(userID).getPass().equals(userPass)) {
				// MainViewController.showErrorDialogue("Login Successful!");
				return passengersEmailKey.get(userID);
			} else {
				MainView.showErrorDialogue("Wrong Password!");
				return null;
			}
		} else
			MainView.showErrorDialogue("Wrong ID and Password!");
		return null;
	}

	public boolean isUniqueEmailAndUserName(String query) {
		return !passengersUserNameKey.containsKey(query) && !passengersEmailKey.containsKey(query);
	}

	public ArrayList<Employee> getEmployeesList() {
		// TODO Auto-generated method stub
		ArrayList<Employee> retList = new ArrayList<>();
		Iterator<?> iterator = authenticatedUsers.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			HashMap.Entry<String, Employee> pair = (HashMap.Entry<String, Employee>) iterator.next();
			retList.add((Employee) pair.getValue());
		}
		return retList;
	}

	public ArrayList<Trip> getTripList() {
		// TODO Auto-generated method stub
		return currentTrips;
	}

	public boolean isValidTripId(String check) {
		return !checkForTripID.containsKey(check);
	}

	public boolean checkWorkIdAndId(String workId, String id) {
		// TODO Auto-generated method stub
		Iterator<?> iterator = authenticatedUsers.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			HashMap.Entry<String, Employee> pair = (HashMap.Entry<String, Employee>) iterator.next();
			Employee emp = pair.getValue();
			if (emp.getWorkID().equals(workId))
				return true;
			if (emp.getId().equals(id))
				return true;
		}
		return false;
	}

	public ArrayList<Vehicle> getVehiclesList() {
		// TODO Auto-generated method stub
		return currentVehicles;
	}

	public ArrayList<Driver> getDriversList() {
		// TODO Auto-generated method stub
		ArrayList<Driver> retList = new ArrayList<>();
		Iterator<?> iterator = authenticatedUsers.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			HashMap.Entry<String, Employee> pair = (HashMap.Entry<String, Employee>) iterator.next();
			if (pair.getValue().getType() == 2) {
				retList.add((Driver) pair.getValue());
			}
		}
		return retList;
	}

	public void updateVehicles() {
		writeVehicles(MainView.VEHICLES_FILE);
	}

	public void updateSeats() {
		// TODO Auto-generated method stub
		writeSeats(MainView.SEATS_FILE);
	}

	public void updateTrips() {
		// TODO Auto-generated method stub
		writeTrips(MainView.TRIPS_FILE);
	}

	public Trip getTripById(String tripID) {
		return checkForTripID.get(tripID);
	}

	public void removeTrip(Trip trip) {
		// TODO Auto-generated method stub
		ArrayList<Seat> seats =  trip.getSeats();
		for(Seat seat :  seats) {
			if(seat.getPassenger() != null) {
				MainView.showErrorDialogue("Cannot Delete this Trip already reserved!");
				return;
			}
		}
		currentTrips.remove(trip);	
		Vehicle vehicle = trip.getTripVehicle();
		if(vehicle != null) {
			vehicle.getCurrentTrips().remove(trip.getTripID());
			vehicle.removeBusyDate(trip.getTripDate());
			writeSeats(MainView.SEATS_FILE);
		}
		writeTrips(MainView.TRIPS_FILE);
	}
}
