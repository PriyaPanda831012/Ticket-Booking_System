package org.airline.bus.Entity;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "add_Bus")
public class AddBus {
	
	@Id
	private long busNo;
	private String To;
	private String From;
	private String busName;
	private String departure;
	private String duration;
	private String arrival;
	private double acPrice;
	private double noAcPrice;
	private String tripType;
	private String acClass;
	private String nonAcClass;
	private int ac_Seat;
	private int nonAc_seat;
	private String[] days;
	private String intermediate;
	private String int_arrival_Time;
	private String int_departure_Time;
	private boolean enabled;
	
	
	public AddBus(long busNo, String to, String from, String busName, String departure, String duration, String arrival,
			double acPrice, double noAcPrice, String tripType, String acClass, String nonAcClass, int ac_Seat,
			int nonAc_seat, String[] days, String intermediate, String int_arrival_Time, String int_departure_Time,
			boolean enabled) {
		this.busNo = busNo;
		To = to;
		From = from;
		this.busName = busName;
		this.departure = departure;
		this.duration = duration;
		this.arrival = arrival;
		this.acPrice = acPrice;
		this.noAcPrice = noAcPrice;
		this.tripType = tripType;
		this.acClass = acClass;
		this.nonAcClass = nonAcClass;
		this.ac_Seat = ac_Seat;
		this.nonAc_seat = nonAc_seat;
		this.days = days;
		this.intermediate = intermediate;
		this.int_arrival_Time = int_arrival_Time;
		this.int_departure_Time = int_departure_Time;
		this.enabled = enabled;
	}
	
	public AddBus() {
		
	}

	public long getBusNo() {
		return busNo;
	}
	public void setBusNo(long busNo) {
		this.busNo = busNo;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public double getAcPrice() {
		return acPrice;
	}
	public void setAcPrice(double acPrice) {
		this.acPrice = acPrice;
	}
	public double getNoAcPrice() {
		return noAcPrice;
	}
	public void setNoAcPrice(double noAcPrice) {
		this.noAcPrice = noAcPrice;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getAcClass() {
		return acClass;
	}
	public void setAcClass(String acClass) {
		this.acClass = acClass;
	}
	public String getNonAcClass() {
		return nonAcClass;
	}
	public void setNonAcClass(String nonAcClass) {
		this.nonAcClass = nonAcClass;
	}
	public int getAc_Seat() {
		return ac_Seat;
	}
	public void setAc_Seat(int ac_Seat) {
		this.ac_Seat = ac_Seat;
	}
	public int getNonAc_seat() {
		return nonAc_seat;
	}
	public void setNonAc_seat(int nonAc_seat) {
		this.nonAc_seat = nonAc_seat;
	}
	public String[] getDays() {
		return days;
	}
	public void setDays(String[] days) {
		this.days = days;
	}
	public String getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}
	public String getInt_arrival_Time() {
		return int_arrival_Time;
	}
	public void setInt_arrival_Time(String int_arrival_Time) {
		this.int_arrival_Time = int_arrival_Time;
	}
	public String getInt_departure_Time() {
		return int_departure_Time;
	}
	public void setInt_departure_Time(String int_departure_Time) {
		this.int_departure_Time = int_departure_Time;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "AddBus [busNo=" + busNo + ", To=" + To + ", From=" + From + ", busName=" + busName + ", departure="
				+ departure + ", duration=" + duration + ", arrival=" + arrival + ", acPrice=" + acPrice
				+ ", noAcPrice=" + noAcPrice + ", tripType=" + tripType + ", acClass=" + acClass + ", nonAcClass="
				+ nonAcClass + ", ac_Seat=" + ac_Seat + ", nonAc_seat=" + nonAc_seat + ", days=" + Arrays.toString(days)
				+ ", intermediate=" + intermediate + ", int_arrival_Time=" + int_arrival_Time + ", int_departure_Time="
				+ int_departure_Time + ", enabled=" + enabled + "]";
	}
	
	

}
