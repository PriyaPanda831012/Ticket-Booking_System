
package org.airline.bus.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.airline.Entity.SeatAdd;
import org.airline.Entity.User;
import org.airline.Service.UserService;
import org.airline.bus.Entity.AddBus;
import org.airline.bus.Entity.Bus_Booking_Ticket;
import org.airline.bus.Entity.Bus_Round_Trip;
import org.airline.bus.Service.AddBusService;
import org.airline.bus.Service.Bus_Booking_Service;
import org.airline.bus.Service.Bus_Round_Trip_Service;
import org.airline.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

@RequestMapping("/user")
public class UserBusController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AddBusService addBusService;

	@Autowired
	private Bus_Booking_Service bus_Booking_Service;

	@Autowired
	private Bus_Round_Trip_Service bus_Round_Trip_Service;

	@ModelAttribute
	public void addCommanData(Model model, Principal principal) {
		String uname = principal.getName();
		User user = userService.getUserByUserName(uname);
		model.addAttribute("user", user);

	}

	@GetMapping("/bus_user_home")
	public String userHomePage(Model model) {
		model.addAttribute("title", "User Home");
		return "BusNormal/home";
	}

	// your profile handler

	@GetMapping("/busprofile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");

		return "BusNormal/profile";
	}

	// view Flight

	@GetMapping("/view_bus")
	public String viewBus(Model model) {
		model.addAttribute("title", "View Bus Page");
		List<AddBus> addBus = this.addBusService.findAllBus(); // desable previous date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		model.addAttribute("date", dtf.format(now));

		model.addAttribute("bus", addBus);

		return "BusNormal/view_bus";
	}

	HashMap<String, Double> dat = new HashMap<String, Double>();
	static Double da_P;

	// find Flight

	@GetMapping("/find_bus")
	public String selectedFlight(@RequestParam("From") String From, @RequestParam("To") String To,
			@RequestParam("date") String date, Model model, HttpSession session) {
		dat.put("Sun", 1000.00);
		dat.put("Mon", 1500.00);
		dat.put("Tue", 2000.00);
		dat.put("Wed", 2500.00);
		dat.put("Thu", 3000.00);
		dat.put("Fri", 3500.00);
		dat.put("Sat", 4000.00);

		try {
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			String day1 = new SimpleDateFormat("EE").format(d);
			System.out.println(day1);
			boolean b = true;
			List<AddBus> findAllBus = this.addBusService.findAllBus();
			findAllBus = findAllBus.stream().filter(
					addBus -> (addBus.getFrom().equals(From) && addBus.getTo().equals(To) && (addBus.isEnabled() == b)))
					.collect(Collectors.toList());
			List<AddBus> buss = null;
			if (findAllBus.isEmpty()) {
				session.setAttribute("message", new Message("Bus Not Found !! ", "alert-danger"));
				return "redirect:/user/view_bus";
			} else {
				buss = new ArrayList<AddBus>();
				for (int i = 0; i < findAllBus.size(); i++) {
					c1: for (String day2 : findAllBus.get(i).getDays()) {
						if (day1.equalsIgnoreCase(day2)) {
							buss.add(findAllBus.get(i));
							continue c1;
						}
					}
				}
				if (buss.isEmpty()) {
					session.setAttribute("message", new Message("Bus Not Found !! ", "alert-danger"));
					return "redirect:/user/view_bus";
				}
				model.addAttribute("date1", date);
				Set<String> set = dat.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if (key.equals(day1)) {
						da_P = dat.get(key);
						System.out.println(da_P);
						break;
					}
				}
				model.addAttribute("addBus", buss);
			}

			model.addAttribute("title", "View Bus Page");
			List<AddBus> addBus = this.addBusService.findAllBus(); // desable previous date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			model.addAttribute("date", dtf.format(now));

			model.addAttribute("bus", addBus);

			return "BusNormal/view_bus";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
			return "BusNormal/view_bus";
		}

	}

	// book Ticket Bage

	@GetMapping("/Book_bus/{fNo}/{date}")
	public String openTicketBookingPage(@PathVariable("fNo") Long fNo, Model model, @PathVariable("date") String date) {
		AddBus BusNo = this.addBusService.findByBusNo(fNo);
		model.addAttribute("addBus", BusNo);
		model.addAttribute("date", date);
		return "BusNormal/book_Ticket";
	}

	// seat Booking static Book_Ticket book_t;

	// seat Booking
		static Bus_Booking_Ticket book_t;

		@PostMapping("/seat_bus")
		public String seatOpen(@ModelAttribute Bus_Booking_Ticket bus_Book_Ticket, Model model) {
			List<Bus_Booking_Ticket> bus_Booking_Tickets = this.bus_Booking_Service.findAllTicket();
			List<Bus_Round_Trip> bus_Round_Trips=this.bus_Round_Trip_Service.findAllTicket();
			
			bus_Round_Trips=bus_Round_Trips.stream()
					.filter(t -> (t.getBusNo() == bus_Book_Ticket.getBusNumber()
					&& t.getTrivalClass().equals(bus_Book_Ticket.getTrivalClass())
					&& t.getDate().compareTo(bus_Book_Ticket.getDate()) == 0))
			.collect(Collectors.toList());
			
			bus_Booking_Tickets = bus_Booking_Tickets.stream()
					.filter(t -> (t.getBusNumber() == bus_Book_Ticket.getBusNumber()
							&& t.getTrivalClass().equals(bus_Book_Ticket.getTrivalClass())
							&& t.getDate().compareTo(bus_Book_Ticket.getDate()) == 0))
					.collect(Collectors.toList());

			if (bus_Booking_Tickets.size() >= 1||bus_Round_Trips.size()>=1) {
				List<String> checkSeat = new ArrayList<String>();

				for (Bus_Booking_Ticket bus_Book_Ticket2 : bus_Booking_Tickets) {
					for (Object seatAdd : bus_Book_Ticket2.getSeatStore()) {
						SeatAdd dd = (SeatAdd) seatAdd;
						checkSeat.add(dd.getItem());
					}
				}
				
//				RoundTrip BookTicket
				for (Bus_Round_Trip bus_round_Trip : bus_Round_Trips) {
					for (Object seatAdd : bus_round_Trip.getSeatStore()) {
						SeatAdd dd = (SeatAdd) seatAdd;
						checkSeat.add(dd.getItem());
					}
				}
				
				model.addAttribute("checkSeat", checkSeat);
			} else {
				model.addAttribute("checkSeat", false);
			}
			
//			if(book_Ticket.getTripType().equals("Round_Trip")) {
//				try {
//					Date d=new SimpleDateFormat("yyyy-MM-dd").parse(book_Ticket.getRe_date());
//					String day=new SimpleDateFormat("EE").format(d);
//					
//					Set<String> set = dat.keySet();
//					Iterator<String> iterator = set.iterator();
//					while (iterator.hasNext()) {
//						String key = iterator.next();
//						if (key.equals(day)) {
//							da_P += dat.get(key);
//							break;
//						}
//					}
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
			
			AddBus addBus = null;
			if (bus_Book_Ticket.getTrivalClass().equals("Ac")) {
			
				addBus = this.addBusService.findByBusNo(bus_Book_Ticket.getBusNumber());
				
				model.addAttribute("totalSeat", addBus.getAc_Seat());
				bus_Book_Ticket.setPrice(bus_Book_Ticket.getPrice() + da_P);

				book_t = bus_Book_Ticket;
			} else if (bus_Book_Ticket.getTrivalClass().equals("NonAc")) {
				addBus = this.addBusService.findByBusNo(bus_Book_Ticket.getBusNumber());
				model.addAttribute("totalSeat", addBus.getNonAc_seat());
				bus_Book_Ticket.setPrice(bus_Book_Ticket.getPrice() + da_P);

				book_t = bus_Book_Ticket;
			}
			return "BusNormal/seat1";
		}


	// seat handler

		@PostMapping("/bus_seat")
		@ResponseBody
		public ResponseEntity<?> seatDetail(@RequestBody SeatAdd data[]) throws IOException {

			if (data.length > 0) {
				if(roundTrip!=null) {
					System.out.println(data.length+"adsjfsfsfff");
				roundTrip.setRound_seatStore(data);
				roundTrip.setRound_Price(roundTrip.getRound_Price() * data.length);
				}else {
					
					book_t.setSeatStore(data);
					book_t.setPrice(book_t.getPrice()*data.length);
				}
			}
			return ResponseEntity.of(Optional.of(data));
		}

		// food Page
		@GetMapping("/food_section_bus")
		public String foodPage() {
			System.out.println(book_t);
			return "BusNormal/food";
		}

		// payment recipt
		static String tempFoods[];
		@PostMapping("/click_pay_bus")
		public String selectedFood(@RequestParam("food") String f_price[], Model model) {
			if (f_price.length > 0) {
				
				double balance = 0.0;

				for (String price : f_price) {

					balance += Double.parseDouble(price);
					System.out.println(price);
				}
				if (book_t!=null&&book_t.getTripType().equals("Round_Trip")) {
					tempFoods=new String[f_price.length];
					for(int i=0;i<f_price.length;i++) {
						tempFoods[i]=f_price[i];	
					}
					
					return "redirect:/user/Round_Trip_Bus/"+book_t.getTo()+"/"+book_t.getFrom()+"/"+book_t.getRe_date()+"/"+book_t.getDate();
				}else if(book_t==null) {
					
					AddBus bus2 = this.addBusService.findByBusNo(roundTrip.getBusNo());
					AddBus bus3 = this.addBusService.findByBusNo(roundTrip.getRound_busNo());
					model.addAttribute("before_food", roundTrip.getPrice());
					model.addAttribute("before_food_Round", roundTrip.getRound_Price());
					
					roundTrip.setRound_Price(roundTrip.getRound_Price()+balance);
					
					model.addAttribute("totalFoodRound", balance);
					
					balance=0.0;
					for(String f: tempFoods) {
						balance+=Double.parseDouble(f);
					}
					roundTrip.setPrice(roundTrip.getPrice()+balance);
					model.addAttribute("totalFood", balance);
					model.addAttribute("numberOfFoodOneTrip", tempFoods);
					model.addAttribute("numberOfFoodRoundTrip", f_price);
					model.addAttribute("book_t", roundTrip);
					
					if (bus2.getAcClass().equals(roundTrip.getTrivalClass())) {
						model.addAttribute("class_price", bus2.getAcPrice());
					} else {
						model.addAttribute("class_price", bus2.getNoAcPrice());
					}
					if (bus3.getAcClass().equals(roundTrip.getRound_trivalClass())) {
						model.addAttribute("Round_class_price", bus3.getAcPrice());
					} else {
						model.addAttribute("Round_class_price", bus3.getNoAcPrice());
					}
					
					
					model.addAttribute("tax", 20.00);
					model.addAttribute("seatPrice", roundTrip.getPrice()+roundTrip.getRound_Price());
					roundTrip.setPrice(roundTrip.getPrice()+10.00);
					roundTrip.setRound_Price(roundTrip.getRound_Price()+10.00);
					model.addAttribute("totalPrice", roundTrip.getPrice()+roundTrip.getRound_Price());
					return "BusNormal/payment_recipt";
					
				}
				AddBus bus = this.addBusService.findByBusNo(book_t.getBusNumber());
				model.addAttribute("before_food", book_t.getPrice());
				
				
				book_t.setPrice(book_t.getPrice() + balance);
				model.addAttribute("totalFood", balance);
				model.addAttribute("numberOfFood", f_price);
				model.addAttribute("book_t", book_t);
				if (bus.getAcClass().equals(book_t.getTrivalClass())) {
					model.addAttribute("class_price", bus.getAcPrice());
				} else {
					model.addAttribute("class_price", bus.getNoAcPrice());
				}
				
			}
			model.addAttribute("tax", 10.00);
			model.addAttribute("seatPrice", book_t.getPrice());
			book_t.setPrice(book_t.getPrice()+10.00);
			return "BusNormal/payment_recipt";
		}

		// payment page
		@GetMapping("/payment_bus")
		public String paymentPage() {

			return "BusNormal/payment";
		}
		static Bus_Booking_Ticket bookticket;
		static Bus_Round_Trip round_trip;
		// pay success
		@PostMapping("/pay_bus")
		public String paymentSuccess(@RequestParam("cardNumber") String cardNumber, @RequestParam("username") String name,
				@RequestParam("cvv") String cvv, @RequestParam("mm") String mm, @RequestParam("yy") String yy,
				Principal principal, HttpSession session) {
			if (cardNumber.length() > 10 && !name.isBlank() && cvv.length() == 3 && mm.length() <= 2 && yy.length() <= 2) {
				String email = principal.getName();
				User findAdmin = this.userService.findAdminAll();

				User user = this.userService.getUserByUserName(email);

				if (findAdmin == null) {
					System.exit(0);
				}
				if(book_t!=null) {
				user.setBalance(user.getBalance() - book_t.getPrice());
				findAdmin.setBalance(findAdmin.getBalance() + book_t.getPrice());
				book_t.setStatus("Booked");
				book_t.setUser(user);
				user.getBus_Book_Tickets().add(book_t);
				
				User user2 = this.userService.saveData(user);
				
				bookticket=user2.getBus_Book_Tickets().get(user2.getBus_Book_Tickets().size()-1);
				this.userService.saveData(findAdmin);
				book_t = null;
				return "BusNormal/payment_success";
				}else if(roundTrip!=null){
					user.setBalance(user.getBalance() - (roundTrip.getPrice()+roundTrip.getRound_Price()));
					findAdmin.setBalance(findAdmin.getBalance() + (roundTrip.getPrice()+roundTrip.getRound_Price()));
					roundTrip.setStatus("Booked");
					roundTrip.setUser(user);
					user.getBus_Round_Trips().add(roundTrip);
					
					User user2 = this.userService.saveData(user);
					
					round_trip=user2.getBus_Round_Trips().get(user2.getBus_Round_Trips().size()-1);
					this.userService.saveData(findAdmin);
					roundTrip = null;
					book_t = null;
					return "BusNormal/payment_success";
				}
			}
			session.setAttribute("message", new Message("Please Fill up Correct Detail !! ", "alert-danger"));
			return "BusNormal/payment";
		}

		// open setting handler
		@GetMapping("/settings_bus")
		public String openSettings() {

			return "BusNormal/settings";
		}

		// change password handler
		@PostMapping("/change-password_bus")
		public String changePassword(@RequestParam("oldPassword") String oldPassword,
				@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {

			String userName = principal.getName();
			User currentUser = this.userService.getUserByUserName(userName);
			if (this.passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
				// change the password
				currentUser.setPassword(this.passwordEncoder.encode(newPassword));
				this.userService.saveData(currentUser);
				session.setAttribute("message",
						new Message("Your Password Is Successfully Updated..... ", "alert-success"));
			} else {
				// error
				session.setAttribute("message", new Message("Please Enter Correct Old Password !! ", "alert-danger"));
				return "redirect:/user/settings_bus";
			}
			return "redirect:/logout";
		}

		// view Ticket
		@GetMapping("/view_bus_booking")
		public String viewBooking(Model model, Principal principal, HttpSession session) {
			model.addAttribute("title", "Show Ticket");

			String name = principal.getName();

			List<Bus_Booking_Ticket> bus_book_Tickets = this.bus_Booking_Service
					.findBookTicketsByUser(userService.getUserByUserName(principal.getName()).getuId());
			if (bus_book_Tickets.isEmpty()) {
				session.setAttribute("message", new Message("You don't have any booking !! ", "alert-danger"));
			} else {
				model.addAttribute("book_Ticket", bus_book_Tickets);
				List<String> dd = new ArrayList<String>();
				for (int i = 0; i < bus_book_Tickets.size(); i++) {
					Bus_Booking_Ticket da = bus_book_Tickets.get(i);
					for (int j = 0; j < da.getSeatStore().length; j++) {
						SeatAdd add = (SeatAdd) da.getSeatStore()[j];
						dd.add(add.getItem());
					}
				}
				model.addAttribute("seat", dd);
			}
			return "BusNormal/view_Booking";
		}

		// cancel Ticket
		@GetMapping("/cancel_bus/{tId}")
		public String cancelBooking(@PathVariable("tId") Long tId, Principal principal, HttpSession session, Model model) {

			Bus_Booking_Ticket bus_Book_Ticket = this.bus_Booking_Service.findById(tId);
			User admin = this.userService.findAdminAll();
			User user = this.userService.getUserByUserName(principal.getName());
			if(bus_Book_Ticket!=null) {
			if (bus_Book_Ticket.getUser().getuId() == user.getuId()) {
				double percent=(bus_Book_Ticket.getPrice()*10)/100;
				percent=bus_Book_Ticket.getPrice()-percent;
				admin.setBalance(admin.getBalance() - percent);
				user.setBalance(user.getBalance() + percent);
				bus_Book_Ticket.setStatus("Canceled");
				model.addAttribute("pass", bus_Book_Ticket);
				
				this.bus_Booking_Service.delete(bus_Book_Ticket);
				this.userService.saveData(user);
				this.userService.saveData(admin);
				session.setAttribute("message", new Message("Ticket Canceled Successfully...\nYour Refundable amount :"+percent+"\nIs Transferred,Please Check Your Account.", "alert-success"));
			}
			
			}else {
				Bus_Round_Trip trip=this.bus_Round_Trip_Service.findById(tId);
				if (trip.getUser().getuId() == user.getuId()) {
					double percent=((trip.getPrice()+trip.getRound_Price())*10)/100;
					percent=(trip.getPrice()+trip.getRound_Price())-percent;
					admin.setBalance(admin.getBalance() - percent);
					user.setBalance(user.getBalance() + percent);
					trip.setStatus("Canceled");
					model.addAttribute("pass", trip);
					
					this.bus_Round_Trip_Service.delete(trip);
					this.userService.saveData(user);
					this.userService.saveData(admin);
					session.setAttribute("message", new Message("Ticket Canceled Successfully...\nYour Refundable amount :"+percent+"\nIs Transferred,Please Check Your Account.", "alert-success"));
				}
			}
			return "BusNormal/eCancel";
		}

		
		//bording pass 
		@GetMapping("/pass_bus")
		public String bordingPass(Model model) {
			
			if(bookticket!=null) {
			List<String> dd = new ArrayList<String>();
			for (int i = 0; i < bookticket.getSeatStore().length; i++) {
				SeatAdd add = (SeatAdd)bookticket.getSeatStore()[i];
				dd.add(add.getItem());
			}
			model.addAttribute("seat", dd);
			model.addAttribute("pass", bookticket);
			
			bookticket=null;
			return "BusNormal/eTicket";
			}else if(round_trip!=null){
				List<String> dd = new ArrayList<String>();
				List<String> dd1 = new ArrayList<String>();
				for (int i = 0; i < round_trip.getSeatStore().length; i++) {
					SeatAdd add = (SeatAdd)round_trip.getSeatStore()[i];
					dd.add(add.getItem());
				}
				
				for (int i = 0; i < round_trip.getRound_seatStore().length; i++) {
					SeatAdd add = (SeatAdd)round_trip.getRound_seatStore()[i];
					dd1.add(add.getItem());
				}
				
				model.addAttribute("seat", dd);
				model.addAttribute("seat1", dd1);
				model.addAttribute("pass", round_trip);
				round_trip=null;
				bookticket=null;
				return "BusNormal/eTicket";
			}
			return "BusNormal/eTicket";
		}
		
		
		
		
		
		
		
		
		
//		Start RoundTrip Work
		@GetMapping("/Round_Trip_Bus/{From}/{To}/{R_date}/{date}")
		public String roundTripViewBus(@PathVariable("From") String From, @PathVariable("To") String To,
				@PathVariable("R_date") String R_date,@PathVariable("date")String date, Model model, HttpSession session) {
			try {
				
				Date d = new SimpleDateFormat("yyyy-MM-dd").parse(R_date);
				String day1 = new SimpleDateFormat("EE").format(d);
				System.out.println(day1);
				boolean b = true;
				List<AddBus> findAllBus = this.addBusService.findAllBus();
				findAllBus = findAllBus.stream().filter(addBus -> (addBus.getFrom().equals(From)
						&& addBus.getTo().equals(To) && (addBus.isEnabled() == b))).collect(Collectors.toList());
				List<AddBus> bus = null;
				if (findAllBus.isEmpty()) {
					session.setAttribute("message", new Message("Bus Not Found !! \nChoose Different Date", "alert-danger"));
					model.addAttribute("From", From);
					model.addAttribute("To", To);
					model.addAttribute("date", date);
					return "BusNormal/Round_Trip_ViewBus";
				} else {
					bus = new ArrayList<AddBus>();
					for (int i = 0; i < findAllBus.size(); i++) {
						c1: for (String day2 : findAllBus.get(i).getDays()) {
							if (day1.equalsIgnoreCase(day2)) {
								bus.add(findAllBus.get(i));
								continue c1;
							}
						}
					}
					if (bus.isEmpty()) {
						session.setAttribute("message", new Message("Bus Not Found !! \nChoose Different Date", "alert-danger"));
						model.addAttribute("From", From);
						model.addAttribute("To", To);
						model.addAttribute("date", date);
						return "BusNormal/Round_Trip_ViewBus";
					}
					model.addAttribute("date1", R_date);
					Set<String> set = dat.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						if (key.equals(day1)) {
							da_P = dat.get(key);
							System.out.println(da_P);
							break;
						}
					}
					model.addAttribute("addBus", bus);
				}

				model.addAttribute("title", "View Bus Page");
				// desable previous date
				model.addAttribute("From", From);
				model.addAttribute("To", To);
				model.addAttribute("date", date);

				return "BusNormal/Round_Trip_ViewBus";
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("message", new Message("Somthing Went Wrong !! " + e.getMessage(), "alert-danger"));
				return "BusNormal/Round_Trip_ViewBus";
			}
			
		}
			
//			RoundTrip Booking
			@GetMapping("/Round_Book_bus/{fNo}/{date}")
			public String openRoundBookingPage(@PathVariable("fNo")Long fNo, Model model, @PathVariable("date") String date) {
				AddBus flightNo = this.addBusService.findByBusNo(fNo);
				model.addAttribute("addFlight", flightNo);
				model.addAttribute("date", date);
				return "BusNormal/Round_Trip";
			}
			
//			roundTrip Seat Open
			static Bus_Round_Trip roundTrip;
			@PostMapping("/Round_seat_bus")
			public String RoundTripSeatOpen(@ModelAttribute Bus_Round_Trip roundTrip_book_Ticket, Model model) {
				List<Bus_Booking_Ticket> book_Tickets = this.bus_Booking_Service.findAllTicket();
				List<Bus_Round_Trip> round_Trips=this.bus_Round_Trip_Service.findAllTicket();
				
				round_Trips=round_Trips.stream()
						.filter(t -> (t.getBusNo() == roundTrip_book_Ticket.getRound_busNo()
						&& t.getTrivalClass().equals(roundTrip_book_Ticket.getRound_trivalClass())
						&& t.getDate().compareTo(roundTrip_book_Ticket.getRe_date()) == 0))
				.collect(Collectors.toList());
				
				book_Tickets = book_Tickets.stream()
						.filter(t -> (t.getBusNumber() == roundTrip_book_Ticket.getRound_busNo()
								&& t.getTrivalClass().equals(roundTrip_book_Ticket.getRound_trivalClass())
								&& t.getDate().compareTo(roundTrip_book_Ticket.getRe_date()) == 0))
						.collect(Collectors.toList());

				if (book_Tickets.size() >= 1||round_Trips.size()>=1) {
					List<String> checkSeat = new ArrayList<String>();
//						Book_Ticket
					for (Bus_Booking_Ticket book_Ticket2 : book_Tickets) {
						for (Object seatAdd : book_Ticket2.getSeatStore()) {
							SeatAdd dd = (SeatAdd) seatAdd;
							checkSeat.add(dd.getItem());
						}
					}
//					RoundTrip BookTicket
					for (Bus_Round_Trip round_Trip : round_Trips) {
						for (Object seatAdd : round_Trip.getSeatStore()) {
							SeatAdd dd = (SeatAdd) seatAdd;
							checkSeat.add(dd.getItem());
						}
					}
					
					model.addAttribute("checkSeat", checkSeat);
				} else {
					model.addAttribute("checkSeat", false);
				}
				
				
				try {
					Date d=new SimpleDateFormat("yyyy-MM-dd").parse(roundTrip_book_Ticket.getRe_date());
					String day=new SimpleDateFormat("EE").format(d);
					
					Set<String> set = dat.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						if (key.equals(day)) {
							da_P= dat.get(key);
							break;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				AddBus addBus = null;
				if (roundTrip_book_Ticket.getRound_trivalClass().equals("Ac")) {
					addBus = this.addBusService.findByBusNo(roundTrip_book_Ticket.getRound_busNo());
					model.addAttribute("totalSeat", addBus.getAc_Seat());
					roundTrip_book_Ticket.setRound_Price(roundTrip_book_Ticket.getRound_Price() + da_P);

					roundTrip = roundTrip_book_Ticket;
					setBook_t_Data_To_RoundTrip_BookTicket();
					book_t=null;
				} else if (roundTrip_book_Ticket.getRound_trivalClass().equals("NonAc")) {
					
					addBus = this.addBusService.findByBusNo(roundTrip_book_Ticket.getRound_busNo());
					model.addAttribute("totalSeat", addBus.getNonAc_seat());
					roundTrip_book_Ticket.setRound_Price(roundTrip_book_Ticket.getRound_Price() + da_P);

					roundTrip = roundTrip_book_Ticket;
					setBook_t_Data_To_RoundTrip_BookTicket();
					book_t=null;
				}
				return "BusNormal/seat1";
			}
			
			public void setBook_t_Data_To_RoundTrip_BookTicket() {
				roundTrip.setBusNo(book_t.getBusNumber());
				roundTrip.setBusName(book_t.getBusName());
				roundTrip.setDate(book_t.getDate());
				roundTrip.setTo(book_t.getTo());
				roundTrip.setFrom(book_t.getFrom());
				roundTrip.setDepatureTime(book_t.getDepatureTime());
				roundTrip.setDuration(book_t.getDuration());
				roundTrip.setArivalTime(book_t.getArivalTime());
				roundTrip.setPrice(roundTrip.getPrice()+book_t.getPrice());
				roundTrip.setTrivalClass(book_t.getTrivalClass());
				roundTrip.setTripType(book_t.getTripType());
				roundTrip.setIntermediate(book_t.getIntermediate());
				roundTrip.setPrice(book_t.getPrice());
				roundTrip.setSeatStore(book_t.getSeatStore());
			}
			
			//RoundTrip view Ticket
			@GetMapping("/Round_view_booking_bus")
			public String viewRoundBooking(Model model, Principal principal, HttpSession session) {
				model.addAttribute("title", "Show Ticket");

				

				List<Bus_Round_Trip> round_Trips = this.bus_Round_Trip_Service
						.findRoundBookingsByUser(userService.getUserByUserName(principal.getName()).getuId());
				if (round_Trips.isEmpty()) {
					session.setAttribute("message", new Message("You don't have any booking !! ", "alert-danger"));
				} else {
					model.addAttribute("book_Ticket", round_Trips);
					List<String> dd = new ArrayList<String>();
					for (int i = 0; i < round_Trips.size(); i++) {
						Bus_Round_Trip da = round_Trips.get(i);
						for (int j = 0; j < da.getSeatStore().length; j++) {
							SeatAdd add = (SeatAdd) da.getSeatStore()[j];
							dd.add(add.getItem());
						}
						List<String> dd1 = new ArrayList<String>();
						for (int j = 0; j < da.getRound_seatStore().length; j++) {
							SeatAdd add = (SeatAdd) da.getRound_seatStore()[j];
							dd1.add(add.getItem());
						}
						dd.addAll(dd1);
					}
					model.addAttribute("seat", dd);
				}
				return "BusNormal/view_Booking";
			}
			
			// video Play hendler
			@GetMapping("/video_bus")
			public String videoPlay(Model model) {
				model.addAttribute("title", "Video Page");

				return "BusNormal/video_play";
			}

}
