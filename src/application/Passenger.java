package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Passenger implements Comparable<Passenger> {
	private int ticketNumber;
	private String fullName;
	private String passportNumber;
	private String nationality;
	private GregorianCalendar birthDate;

	public Passenger(int ticketNumber, String fullName, String passportNumber, String nationality,
			GregorianCalendar birthDate) {
		super();
		this.ticketNumber = ticketNumber;
		this.fullName = fullName;
		this.passportNumber = passportNumber;
		this.nationality = nationality;
		this.birthDate = birthDate;
	}

	public Passenger() {
		super();
	}

	public int getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public GregorianCalendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(GregorianCalendar birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public int compareTo(Passenger o) {
		return this.fullName.compareTo(o.fullName);
	}

	@Override
	public String toString() {
		return String.format(
				"Ticket Number: %-10dFullName: %-30sPassport Number: %-10sNationality: %-10sDate Of Birth %d/%d/%d\n",
				ticketNumber, fullName, passportNumber, nationality, birthDate.get(Calendar.DAY_OF_MONTH),
				birthDate.get(Calendar.MONTH), birthDate.get(Calendar.YEAR));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof Passenger)
			return this.fullName.equals(((Passenger) obj).fullName);
		else
			return super.equals(obj);
	}

}
