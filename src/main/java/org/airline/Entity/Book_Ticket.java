package org.airline.Entity;


import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Book_Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Ticket_Id")
	private long tId;
	@Column(name = "Flight_Number")
	private long flightNo;
	@Column(name = "Flight_Name")
	private String flightName;
	@Column(name = "F_Date")
	private String date;
	@Column(name = "TO")
	private String to;
	@Column(name = "From")
	private String from;
	private String depatureTime;
	private String duration;
	private String arivalTime;
	private double price;
	private String trivalClass;
	private String tripType;
	private String Re_date;
	private String intermediate;
	private Object[] seatStore;
	private String status;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Book_Ticket(long tId, long flightNo, String flightName, String date, String to, String from,
			String depatureTime, String duration, String arivalTime, double price, String trivalClass, String tripType,
			String re_date, String intermediate,Object[] seatStore,
			String status, User user) {
		this.tId = tId;
		this.flightNo = flightNo;
		this.flightName = flightName;
		this.date = date;
		this.to = to;
		this.from = from;
		this.depatureTime = depatureTime;
		this.duration = duration;
		this.arivalTime = arivalTime;
		this.price = price;
		this.trivalClass = trivalClass;
		this.tripType = tripType;
		Re_date = re_date;
		this.intermediate = intermediate;
		this.seatStore = seatStore;
		this.status = status;
		this.user = user;
	}
	public Book_Ticket() {
		
	}
	public long gettId() {
		return tId;
	}
	public void settId(long tId) {
		this.tId = tId;
	}
	public long getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(long flightNo) {
		this.flightNo = flightNo;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getDepatureTime() {
		return depatureTime;
	}
	public void setDepatureTime(String depatureTime) {
		this.depatureTime = depatureTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getArivalTime() {
		return arivalTime;
	}
	public void setArivalTime(String arivalTime) {
		this.arivalTime = arivalTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getTrivalClass() {
		return trivalClass;
	}
	public void setTrivalClass(String trivalClass) {
		this.trivalClass = trivalClass;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	
	public Object[] getSeatStore() {
		return seatStore;
	}
	public void setSeatStore(Object[] seatStore) {
		this.seatStore = seatStore;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getRe_date() {
		return Re_date;
	}
	public void setRe_date(String re_date) {
		Re_date = re_date;
	}
	public String getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(String intermediate) {
		this.intermediate = intermediate;
	}
	@Override
	public String toString() {
		return "Book_Ticket [tId=" + tId + ", flightNo=" + flightNo + ", flightName=" + flightName + ", date=" + date
				+ ", to=" + to + ", from=" + from + ", depatureTime=" + depatureTime + ", duration=" + duration
				+ ", arivalTime=" + arivalTime + ", price=" + price + ", trivalClass=" + trivalClass + ", tripType="
				+ tripType + ", Re_date=" + Re_date + ", intermediate=" + intermediate + ", seatStore="
				+ Arrays.toString(seatStore) + ", status=" + status + ", user=" + user + "]";
	}	
}
