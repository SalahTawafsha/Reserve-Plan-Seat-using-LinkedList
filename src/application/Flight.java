package application;

import java.util.GregorianCalendar;

public class Flight implements Comparable<Flight> {
	private int flightNumber;
	private String airlineName;
	private String source;
	private String destination;
	private int capacity;
	private LinkedList<Passenger> passengers = new LinkedList<>();

	public Flight(int flightNumber, String airline, String source, String destination, int capacity) {
		super();
		this.flightNumber = flightNumber;
		this.airlineName = airline;
		this.source = source;
		this.destination = destination;
		this.capacity = capacity;
	}

	public LinkedList<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(LinkedList<Passenger> passengers) {
		this.passengers = passengers;
	}

	public boolean addPassenger(int flightNumber, int ticketNumber, String fullName, String passportNumber,
			String nationality, GregorianCalendar birthDate) throws IndexOutOfBoundsException {
		if (passengers.length() < capacity)
			return passengers.insert(new Passenger(ticketNumber, fullName, passportNumber, nationality, birthDate));
		else
			throw new IndexOutOfBoundsException("can't add more, the flight " + flightNumber + " is full !");

	}

	public Node<Passenger> passengerSearchByTicketNum(int ticketNumber) {
		Node<Passenger> curr = passengers.getHead();
		while (curr != null) {
			if (curr.getData().getTicketNumber() == ticketNumber)
				return curr;

			curr = curr.getNext();
		}

		return null;
	}

	public Flight() {
		super();
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getAirline() {
		return airlineName;
	}

	public void setAirline(String airline) {
		this.airlineName = airline;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) throws Exception {
		int l = passengers.length();
		if (capacity >= l)
			this.capacity = capacity;
		else
			throw new Exception("the capacity that you entered is NOT accepted since the number of passengers is: " + l
					+ " that larger than capacity you enterd");
	}

	@Override
	public int compareTo(Flight o) {
		if (this.flightNumber > o.flightNumber)
			return 1;
		else if (this.flightNumber < o.flightNumber)
			return -1;
		return 0;
	}

	public int getTicket() {
		Node<Passenger> curr = passengers.getHead();
		if (curr != null) {
			int ticket = curr.getData().getTicketNumber();
			while (true)
				if (passengerSearchByTicketNum(++ticket) == null)
					return ticket;
		}

		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof Flight)
			return this.flightNumber == ((Flight) obj).flightNumber;
		else
			return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format(
				"\nFlight Number: %-10dAirline Name: %-20sSource: %-20sDestination: %-20sCapacity: %-10d\nPassengers: \n"
						+ passengers + "\n",
				flightNumber, airlineName, source, destination, capacity);
	}

}
