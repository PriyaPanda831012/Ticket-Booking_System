package org.airline.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.airline.Entity.AddFlight;
import org.airline.Entity.User;
import org.airline.Service.AddFlightService;
import org.airline.Service.UserService;
import org.airline.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AddFlightService addFlightService;
	
	@ModelAttribute
	public void addCommanData(Model model,Principal principal) {
		String uname=principal.getName();
		User user=userService.getUserByUserName(uname);
		model.addAttribute("user", user);
		
	}
	
	
	@GetMapping("/das")
	public String adminDasboard(Model model) {
		model.addAttribute("title", "Admin DashBoard");
		return "Admin/admin_dasboard";
	}
	@GetMapping("/newadmin")
	public String openNewAdminForm(Model model,Principal principal) {
		model.addAttribute("title", "Register Admin");
		model.addAttribute("user1", this.userService.getUserByUserName((principal.getName())));
		return "Admin/newadmin";
	}
	@PostMapping("/do_register")
	public String createNewAdmin(@ModelAttribute User user12,Model model,HttpSession session,Principal principal) {
		
		try {
			User u=this.userService.getUserByUserName(principal.getName());
			
			 user12.setRole("ROLE_ADMIN"); 
			 user12.setEnabled(true);
			 user12.setBalance(u.getBalance());
			 user12.setPassword(u.getPassword());
			 
			
			
			User data = this.userService.saveData(user12);
			model.addAttribute("user1",new User());
			session.setAttribute("message", new Message(data.getName()+"\nSuccessfully Updated !! ","alert-success" ));
			return "redirect:/logout";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("user1", user12);
			
				session.setAttribute("message", new Message("Somthing Went Wrong !! "+e.getMessage(),"alert-danger" ));
				return "redirect:/admin/newadmin";
			}
	}
	
	//your profile handler
		@GetMapping("/profile")
		public String yourProfile(Model model) {
			model.addAttribute("title", "Profile Page");
			
			return "Admin/profile";
		}
		@GetMapping("/addFlight")
		public String openAddFlightForm(Model model) {
			model.addAttribute("title", "Add Flight");
			model.addAttribute("addFlight", new AddFlight());
			return "Admin/AddFlight";
		}
		@PostMapping("/add_Flight")
		public String addFlight(@ModelAttribute AddFlight addFlight,Model model,HttpSession session) {
			try {
				long gId=(long)(Math.random()*500000);
				addFlight.setFlightNo(gId);
				addFlight.setEnabled(true);
				AddFlight add_Flight=this.addFlightService.saveFlight(addFlight);
				model.addAttribute("addFlight", new AddFlight());
				session.setAttribute("message", new Message(add_Flight.getFlightName()+"\nSuccessfully Registered !! ","alert-success" ));
				
				return "Admin/AddFlight";
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("addFlight", addFlight);
			
				session.setAttribute("message", new Message("Somthing Went Wrong !! "+e.getMessage(),"alert-danger" ));
				return "Admin/AddFlight";
			}
			
		}
		@GetMapping("/show_flights/{page}")
		public String showFlight(@PathVariable("page")Integer page,Model model) {
			PageRequest pageRequest = PageRequest.of(page, 5);// this is Child class of Pageable interface
			Page<AddFlight> addFlights=this.addFlightService.findAllFlight(pageRequest);
			model.addAttribute("totalPages", addFlights.getTotalPages());
			
			addFlights = new PageImpl<AddFlight>(addFlights.stream()
					.sorted((c1, c2) -> c1.getFlightName().compareToIgnoreCase(c2.getFlightName())).collect(Collectors.toList()));
			
			model.addAttribute("addFlights", addFlights);
			model.addAttribute("currentPage", page);
			long num=this.addFlightService.countRecourd();
			model.addAttribute("numberOfFlight", num);
			return "Admin/admin_dasboard";
			
		}
		
		@GetMapping("/delete/{fId}")
		public String deleteFlight(@PathVariable("fId")Long fId,HttpSession session) {
			AddFlight addFlight=this.addFlightService.findByFlightNo(fId);
			if(addFlight!=null) {
				this.addFlightService.deleteFlight(addFlight);
				session.setAttribute("message",	new Message("Flight Deleted Successfully...","alert-success"));
				
			}
			return "redirect:/admin/show_flights/0";
		}
		
		
		
		//open setting handler
		@GetMapping("/settings")
		public String openSettings() {
			
			return "Admin/settings";
		}
		
		
		//change password handler
		@PostMapping("/change-password")
		public String changePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword,Principal principal,HttpSession session) {

			String userName=principal.getName();
			User currentUser = this.userService.getUserByUserName(userName);
			if(this.passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
				//change the password
				currentUser.setPassword(this.passwordEncoder.encode(newPassword));
				this.userService.saveData(currentUser);
				session.setAttribute("message", new Message("Your Password Is Successfully Updated..... ", "alert-success"));
			}else {
				//error
				session.setAttribute("message", new Message("Please Enter Correct Old Password !! ", "alert-danger"));
				return "redirect:/admin/settings";
			}
			return "redirect:/logout";
		}
		
		@GetMapping("/{fId}/flight")
		public String showFlightDetail(@PathVariable("fId")Long fId,Model model) {
			AddFlight addFlight=this.addFlightService.findByFlightNo(fId);
			model.addAttribute("addFlight", addFlight);
			model.addAttribute("title", addFlight.getFlightName());
			return "Admin/flight_detail";
		}
		
		
		/* flight onOff */
		@PostMapping("/flightOnOf/{fNo}/{cPage}/{onof}")
		public String flightOnOOf(@PathVariable("onof")boolean onof,@PathVariable("fNo")Long fNo ,@PathVariable("cPage")int cPage,HttpSession session) {
				
			AddFlight byFlightNo = this.addFlightService.findByFlightNo(fNo);
				if(onof&&byFlightNo.isEnabled()) {
					byFlightNo.setEnabled(false);
					this.addFlightService.saveFlight(byFlightNo);
					session.setAttribute("message", new Message(byFlightNo.getFlightName()+"\nSuccessfully Flight Closed!!","alert-success" ));
				}else if(!onof&&!byFlightNo.isEnabled()) {
					byFlightNo.setEnabled(true);
					this.addFlightService.saveFlight(byFlightNo);
					session.setAttribute("message", new Message(byFlightNo.getFlightName()+"\nSuccessfully Flight Started !! ","alert-success" ));
				}
			
			return "redirect:/admin/show_flights/"+cPage;
		}
		
//		update  flight
		@GetMapping("/update_flight/{fNo}")
		public String openUpdateFlight(@PathVariable("fNo")Long fNo,Model model) {
			AddFlight addFlight = this.addFlightService.findByFlightNo(fNo);
			model.addAttribute("addFlight", addFlight);
		return "Admin/update_flight";
		}
		@PostMapping("/update_flight")
		public String updateFlight(@ModelAttribute AddFlight addFlight,HttpSession session,Model model) {
			try {
				
				AddFlight add_Flight=this.addFlightService.saveFlight(addFlight);
				
				session.setAttribute("message", new Message(add_Flight.getFlightName()+"\nSuccessfully Updated !! ","alert-success" ));
				
				
				return "redirect:/admin/show_flights/0";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("addFlight", addFlight);
			
				session.setAttribute("message", new Message("Somthing Went Wrong !! "+e.getMessage(),"alert-danger" ));
				return "Admin/update_flight";
			}
			
			
		}
}
