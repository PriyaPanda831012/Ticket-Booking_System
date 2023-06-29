package org.airline.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.airline.bus.Entity.Bus_Booking_Ticket;
import org.airline.bus.Entity.Bus_Round_Trip;

@Entity
@Table(name = "USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Email_Id",unique = true)
	private String email;
	private String password;
	private String gender;
	@Column(name = "Mobile_Number")
	private String mobileNo;
	@Column(name = "Adhar_Number")
	private String adharNo;
	private String address;
	private String role;
	private boolean enabled;
	private double balance;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Book_Ticket> book_Tickets;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Round_Trip> round_Trips;
	
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Bus_Booking_Ticket> bus_Book_Tickets;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Bus_Round_Trip> bus_Round_Trips;
	
	
	public User(Long uId, String name, String email, String password, String gender, String mobileNo, String adharNo,
			String address, String role, boolean enabled, double balance, List<Book_Ticket> book_Tickets,
			List<Round_Trip> round_Trips, List<Bus_Booking_Ticket> bus_Book_Tickets,
			List<Bus_Round_Trip> bus_Round_Trips) {
		this.uId = uId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.adharNo = adharNo;
		this.address = address;
		this.role = role;
		this.enabled = enabled;
		this.balance = balance;
		this.book_Tickets = book_Tickets;
		this.round_Trips = round_Trips;
		this.bus_Book_Tickets = bus_Book_Tickets;
		this.bus_Round_Trips = bus_Round_Trips;
	}

	public User() {
		
	}

	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAdharNo() {
		return adharNo;
	}
	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Book_Ticket> getBook_Tickets() {
		return book_Tickets;
	}
	public void setBook_Tickets(List<Book_Ticket> book_Tickets) {
		this.book_Tickets = book_Tickets;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<Round_Trip> getRound_Trips() {
		return round_Trips;
	}

	public void setRound_Trips(List<Round_Trip> round_Trips) {
		this.round_Trips = round_Trips;
	}
	

	public List<Bus_Booking_Ticket> getBus_Book_Tickets() {
		return bus_Book_Tickets;
	}

	public void setBus_Book_Tickets(List<Bus_Booking_Ticket> bus_Book_Tickets) {
		this.bus_Book_Tickets = bus_Book_Tickets;
	}

	public List<Bus_Round_Trip> getBus_Round_Trips() {
		return bus_Round_Trips;
	}

	public void setBus_Round_Trips(List<Bus_Round_Trip> bus_Round_Trips) {
		this.bus_Round_Trips = bus_Round_Trips;
	}

	@Override
	public String toString() {
		return "User [uId=" + uId + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", mobileNo=" + mobileNo + ", adharNo=" + adharNo + ", address=" + address + ", role=" + role
				+ ", enabled=" + enabled + ", balance=" + balance + ", book_Tickets=" + book_Tickets + ", round_Trips="
				+ round_Trips + ", bus_Book_Tickets=" + bus_Book_Tickets + ", bus_Round_Trips=" + bus_Round_Trips + "]";
	}
	
}
