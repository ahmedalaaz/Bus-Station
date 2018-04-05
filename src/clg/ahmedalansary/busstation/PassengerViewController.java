package clg.ahmedalansary.busstation;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PassengerViewController implements Initializable {
	@FXML
	private Label passengerName;
	@FXML
	private Label passengerEmail;
	@FXML
	private Label passengerPhone;
	@FXML
	private Label originsLabel;
	@FXML
	private Label destinationLabel;
	@FXML
	private Label ticketOrigin;
	@FXML
	private Label ticketDestination;
	@FXML
	private Label ticketPrice;
	@FXML
	private Label ticketSeatNumber;
	@FXML
	private Label ticketDate;
	@FXML
	private Label ticketVehicleLicence;
	@FXML
	private AnchorPane passMainPane;
	@FXML
	private AnchorPane reservingTripPane;
	@FXML
	private AnchorPane ticketBox;
	@FXML
	private AnchorPane passSeatsBox;
	@FXML
	private GridPane chairsGridPane;
	@FXML
	private JFXListView<Trip> tripsListView;
	private ObservableList<Trip> observableTripsList;
	private ArrayList<Trip> tripsArrayList;
	@FXML
	private JFXListView<Seat> passSeatsListView;
	private ObservableList<Seat> observablePassSeatsList;
	private ArrayList<Seat> passSeatsArrayList;
	@FXML
	private JFXComboBox<String> originsCB;
	@FXML
	private JFXComboBox<String> destinationCB;
	@FXML
	private JFXProgressBar progressBar;
	private Passenger currentPassenger;
	private ArrayList<Seat> seatsToReserve = new ArrayList<>();
	private Trip cTrip;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		currentPassenger = MainViewController.authenticatedPassenger;
		passengerName.setText(currentPassenger.getName());
		passengerEmail.setText(currentPassenger.getEmail());
		passengerPhone.setText(currentPassenger.getPhoneNumber());

		closeAllPanes();
		passMainPane.setVisible(true);
		tripsArrayList = MainView.db.getTripList();
		Set<String> origins = new HashSet<>();
		Set<String> dests = new HashSet<>();
		originsCB.getItems().add("");
		destinationCB.getItems().add("");
		ArrayList<Trip> temp3 = new ArrayList<Trip>();
		for (Trip t : tripsArrayList) {
			if (t.assignedToVechicle == true && t.getNumberOfAvailableSeats() != 0) {
				// System.out.println(t);
				if (!origins.contains(t.getOrigins())) {
					origins.add(t.getOrigins());
					originsCB.getItems().add(t.getOrigins());
				}
				if (!dests.contains(t.getDestination())) {
					dests.add(t.getDestination());
					destinationCB.getItems().add(t.getDestination());
				}
				temp3.add(t);
			}
		}
		tripsArrayList = temp3;

		passSeatsArrayList = currentPassenger.getSeats();
	}

	@FXML
	protected void viewReserveTripBox() {
		progressBar.setVisible(true);
		closeAllPanes();
		reservingTripPane.setVisible(true);
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// label.setText("Hello World");

				observableTripsList = FXCollections.observableArrayList(tripsArrayList);
				tripsListView.setItems(observableTripsList);
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
								} else if (trip.assignedToVechicle == true && trip.getNumberOfAvailableSeats() != 0) {
									setText(null);
									GridPane grid = new GridPane();
									grid.setHgap(10);
									grid.setVgap(4);
									grid.setPadding(new Insets(0, 10, 0, 10));

									Label icon = new Label("");
									icon.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
									icon.getStyleClass().add("cache-list-icon");
									grid.add(icon, 0, 0, 1, 2);

									Label name = new Label(trip.getOrigins() + "        " + trip.getDestination());
									name.getStyleClass().add("cache-list-name");
									grid.add(name, 1, 0);

									Label dt = new Label(trip.getDistance() + "   ***   " + trip.getDuration());
									grid.add(dt, 1, 1);
									dt.getStyleClass().add("cache-list-dt");

									Label tripStops = new Label("Stops : " + trip.getStops().size());
									grid.add(tripStops, 1, 2);
									tripStops.getStyleClass().add("cache-list-dt");
									Date date = trip.getTripDate();
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
									String d = sdf.format(date);
									Label tripDate = new Label("Date : " + d + " At : " + trip.getDepartureTime());
									grid.add(tripDate, 1, 3);
									tripDate.getStyleClass().add("cache-list-dt");
								
									Label tripVehicle = new Label("Vehicle : " + trip.getVechicle().getClassName());
									grid.add(tripVehicle, 1, 4);
									tripVehicle.getStyleClass().add("cache-list-dt");

									Label tripAvailableSeats = new Label(
											"Available Seats : " + trip.getNumberOfAvailableSeats());
									grid.add(tripAvailableSeats, 1, 5);
									tripAvailableSeats.getStyleClass().add("cache-list-dt");

									setGraphic(grid);
								}
								super.updateItem(trip, empty);

							}

						};

						return cell;
					}
				});

				progressBar.setVisible(false);

			}
		});
		new Thread(sleeper).start();

	}

	@FXML
	protected void viewPassengerTripsBox() {
		progressBar.setVisible(true);

		closeAllPanes();
		passSeatsBox.setVisible(true);
		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {

					Thread.sleep(1500);
				} catch (InterruptedException e) {
				}
				return null;
			}
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				// label.setText("Hello World");
				try {
					passSeatsArrayList = currentPassenger.getSeats();
					observablePassSeatsList = FXCollections.observableArrayList(passSeatsArrayList);
					if (passSeatsArrayList == null || observablePassSeatsList == null)
						return;
					passSeatsListView.setItems(observablePassSeatsList);
					passSeatsListView.setCellFactory(new Callback<ListView<Seat>, ListCell<Seat>>() {
						@Override
						public ListCell<Seat> call(ListView<Seat> p) {

							ListCell<Seat> cell = new ListCell<Seat>() {

								@Override
								protected void updateItem(Seat seat, boolean empty) {
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
										String tripID = seat.getTicket().getTripID();
										Trip trip = MainView.db.getTripById(tripID);
										Label icon = new Label("");
										icon.setFont(Font.font("FontAwesome", FontWeight.BOLD, 24));
										icon.getStyleClass().add("cache-list-icon");
										grid.add(icon, 0, 0, 1, 2);
										Label name = new Label(trip.getOrigins() + "        " + trip.getDestination());
										name.getStyleClass().add("cache-list-name");
										grid.add(name, 1, 0);

										Label dt = new Label(trip.getDistance() + "   ***   " + trip.getDuration());
										grid.add(dt, 1, 1);
										dt.getStyleClass().add("cache-list-dt");
										String stops = "";
										ArrayList<Stop> allStops = trip.getStopList();
										for (Stop s : allStops) {
											stops += s.getName() + ", ";
										}
										Label tripStops = new Label("Stops : " + stops);
										grid.add(tripStops, 1, 2);
										tripStops.getStyleClass().add("cache-list-dt");
										Date date = trip.getTripDate();
										SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
										String d = sdf.format(date);
										Label tripDate = new Label("Date : " + d + " At : " + trip.getDepartureTime());
										grid.add(tripDate, 1, 3);
										tripDate.getStyleClass().add("cache-list-dt");

										Label tripVehicle = new Label("Vehicle : " + trip.getVechicle().getClassName());
										grid.add(tripVehicle, 1, 4);
										tripVehicle.getStyleClass().add("cache-list-dt");

										Label tripSeatNumber = new Label(
												"Seat Number : " + seat.getTicket().getSeatNumber());
										grid.add(tripSeatNumber, 1, 5);
										tripSeatNumber.getStyleClass().add("cache-list-dt");

										setGraphic(grid);
									}
									super.updateItem(seat, empty);

								}

							};

							return cell;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}

				progressBar.setVisible(false);

			}
		});
		new Thread(sleeper).start();

	}

	@FXML
	public void refreshPassSeats() {
		try {
			if (observablePassSeatsList == null || passSeatsArrayList == null)
				return;
			observablePassSeatsList.removeAll(observablePassSeatsList);
			passSeatsListView.getItems().clear();
			passSeatsArrayList = currentPassenger.getSeats();
			passSeatsListView.getItems().addAll(passSeatsArrayList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	protected void closeProgram(MouseEvent event) {
		System.exit(0);
	}

	private void closeAllPanes() {
		passMainPane.setVisible(false);
		reservingTripPane.setVisible(false);
		passSeatsBox.setVisible(false);
		ticketBox.setVisible(false);
	}

	@FXML
	protected void back(MouseEvent event) {
		if (passMainPane.isVisible()) {
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
		} else {
			closeAllPanes();
			cancelReserving();
			passMainPane.setVisible(true);

		}
	}

	@FXML
	protected void filterListView() {
		String origin = originsCB.getValue();
		String dest = destinationCB.getValue();
		int cnt = 0;
		if (origin != null && !origin.isEmpty())
			cnt++;
		if (dest != null && !dest.isEmpty())
			cnt++;
		if (cnt == 0) {
			refreshReservingTripsList();
			return;
		}
		observableTripsList.removeAll(observableTripsList);
		tripsListView.getItems().clear();
		tripsArrayList = MainView.db.getTripList();
		ArrayList<Trip> temp3 = new ArrayList<Trip>();
		for (Trip t : tripsArrayList) {
			if (t.assignedToVechicle == true) {
				// System.out.println(t);
				int temp = 0;
				if (origin != null && t.getOrigins().equals(origin))
					temp++;
				if (dest != null && t.getDestination().equals(dest))
					temp++;
				if (temp != cnt)
					continue;
				temp3.add(t);
			}
		}
		tripsArrayList = temp3;
		tripsListView.getItems().addAll(tripsArrayList);
	}

	@FXML
	protected void viewChairsPane() {
		chairsGridPane.setVgap(25);
		chairsGridPane.setHgap(25);
		Trip selectedTrip = tripsListView.getSelectionModel().getSelectedItem();
		if (selectedTrip == null) {
			MainView.showErrorDialogue("Please Select a Trip!");
			return;
		}
		/*if (!currentPassenger.isDateOk(selectedTrip.getTripDate())) {
			MainView.showErrorDialogue("You are busy at this date!");
			return;
		}*/
		originsLabel.setText(selectedTrip.getOrigins());
		destinationLabel.setText(selectedTrip.getDestination());
		cTrip = selectedTrip;
		ArrayList<Seat> seats = selectedTrip.getSeats();
		int shift = 1;
		for (int i = 0; i < selectedTrip.getVechicle().getCapacity(); i++) {
			Label seat;
			Seat currentSeat = seats.get(i);
			if (currentSeat.getPassenger() == null) {
				Image image = new Image(getClass().getResourceAsStream("/views/freeSeat.png"));
				seat = new Label("", new ImageView(image));
			} else {

				Image image = new Image(getClass().getResourceAsStream("/views/busySeat.png"));
				seat = new Label("", new ImageView(image));
			}
			seat.setOnMousePressed(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					// TODO Auto-generated method stub
					if (currentSeat.getPassenger() != null) {
						MainView.showErrorDialogue("This seat is already reserved ,sorry!");
					} else {
						// take seat index;
						if (seatsToReserve.contains(currentSeat)) {
							seatsToReserve.remove(currentSeat);
							Image image = new Image(getClass().getResourceAsStream("/views/freeSeat.png"));
							seat.setGraphic(new ImageView(image));

						} else {

							seatsToReserve.add(currentSeat);
							Image image = new Image(getClass().getResourceAsStream("/views/trySeat.png"));
							seat.setGraphic(new ImageView(image));

						}
					}
				}
			});
			if (currentSeat.getPassenger() == null) {
				seat.setOnMouseEntered(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						if (seatsToReserve.contains(currentSeat))
							return;
						Image image = new Image(getClass().getResourceAsStream("/views/hoverSeat.png"));
						seat.setGraphic(new ImageView(image));

					}
				});
				seat.setOnMouseExited(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						// TODO Auto-generated method stub
						if (seatsToReserve.contains(currentSeat))
							return;
						Image image = new Image(getClass().getResourceAsStream("/views/freeSeat.png"));
						seat.setGraphic(new ImageView(image));

					}
				});
			}
			if (i % 4 == 0)
				shift = 1;
			if (i % 4 == 2) {
				shift = 10;
			}
			chairsGridPane.add(seat, i % 4 + shift, i / 4 + 1);

		}
		chairsGridPane.setVisible(true);
	}

	@FXML
	protected void confirmReservingTrips() {
		if (seatsToReserve.size() == 0) {
			cancelReserving();
			return;
		}
		Iterator<Seat> iterator = seatsToReserve.iterator();
		while (iterator.hasNext()) {
			Seat seat = (Seat) iterator.next();
			seat.setPassenger(currentPassenger);
			Ticket ticket = seat.getTicket();
			ticket.setPassengerID(currentPassenger.getUserName());
			ticket.setPassengerName(currentPassenger.getName());
			cTrip.decrementSeats();
			double price = MainView.getPrice(cTrip.getDistance());
			ticket.setPrice(price);
			currentPassenger.addBusyDate(cTrip.getTripDate());
			currentPassenger.addSeat(seat);
			ticketOrigin.setText(cTrip.getOrigins());
			ticketDestination.setText(cTrip.getDestination());
			ticketPrice.setText(Double.toString(price));
			ticketSeatNumber.setText(ticket.getSeatNumber());
			SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyy");
			ticketDate.setText(sdf.format(cTrip.getTripDate()) + " " +cTrip.getDepartureTime());
			ticketVehicleLicence.setText(cTrip.getVechicle().getLicence());
			viewTicketDialogue();
		}
		MainView.db.updateSeats();
		refreshReservingTripsList();
		cancelReserving();
	}

	private void viewTicketDialogue() {
		// TODO Auto-generated method stub
		ticketBox.setVisible(true);
	}

	@FXML
	protected void cancelReserving() {
		seatsToReserve.removeAll(seatsToReserve);
		chairsGridPane.getChildren().clear();
		chairsGridPane.setVisible(false);
		cTrip = null;
		originsLabel.setText("Origins");
		destinationLabel.setText("Destination");
	}

	private void refreshReservingTripsList() {
		observableTripsList.removeAll(observableTripsList);
		tripsListView.getItems().clear();
		tripsArrayList = MainView.db.getTripList();
		ArrayList<Trip> temp3 = new ArrayList<Trip>();
		for (Trip t : tripsArrayList) {
			if (t.assignedToVechicle == true && t.getNumberOfAvailableSeats() != 0) {
				// System.out.println(t);
				temp3.add(t);
			}
		}
		tripsArrayList = temp3;
		tripsListView.getItems().addAll(tripsArrayList);
	}

	@FXML
	protected void cancelThisTrip() {
		Seat seat = passSeatsListView.getSelectionModel().getSelectedItem();
		if (seat == null) {
			MainView.showErrorDialogue("Please select a seat!");
			return;
		}
		seat.setPassenger(null);
		seat.getTicket().setPassengerID(null);
		seat.getTicket().setPassengerName(null);
		Trip trip = MainView.db.getTripById(seat.getTicket().getTripID());
		trip.incrementAvailableSeats();
		currentPassenger.getSeats().remove(seat);
		MainView.db.updateSeats();
		refreshPassSeats();
		MainView.showSuccessDialogue("Reservation canceled successfully!");
	}
	@FXML
	protected void closeTicketBox() {
		ticketBox.setVisible(false);
	}
	@FXML
	protected void sendTicketToMail() {
		String mailBody = "";
		mailBody +=  " Origins : " + ticketOrigin.getText() + "\n"+
		"Destination : " + ticketDestination.getText() + "\n"
		+"Seat Number : " + ticketSeatNumber.getText() + "\n"+
		"Price : " + ticketPrice.getText() + "\n"
		+ "Date : " + ticketDate.getText() + "\n"
		+"Vehicle Licence : " + ticketVehicleLicence.getText();
		String res[] = {currentPassenger.getEmail()};
		GmailRequest  mailRequest = new GmailRequest("clg.ahmedalansary", "colllegeProjects4884", res
				, "New Reservation",mailBody);
		mailRequest.sendFromGMail();
		closeTicketBox();
	}

}
