package clg.ahmedalansary.busstation;

import java.io.IOException;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import com.google.maps.errors.ApiException;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ManagerViewController implements Initializable {
	private Employee currentEmployee;
	private ArrayList<Stop> stopsList = new ArrayList<>();
	@FXML
	private Button submitTripBtn;
	@FXML
	private Button addNewStopToTripBtn;
	@FXML
	private Button checkIdBtn;
	@FXML
	private VBox newEmployeeBox;
	@FXML
	private VBox viewEmployeesBox;
	@FXML
	private VBox newTripBoxFrame;
	@FXML
	private VBox viewTripsBox;
	@FXML
	private VBox newVehicleBox;
	@FXML 
	private VBox passengersBox;
	@FXML
	private VBox LinkDriverToVehicleBox;
	@FXML
	private VBox LinkVehicleToTripBox;
	@FXML
	private Label welcomingLabel;
	@FXML
	private Label tripFromLabel;
	@FXML
	private Label tripToLabel;
	@FXML
	private Label tripStopsLabel;
	@FXML
	private Label tripDistanceLabel;
	@FXML
	private Label tripDurationLabel;
	@FXML
	private Label departureFromDistinationLabel;
	@FXML
	private Label selectedVehicleLabel;
	@FXML
	private Label selectedDriverLabel;
	@FXML
	private Label tripPassengersLabel;
	@FXML
	private Label selectedTripLinkLabel;
	@FXML
	private Label selectedVehicleLinkLabel;
	@FXML
	private TextField accountNameInput;
	@FXML
	private TextField accountAgeInput;
	@FXML
	private TextField accountIdInput;
	@FXML
	private TextField accountPhoneInput;
	@FXML
	private TextField accountWorkIdInput;
	@FXML
	private TextField tripFromTF;
	@FXML
	private TextField tripToTF;
	@FXML
	private TextField tripStopsTF;
	@FXML
	private TextField tripIdTF;
	@FXML
	private TextField vehicleLicenceTF;
	@FXML
	private JFXComboBox<String> accountTypeComboBox;
	@FXML
	private JFXDatePicker datePicker;
	@FXML
	private JFXTimePicker timePicker;
	@FXML
	private JFXComboBox<String> tripTypeCB;
	@FXML
	private JFXComboBox<String> vehicleBrandsCB;
	@FXML
	private ComboBox<String> vehicleTypeCB;
	@FXML
	private PasswordField accountWorkPassInput;
	@FXML
	private JFXSpinner spinner;
	@FXML
	private ListView<Employee> employeesListView;
	@FXML
	private ListView<Trip> tripsListView;
	@FXML
	private ListView<Vehicle> vehiclesListView;
	@FXML
	private ListView<Driver> driversListView;
	@FXML
	private ListView<Vehicle> vehiclesLinkingListView;
	@FXML
	private ListView<Seat> tripsPassengersListView;
	@FXML
	private ListView<Trip> tripsLinkingListView;
	private ArrayList<Vehicle> myVehiclesList;
	private ObservableList<Vehicle> myVehiclesObservableList;
	private ArrayList<Vehicle> myVehiclesLinkingList;
	private ObservableList<Vehicle> myVehiclesLinkingObservableList;
	private ArrayList<Driver> myDriverList;
	private ObservableList<Driver> myDriverObservableList;
	private ArrayList<Employee> myEmployeeList;
	private ObservableList<Employee> myEmployeesObservableList;
	private ArrayList<Trip> myTripsList;
	private ObservableList<Trip> myTripsObservableList;
	private ArrayList<Trip> myTripsLinkingList;
	private ObservableList<Trip> myTripsLinkingObservableList;
	private ArrayList<Seat> myTripPassengersList;
	private ObservableList<Seat> myTripPassengersObservableList;
	
	private String tempTripID = null;
	private Vehicle selectedV;
	private Driver selectedD;
	@FXML
	protected void closeApp(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	protected void viewNewEmployeeBox() {
		// TODO Hide other visible Frame and add some CSS
		closeAllBoxes();
		newEmployeeBox.setVisible(true);

	}

	@FXML
	protected void viewMyEmployees() {
		closeAllBoxes();
		viewEmployeesBox.setVisible(true);
	}

	@FXML
	protected void viewAllTrips() {
		closeAllBoxes();
		viewTripsBox.setVisible(true);
	}

	@FXML
	protected void viewLinkVehicleToTripBox() {
		closeAllBoxes();
		refreshVehicleTripListViews();
		LinkVehicleToTripBox.setVisible(true);
	}

	private void closeAllBoxes() {
		newEmployeeBox.setVisible(false);
		viewEmployeesBox.setVisible(false);
		newTripBoxFrame.setVisible(false);
		viewTripsBox.setVisible(false);
		newVehicleBox.setVisible(false);
		LinkDriverToVehicleBox.setVisible(false);
		LinkVehicleToTripBox.setVisible(false);
		passengersBox.setVisible(false);
	}

	@FXML
	protected void viewNewTripBox() {
		closeAllBoxes();
		newTripBoxFrame.setVisible(true);
	}

	@FXML
	protected void viewLinkDriverToVehicleBox() {
		refreshVehicleDriverListViews();
		closeAllBoxes();
		LinkDriverToVehicleBox.setVisible(true);
	}

	@FXML
	protected void viewNewVehicleBox() {
		closeAllBoxes();
		newVehicleBox.setVisible(true);
	}

	@FXML
	protected void onVehicleSelected() {
		Vehicle selectedVehicle = vehiclesListView.getSelectionModel().getSelectedItem();
		if (selectedVehicle == null)
			return;
		System.out.println(selectedVehicle.toString());
		selectedVehicleLabel.setText(selectedVehicle.getBrand() + "\n" + selectedVehicle.getLicence());
		selectedV = selectedVehicle;
	}

	@FXML
	protected void onDriverSelected() {
		Driver selectedDriver = driversListView.getSelectionModel().getSelectedItem();
		if (selectedDriver == null)
			return;
		selectedDriverLabel.setText(selectedDriver.getName() + "\n" + selectedDriver.getWorkID());
		System.out.println(selectedDriver.toString());

		selectedD = selectedDriver;
	}

	@FXML
	protected void onVehicleLinkingSelected() {
		Vehicle selectedVehicle = vehiclesLinkingListView.getSelectionModel().getSelectedItem();
		if (selectedVehicle == null)
			return;
		// System.out.println(selectedVehicle.toString());
		selectedVehicleLinkLabel.setText(selectedVehicle.getBrand() + "\n" + selectedVehicle.getLicence());
	}

	@FXML
	protected void onTripLinkingSelected() {
		Trip selectedTrip = tripsLinkingListView.getSelectionModel().getSelectedItem();
		if (selectedTrip == null)
			return;
		selectedTripLinkLabel.setText(selectedTrip.getOrigins() + "\n" + selectedTrip.getTripID());
		// System.out.println(selectedDriver.toString());
	}

	@FXML
	protected void linkDriverToVehicle() {
		Vehicle selectedVehicle = selectedV;
		Driver selectedDriver = selectedD;
		selectedVehicle.setDriver(selectedDriver);
		selectedVehicle.setDriverId(selectedDriver.getWorkID());
		selectedDriver.setVehicle(selectedVehicle);
		MainView.db.updateVehicles();
		MainView.showSuccessDialogue("Successfull Linking!");
	}

	@FXML
	protected void addNewAccount() {
		String name = accountNameInput.getText();
		String phoneNumber = accountPhoneInput.getText();
		String id = accountIdInput.getText();
		String age = accountAgeInput.getText();
		String workId = accountWorkIdInput.getText();
		String workPass = accountWorkPassInput.getText();
		String comboBoxValue = accountTypeComboBox.getValue();
		if (comboBoxValue == null) {
			MainView.showErrorDialogue("Missing Data! || Error Occured!");
			return;
		}
		if (!name.isEmpty() && !phoneNumber.isEmpty() && !id.isEmpty() && !age.isEmpty()

				&& !workId.isEmpty() && !workPass.isEmpty() && !comboBoxValue.isEmpty()) {
			byte type;
			if (comboBoxValue.equals("Manager"))
				type = 1;
			else if (comboBoxValue.equals("Driver"))
				type = 2;
			else {
				type = -1;
			}
			if (MainView.db.checkWorkIdAndId(workId, id)) {
				MainView.showErrorDialogue("ID Already Exists!");
				return;
			}
			if (type == -1) {
				MainView.showErrorDialogue("Missing Data! || Error Occured!");
				return;
			}
			Employee newEmployee;
			if (type == 1) {
				newEmployee = new Manager(name, phoneNumber, id, Integer.parseInt(age), workId, workPass, type);
			} else {
				newEmployee = new Driver(name, phoneNumber, id, Integer.parseInt(age), workId, workPass, type);
			}
			MainView.db.addAccount(newEmployee);
			MainView.showSuccessDialogue("Employee Added Successfully!");
			accountNameInput.setText("");
			accountAgeInput.setText("");
			accountPhoneInput.setText("");
			accountIdInput.setText("");
			accountWorkIdInput.setText("");
			accountTypeComboBox.setValue("");
			accountWorkPassInput.setText("");
		} else {
			MainView.showErrorDialogue("Missing Data! || Error Occured!");
		}

	}

	public void setWelcomingLabel(String welcome) {
		welcomingLabel.setText(welcome);
	}
	// New Vehicle

	@FXML
	protected void addNewVehicle() {
		String brand = vehicleBrandsCB.getValue();
		String type = vehicleTypeCB.getValue();
		String licence = vehicleLicenceTF.getText();
		if (licence == null || brand == null || type == null) {
			MainView.showErrorDialogue("Missing Data!");
			return;
		}
		// CHECK if licence already exists !!
		if (!MainView.db.checkVehicleLicence(licence)) {
			MainView.showErrorDialogue("Licence Already Exists!");
			return;
		}
		Vehicle newVehicle = null;
		switch (type) {
		case "Bus":
			newVehicle = new Bus(brand, licence);
			break;
		case "MiniBus":
			newVehicle = new MiniBus(brand, licence);

			break;
		case "Limousine":
			newVehicle = new Limousine(brand, licence);

		}
		MainView.db.addVehicle(newVehicle);
		MainView.showSuccessDialogue("Vehicle Added Successfully!");
		vehicleBrandsCB.setValue("");
		vehicleTypeCB.setValue("");
		vehicleLicenceTF.setText("");
	}

	// New Trip
	@FXML
	protected void getTripDistDura() {
		String from = tripFromTF.getText();
		String to = tripToTF.getText();
		if (from.isEmpty() || to.isEmpty()) {
			MainView.showErrorDialogue("Empty text fields for the trip!");
			return;
		}
		try {
			// Add process Dialogue :)
			// If no Internet make them editable !!
			spinner.setVisible(true);
			Object[] data = MainView.getDriveDistDura(from, to);
			System.out.println(data.toString());
			tripDistanceLabel.setText(data[0].toString());
			tripDurationLabel.setText(data[1].toString());
			checkIdBtn.setVisible(true);
			tripFromLabel.setText(from);
			tripToLabel.setText(to);
			spinner.setVisible(false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			MainView.showErrorDialogue("Error getting Distance Please recheck the names!");
			e.printStackTrace();
			spinner.setVisible(false);

			return;
		}
		spinner.setVisible(false);
	}

	@FXML
	protected void checkTripID() {
		String tripID = tripIdTF.getText();
		if (tripID == null || tripID.isEmpty()) {
			MainView.showErrorDialogue("Wrong ID for the Trip!");
			return;
		}
		if (!MainView.db.isValidTripId(tripID)) {
			MainView.showErrorDialogue("This Trips ID already exists!");
			return;
		}
		tempTripID = tripID;
		submitTripBtn.setVisible(true);
		addNewStopToTripBtn.setVisible(true);
	}

	@FXML
	protected void addNewStopToTrip() {
		String newStop = tripStopsTF.getText();
		if (newStop.isEmpty() || newStop == null) {
			MainView.showErrorDialogue("Enter the name of the country to add a stop, please!");
			return;
		}
		String distString = tripDistanceLabel.getText();
		distString = removeComma(distString);
		Double dist = Double.parseDouble(distString.split(" ")[0]);

		try {
			Object[] data = MainView.getDriveDistDura(tripFromLabel.getText(), newStop);
			String dString = data[0].toString();
			dString = removeComma(dString);
			Double checkDist = Double.parseDouble(dString.split(" ")[0]);
			if (checkDist > dist) {
				MainView.showErrorDialogue("Error cannot add this stop recheck the distances!");
				return;
			}
			String distStopToDist = (String) MainView.getDriveDistDura(newStop, tripToLabel.getText())[0];
			distStopToDist = removeComma(distStopToDist);
			Double stopToDist = Double.parseDouble(distStopToDist.split(" ")[0]);
			if (stopToDist > dist) {
				MainView.showErrorDialogue("Error cannot add this stop recheck the distances!");
				return;
			}
			stopsList.add(new Stop(data[0].toString(), data[1].toString(), newStop));
			System.out.println(stopsList.toString());
			MainView.showSuccessDialogue("Stop added to Trip Successfully!");
			tripStopsLabel.setText(Integer.toString(stopsList.size()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String removeComma(String distString) {
		if (distString.contains(",")) {
			String temp = "";
			for (int i = 0; i < distString.length(); i++) {
				if (distString.charAt(i) != ',')
					temp += distString.charAt(i);
			}
			distString = temp;
		}

		return distString;
	}
	@FXML
	protected void deleteTrip() {
		Trip trip =  tripsListView.getSelectionModel().getSelectedItem();
		MainView.db.removeTrip(trip);
		MainView.showSuccessDialogue("Trip Deleted!");
		refreshTripsListView();
		
	}
	@FXML
	protected void submitTrip() {
		String tripType = tripTypeCB.getValue().trim();
		String datee = datePicker.getValue().toString().trim();
		if (datee.equals(null)) {
			MainView.showErrorDialogue("Missing Data!");
			return;
		}
		String[] arr = datee.split("-");
		String dateDay = arr[2];
		String dateMonth = arr[1];
		String dateYear = arr[0];
		String departureTime = timePicker.getValue().toString();
		if (tripType == null || dateDay == null || dateMonth == null || dateYear == null || departureTime == null
				|| tempTripID == null) {
			MainView.showErrorDialogue("Missing Data!");
			return;
		}
		if (dateMonth.length() == 1) {
			dateMonth = '0' + dateMonth;
		}
		if (dateDay.length() == 1) {
			dateDay = '0' + dateDay;
		}

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateYear + '-' + dateMonth + '-' + dateDay);
			Date currentDate = MainView.getCurrentDate();
			if (date.before(currentDate)) {
				MainView.showErrorDialogue("Invalid Date!");
				return;
			}
			Trip newTrip = null;
			clg.ahmedalansary.busstation.TripInterface.tripType tempType;
			if (stopsList.size() == 0)
				tempType = clg.ahmedalansary.busstation.TripInterface.tripType.non_stop;
			else if (stopsList.size() == 1)
				tempType = clg.ahmedalansary.busstation.TripInterface.tripType.one_stop;
			else
				tempType = clg.ahmedalansary.busstation.TripInterface.tripType.many_stops;

			if (tripType.equals("Internal Trip")) {
				ArrayList<Stop> tempStops = new ArrayList<Stop>();
				for (Stop s : stopsList) {
					tempStops.add(s);
				}
				newTrip = new InternalTrip(tripFromLabel.getText(), tripToLabel.getText(), tripDistanceLabel.getText(),
						tripDurationLabel.getText(), departureTime, tempStops, date, tempType, false, tempTripID);

				int temp = Integer.parseInt(departureTime.charAt(0) + "");
				if (departureTime.length() > 1 && departureTime.charAt(1) != ':') {
					temp = temp * 10 + Integer.parseInt(departureTime.charAt(1) + "");
				}
				if (departureTime.contains("PM")) {
					temp += 12;
					temp %= 24;
				}
				temp = temp + Integer.parseInt(newTrip.getDuration().split(" ")[0]);
				temp++;
				Date newDate = date;
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 2);
				newDate = c.getTime();
				//System.out.println(date + " " + newDate);
				temp = temp % 24;
				//System.out.println(temp);
				String newTripID = tempTripID;
				int i = 0;
				while (!MainView.db.isValidTripId(newTripID) || newTripID.equals(tempTripID)) {
					newTripID = newTripID + Integer.toString(i);
					i++;
				}
				ArrayList<Stop> newStopList = new ArrayList<Stop>();
				for (Stop stop : stopsList) {
					String s = stop.getName();
					Object[] response = MainView.getDriveDistDura(tripToLabel.getText(), s);
					Stop nStop = new Stop(response[0].toString(), response[1].toString(), s);
					newStopList.add(nStop);
				}

				System.out.println(newStopList.size());
				MainView.db.addTrip(new InternalTrip(tripToLabel.getText(), tripFromLabel.getText(),
						tripDistanceLabel.getText(), tripDurationLabel.getText(), Integer.toString(temp), newStopList,
						newDate, tempType, false, newTripID));

			} else {
				ArrayList<Stop> tempStops = new ArrayList<Stop>();
				for (Stop s : stopsList) {
					tempStops.add(s);
				}
				newTrip = new ExternalTrip(tripFromLabel.getText(), tripToLabel.getText(), tripDistanceLabel.getText(),
						tripDurationLabel.getText(), departureTime, tempStops, date, tempType, false, tempTripID);

				int temp = Integer.parseInt(departureTime.charAt(0) + "");
				if (departureTime.length() > 1 && departureTime.charAt(1) != ':') {
					temp = temp * 10 + Integer.parseInt(departureTime.charAt(1) + "");
				}
				if (departureTime.contains("AM")) {
					temp += 12;
					temp %= 24;
				}
				temp = temp + Integer.parseInt(newTrip.getDuration().split(" ")[0]);
				temp++;
				Date newDate = date;
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 2);
				newDate = c.getTime();
				temp = temp % 24;
				String newTripID = tempTripID;
				int i = 0;
				while (!MainView.db.isValidTripId(newTripID)) {
					newTripID.concat(Integer.toString(i++));
				}
				ArrayList<Stop> newStopList = new ArrayList<Stop>();
				for (Stop stop : stopsList) {
					String s = stop.getName();
					Object[] response = MainView.getDriveDistDura(tripToLabel.getText(), s);
					Stop nStop = new Stop(response[0].toString(), response[1].toString(), s);
					newStopList.add(nStop);
				}
				MainView.db.addTrip(new ExternalTrip(tripToLabel.getText(), tripFromLabel.getText(),
						tripDistanceLabel.getText(), tripDurationLabel.getText(), Integer.toString(temp), newStopList,
						newDate, tempType, false, newTripID));
			}
			MainView.db.addTrip(newTrip);
			MainView.showSuccessDialogue("Trip Added Successfully !");
			stopsList.removeAll(stopsList);
			tripFromTF.setText("");
			tripToTF.setText("");
			tripDistanceLabel.setText("");
			tripDurationLabel.setText("");
			tripIdTF.setText("");
			checkIdBtn.setVisible(false);

			tripStopsTF.setText("");
			addNewStopToTripBtn.setVisible(false);
			tripTypeCB.setValue("");
			tripFromLabel.setText("");
			tripToLabel.setText("");
			tripStopsLabel.setText("");
			submitTripBtn.setVisible(false);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	protected void linkVehicleToTrip() {
		Vehicle selectedVehicle = vehiclesLinkingListView.getSelectionModel().getSelectedItem();
		Trip selectedTrip = tripsLinkingListView.getSelectionModel().getSelectedItem();
		if (!selectedVehicle.isDateOk(selectedTrip.getTripDate())) {
			MainView.showErrorDialogue("Vehicle is busy at this Date!");
			return;
		}
		selectedVehicle.addNewTrip(selectedTrip);
		selectedVehicle.addBusyDate(selectedTrip.getTripDate());
		selectedVehicle.addBusyDate(MainView.addDayToDate(selectedTrip.getTripDate()));
		selectedTrip.assignedToVechicle = true;
		selectedTrip.setTripVehicle(selectedVehicle);
		for (int i = 0; i < selectedVehicle.getCapacity(); i++) {
			selectedTrip.addSeat(new Seat(
					new Ticket(selectedTrip.getTripID(), selectedVehicle.getLicence(), Integer.toString(i + 1))));
		}
		MainView.db.updateVehicles();
		MainView.db.updateSeats();
		System.out.println(selectedTrip.getStopList().size());
		MainView.db.updateTrips();
		MainView.showSuccessDialogue("Successfull Linking!");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		currentEmployee = MainViewController.authenticatedEmployee;
		setWelcomingLabel("Welcome " + currentEmployee.getName());
		accountTypeComboBox.getItems().add("Manager");
		accountTypeComboBox.getItems().add("Driver");
		datePicker.setPromptText("pick a date");
		timePicker.setEditable(false);
		datePicker.setEditable(false);
		timePicker.setPromptText("Choose hour");
		tripIdTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					tripIdTF.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		accountPhoneInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					accountPhoneInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		accountIdInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					accountIdInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		vehicleLicenceTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					vehicleLicenceTF.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		closeAllBoxes();
		myEmployeeList = MainView.db.getEmployeesList();
		myEmployeesObservableList = FXCollections.observableArrayList(myEmployeeList);
		employeesListView.setItems(myEmployeesObservableList);
		employeesListView.setCellFactory(new Callback<ListView<Employee>, ListCell<Employee>>() {
			@Override
			public ListCell<Employee> call(ListView<Employee> p) {

				ListCell<Employee> cell = new ListCell<Employee>() {

					@Override
					protected void updateItem(Employee emp,
							boolean empty) { /*
												 * if (emp != null) { setText(emp.getCellText()); }else {
												 * setVisible(false); }
												 */
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));

							Label iconn = new Label("");
							iconn.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
							iconn.getStyleClass().add("emp-list-icon");
							grid.add(iconn, 0, 0, 1, 2);

							Label name = new Label(emp.getName());
							name.getStyleClass().add("emp-list-name");
							grid.add(name, 1, 0);

							String type = null;
							if (emp.getType() == 1)
								type = "Manager";
							else
								type = "Driver";
							Label dt = new Label(emp.getPhoneNumber() + "  ***  " + type);
							grid.add(dt, 1, 1);
							dt.getStyleClass().add("emp-list-dt");

							setGraphic(grid);
						}
						super.updateItem(emp, empty);

					}

				};

				return cell;
			}
		});
		myTripsList = MainView.db.getTripList();
		myTripsObservableList = FXCollections.observableArrayList(myTripsList);
		tripsListView.setItems(myTripsObservableList);
		tripsListView.setCellFactory(new Callback<ListView<Trip>, ListCell<Trip>>() {
			@Override
			public ListCell<Trip> call(ListView<Trip> p) {

				ListCell<Trip> cell = new ListCell<Trip>() {

					@Override
					protected void updateItem(Trip trip, boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setText(null);
							setGraphic(null);
						} else {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));

							Label icon = new Label("");
							icon.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
							icon.getStyleClass().add("cache-list-icon");
							grid.add(icon, 0, 0, 1, 2);

							Label name = new Label(trip.getOrigins() + "        " + trip.getDestination());
							icon.setFont(Font.font("FontAwesome", 24));
							name.getStyleClass().add("cache-list-name");
							grid.add(name, 1, 0);

							Label dt = new Label(trip.getDistance() + "   ***   " + trip.getDuration());
							icon.setFont(Font.font("MonoSpaced"));
							grid.add(dt, 1, 1);
							dt.getStyleClass().add("cache-list-dt");

							/*
							 * Label tripType = new Label("Stops : " + trip.getStops().size());
							 * icon.setFont(Font.font("MonoSpaced")); grid.add(tripType, 1, 2);
							 * tripType.getStyleClass().add("cache-list-dt");
							 */

							setGraphic(grid);
						}
						super.updateItem(trip, empty);

					}

				};

				return cell;
			}
		});
		myTripsLinkingList = MainView.db.getTripList();
		ArrayList<Trip> temp3 = new ArrayList<Trip>();
		for (Trip t : myTripsLinkingList) {
			System.out.println(t.getTripID() + " " + t.assignedToVechicle);
			if (t.assignedToVechicle == false)
				temp3.add(t);
		}
		myTripsLinkingList = temp3;
		myTripsLinkingObservableList = FXCollections.observableArrayList(myTripsLinkingList);
		tripsLinkingListView.setItems(myTripsLinkingObservableList);
		tripsLinkingListView.setCellFactory(new Callback<ListView<Trip>, ListCell<Trip>>() {
			@Override
			public ListCell<Trip> call(ListView<Trip> p) {

				ListCell<Trip> cell = new ListCell<Trip>() {

					@Override
					protected void updateItem(Trip trip, boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setText(null);
							setGraphic(null);
						} else if (trip.assignedToVechicle == false) {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));

							Label icon = new Label("");
							icon.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
							icon.getStyleClass().add("cache-list-icon");
							grid.add(icon, 0, 0, 1, 2);

							Label name = new Label(trip.getOrigins() + "        " + trip.getDestination());
							icon.setFont(Font.font("FontAwesome", 24));
							name.getStyleClass().add("cache-list-name");
							grid.add(name, 1, 0);

							Label dt = new Label(trip.getDistance() + "   ***   " + trip.getDuration());
							icon.setFont(Font.font("MonoSpaced"));
							grid.add(dt, 1, 1);
							dt.getStyleClass().add("cache-list-dt");

							/*
							 * Label tripType = new Label("Stops : " + trip.getStops().size());
							 * icon.setFont(Font.font("MonoSpaced")); grid.add(tripType, 1, 2);
							 * tripType.getStyleClass().add("cache-list-dt");
							 */

							setGraphic(grid);
						}
						super.updateItem(trip, empty);

					}

				};

				return cell;
			}
		});
		myVehiclesList = MainView.db.getVehiclesList();
		ArrayList<Vehicle> temp = new ArrayList<Vehicle>();
		for (Vehicle v : myVehiclesList) {
			if (v.getDriver() == null)
				temp.add(v);
		}
		myVehiclesList = temp;
		myVehiclesObservableList = FXCollections.observableArrayList(myVehiclesList);
		vehiclesListView.setItems(myVehiclesObservableList);
		vehiclesListView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
			@Override
			public ListCell<Vehicle> call(ListView<Vehicle> p) {

				ListCell<Vehicle> cell = new ListCell<Vehicle>() {

					@Override
					protected void updateItem(Vehicle vehicle, boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setGraphic(null);
							setText(null);
						} else if (vehicle.getDriverId() == null) {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));
							String carIcon = "";
							System.out.println(vehicle.getClass().toString());
							if (vehicle instanceof Bus) {
								carIcon = "";
							} else if (vehicle instanceof MiniBus) {
								carIcon = "";

							} else {
								carIcon = "";
							}

							Label icon = new Label(carIcon);
							icon.setFont(Font.font(26));

							if (vehicle instanceof MiniBus) {
								icon.getStyleClass().add("cache-list-icon-small");

							} else {
								icon.getStyleClass().add("cache-list-icon");
							}
							grid.add(icon, 0, 0, 1, 2);
							Label name = new Label(vehicle.getBrand() + "        " + vehicle.getLicence());
							name.getStyleClass().add("cache-list-name");
							grid.add(name, 1, 0);
							/*
							 * Label dt = new Label(vehicle.get); icon.setFont(Font.font("MonoSpaced"));
							 * grid.add(dt, 1, 1); dt.getStyleClass().add("cache-list-dt");
							 */
							/*
							 * Label tripType = new Label("Stops : " + trip.getStops().size());
							 * icon.setFont(Font.font("MonoSpaced")); grid.add(tripType, 1, 2);
							 * tripType.getStyleClass().add("cache-list-dt");
							 */

							setGraphic(grid);
						}
						super.updateItem(vehicle, empty);

					}

				};

				return cell;
			}
		});
		myVehiclesLinkingList = MainView.db.getVehiclesList();
		ArrayList<Vehicle> temp4 = new ArrayList<Vehicle>();
		for (Vehicle v : myVehiclesLinkingList) {
			if (v.getDriver() != null)
				temp4.add(v);
		}
		myVehiclesLinkingList = temp4;
		myVehiclesLinkingObservableList = FXCollections.observableArrayList(myVehiclesLinkingList);
		vehiclesLinkingListView.setItems(myVehiclesLinkingObservableList);
		vehiclesLinkingListView.setCellFactory(new Callback<ListView<Vehicle>, ListCell<Vehicle>>() {
			@Override
			public ListCell<Vehicle> call(ListView<Vehicle> p) {

				ListCell<Vehicle> cell = new ListCell<Vehicle>() {

					@Override
					protected void updateItem(Vehicle vehicle, boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setGraphic(null);
							setText(null);
						} else if (vehicle.getDriverId() != null) {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));
							String carIcon = "";
							System.out.println(vehicle.getClass().toString());
							if (vehicle instanceof Bus) {
								carIcon = "";
							} else if (vehicle instanceof MiniBus) {
								carIcon = "";

							} else {
								carIcon = "";
							}

							Label icon = new Label(carIcon);
							icon.setFont(Font.font(26));

							if (vehicle instanceof MiniBus) {
								icon.getStyleClass().add("cache-list-icon-small");

							} else {
								icon.getStyleClass().add("cache-list-icon");
							}
							grid.add(icon, 0, 0, 1, 2);
							Label name = new Label(vehicle.getBrand() + "        " + vehicle.getLicence());
							name.getStyleClass().add("cache-list-name");
							grid.add(name, 1, 0);
							/*
							 * Label dt = new Label(vehicle.get); icon.setFont(Font.font("MonoSpaced"));
							 * grid.add(dt, 1, 1); dt.getStyleClass().add("cache-list-dt");
							 */
							/*
							 * Label tripType = new Label("Stops : " + trip.getStops().size());
							 * icon.setFont(Font.font("MonoSpaced")); grid.add(tripType, 1, 2);
							 * tripType.getStyleClass().add("cache-list-dt");
							 */

							setGraphic(grid);
						}
						super.updateItem(vehicle, empty);

					}

				};

				return cell;
			}
		});
		myDriverList = MainView.db.getDriversList();
		ArrayList<Driver> temp2 = new ArrayList<Driver>();
		for (Driver d : myDriverList) {
			if (d.getVehicle() == null)
				temp2.add(d);
		}
		myDriverList = temp2;

		myDriverObservableList = FXCollections.observableArrayList(myDriverList);
		driversListView.setItems(myDriverObservableList);
		driversListView.setCellFactory(new Callback<ListView<Driver>, ListCell<Driver>>() {
			@Override
			public ListCell<Driver> call(ListView<Driver> p) {

				ListCell<Driver> cell = new ListCell<Driver>() {

					@Override
					protected void updateItem(Driver driver, boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setGraphic(null);
							setText(null);
						} else if (driver.getVehicle() == null) {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));

							Label iconn = new Label("");
							iconn.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
							iconn.getStyleClass().add("emp-list-icon");
							grid.add(iconn, 0, 0, 1, 2);

							Label name = new Label(driver.getName());
							name.getStyleClass().add("emp-list-name");
							grid.add(name, 1, 0);

							String type = null;
							type = "Driver";
							Label dt = new Label(driver.getPhoneNumber() + "  ***  " + type);
							grid.add(dt, 1, 1);
							dt.getStyleClass().add("emp-list-dt");

							setGraphic(grid);
						}
						super.updateItem(driver, empty);
					}

				};

				return cell;
			}
		});
		/*
		 * for (int i = 1; i <= 31; i++) dateDayCB.getItems().add(Integer.toString(i));
		 * for (int i = 1; i <= 12; i++)
		 * dateMonthCB.getItems().add(Integer.toString(i)); for (int i = 2018; i <=
		 * 2030; i++) dateYearCB.getItems().add(Integer.toString(i));
		 */
		/*
		 * for (int i = 0; i <= 23; i++)
		 * departureTimeCB.getItems().add(Integer.toString(i));
		 */
		tripTypeCB.getItems().addAll("Internal Trip", "External Trip");

		vehicleBrandsCB.getItems().addAll("Hyundai", "Mercedes-Benz", "Kia", "Jeep", "Mitsubishi", "Toyota", "Renault",
				"Nissan", "Suzuki", "Volkswagen", "Peugeot", "Land Rover", "Chevrolet");
		vehicleTypeCB.getItems().addAll("Bus", "MiniBus", "Limousine");
	}

	@FXML
	protected void refreshEmployeesListView() {
		myEmployeesObservableList.removeAll(myEmployeesObservableList);
		employeesListView.getItems().clear();
		myEmployeeList = MainView.db.getEmployeesList();
		employeesListView.getItems().addAll(myEmployeeList);
	}

	@FXML
	protected void refreshTripsListView() {
		myTripsObservableList.removeAll(myTripsObservableList);
		tripsListView.getItems().clear();
		myTripsList = MainView.db.getTripList();
		tripsListView.getItems().addAll(myTripsList);
	}

	@FXML
	protected void refreshVehicleDriverListViews() {
		myVehiclesObservableList.removeAll(myVehiclesObservableList);
		vehiclesListView.getItems().clear();
		myVehiclesList = MainView.db.getVehiclesList();
		ArrayList<Vehicle> temp = new ArrayList<Vehicle>();
		for (Vehicle v : myVehiclesList) {
			if (v.getDriver() == null)
				temp.add(v);
		}
		myVehiclesList = temp;
		vehiclesListView.getItems().addAll(myVehiclesList);

		myDriverObservableList.removeAll(myDriverObservableList);
		driversListView.getItems().clear();
		myDriverList = MainView.db.getDriversList();
		ArrayList<Driver> temp2 = new ArrayList<Driver>();
		for (Driver d : myDriverList) {
			if (d.getVehicle() == null)
				temp2.add(d);
		}
		myDriverList = temp2;

		driversListView.getItems().addAll(myDriverList);
	}

	@FXML
	protected void refreshVehicleTripListViews() {
		myVehiclesLinkingObservableList.removeAll(myVehiclesLinkingObservableList);
		vehiclesLinkingListView.getItems().clear();
		myVehiclesLinkingList = MainView.db.getVehiclesList();
		ArrayList<Vehicle> temp = new ArrayList<Vehicle>();
		for (Vehicle v : myVehiclesLinkingList) {
			if (v.getDriver() != null)
				temp.add(v);
		}
		myVehiclesLinkingList = temp;
		vehiclesLinkingListView.getItems().addAll(myVehiclesLinkingList);

		myTripsLinkingObservableList.removeAll(myTripsLinkingObservableList);
		tripsLinkingListView.getItems().clear();
		myTripsLinkingList = MainView.db.getTripList();
		ArrayList<Trip> temp3 = new ArrayList<Trip>();
		for (Trip t : myTripsLinkingList) {
			if (t.assignedToVechicle == false)
				temp3.add(t);
		}
		myTripsLinkingList = temp3;

		tripsLinkingListView.getItems().addAll(myTripsLinkingList);
	}

	@FXML
	protected void back(MouseEvent event) {
		tempTripID = null;
		selectedV = null;
		selectedD = null;
		try {
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent mainRoot = FXMLLoader.load(getClass().getResource("/views/main_view.fxml"));
			Scene mainScene = new Scene(mainRoot);
			primaryStage.hide();
			primaryStage.setScene(mainScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	protected void viewTripPassengers() {
		Trip trip = tripsListView.getSelectionModel().getSelectedItem();
		tripPassengersLabel.setText(trip.getOrigins() + " to " + trip.getDestination());
		myTripPassengersList = trip.getSeats();
		ArrayList<Seat> temp = new ArrayList<Seat>();
		for (Seat seat: myTripPassengersList) {
			if (seat.getPassenger() != null)
				temp.add(seat);
		}
		myTripPassengersList =  temp;

		myTripPassengersObservableList= FXCollections.observableArrayList(myTripPassengersList);
		tripsPassengersListView.setItems(myTripPassengersObservableList);
		tripsPassengersListView.setCellFactory(new Callback<ListView<Seat>, ListCell<Seat>>() {
			@Override
			public ListCell<Seat> call(ListView<Seat> p) {

				ListCell<Seat> cell = new ListCell<Seat>() {

					@Override
					protected void updateItem(Seat seat , boolean empty) {
						/*
						 * if (emp != null) { setText(emp.getCellText()); }else { setVisible(false); }
						 */
						if (empty) {
							setGraphic(null);
							setText(null);
						} else if (seat.getPassenger() != null) {
							setText(null);
							GridPane grid = new GridPane();
							grid.setHgap(10);
							grid.setVgap(4);
							grid.setPadding(new Insets(0, 10, 0, 10));

							Label iconn = new Label("");
							iconn.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
							iconn.getStyleClass().add("emp-list-icon");
							grid.add(iconn, 0, 0, 1, 2);
							Passenger  passenger  =  seat.getPassenger();
							Label name = new Label(passenger.getName());
							name.getStyleClass().add("emp-list-name");
							grid.add(name, 1, 0);

							Label dt = new Label("Phone : " + passenger.getPhoneNumber() + "\n"
									+ "Seat Number : " + seat.getTicket().getSeatNumber());
							grid.add(dt, 1, 1);
							dt.getStyleClass().add("emp-list-dt");
							
							setGraphic(grid);
						}
						super.updateItem(seat, empty);
					}

				};

				return cell;
			}
		});
		closeAllBoxes();
		passengersBox.setVisible(true);
	}
	@FXML
	protected void returnToViewTrips() {
		closeAllBoxes();
		viewTripsBox.setVisible(true);
	}
}
