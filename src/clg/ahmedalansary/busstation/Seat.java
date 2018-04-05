package clg.ahmedalansary.busstation;

import javafx.scene.control.Label;

public class Seat {
	private Passenger passenger;
	private Ticket ticket;
	private Label label;
	public Seat( Ticket ticket) {
		super();
		this.ticket = ticket;
		this.label = new Label();
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	
	
}
