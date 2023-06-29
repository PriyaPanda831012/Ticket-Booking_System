package org.airline.bus.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.airline.Entity.User;
import org.airline.Service.UserService;
import org.airline.bus.Entity.AddBus;
import org.airline.bus.Service.AddBusService;
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

@Controller
@RequestMapping("/admin")
public class AdminBusController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AddBusService addBusService;
	
	@ModelAttribute
	public void addCommanData(Model model,Principal principal) {
		String uname=principal.getName();
		User user=userService.getUserByUserName(uname);
		model.addAttribute("user", user);
		
	}
	
	

	//your profile handler
	
	@GetMapping("/profile_b")
	public String yourProfile(Model model) {
	 model.addAttribute("title", "Profile Page");
	 return "BusAdmin/profile"; 
	 }
	 
		@GetMapping("/addBus")
		public String openAddBusForm(Model model) {
			model.addAttribute("title", "Add Bus");
			model.addAttribute("addBus", new AddBus());
			return "BusAdmin/AddBus";
		}
		@PostMapping("/add_Bus")
		public String addBus(@ModelAttribute AddBus addBus,Model model,HttpSession session) {
			try {
				long gId=(long)(Math.random()*500000);
				addBus.setBusNo(gId);
				addBus.setEnabled(true);
				AddBus add_Bus=this.addBusService.saveBus(addBus);
				model.addAttribute("addBus", new AddBus());
				session.setAttribute("message", new Message(add_Bus.getBusName()+"\nSuccessfully Registered !! ","alert-success" ));
				
				return "BusAdmin/AddBus";
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("addBus", addBus);
			
				session.setAttribute("message", new Message("Somthing Went Wrong !! "+e.getMessage(),"alert-danger" ));
				return "BusAdmin/AddBus";
			}
			
		}
		@GetMapping("/show_bus/{page}")
		public String showBus(@PathVariable("page")Integer page,Model model) {
			PageRequest pageRequest = PageRequest.of(page, 5);// this is Child class of Pageable interface
			Page<AddBus> addBuss=this.addBusService.findAllBus(pageRequest);
			model.addAttribute("totalPages", addBuss.getTotalPages());
			
			addBuss = new PageImpl<AddBus>(addBuss.stream()
					.sorted((c1, c2) -> c1.getBusName().compareToIgnoreCase(c2.getBusName())).collect(Collectors.toList()));
			
			model.addAttribute("addBuss", addBuss);
			model.addAttribute("currentPage", page);
			long num=this.addBusService.countRecourd();
			model.addAttribute("numberOfBus", num);
			return "BusAdmin/admin_dasboard";
			
		}
		
		@GetMapping("/delete_bus/{bId}")
		public String deleteBus(@PathVariable("bId")Long fId,HttpSession session) {
			AddBus addBus=this.addBusService.findByBusNo(fId);
			if(addBus!=null) {
				this.addBusService.deleteBus(addBus);
				session.setAttribute("message",	new Message("Bus Deleted Successfully...","alert-success"));
				
			}
			return "redirect:/BusAdmin/show_bus/0";
		}
		
		
		
	
		  //open setting handler
		  
		  @GetMapping("/settings_bus")
		  public String openSettings() {
		  return "BusAdmin/settings"; 
		  }
		 
		
		
		
		@GetMapping("/{bId}/bus")
		public String showBusDetail(@PathVariable("bId")Long bId,Model model) {
			AddBus addBus=this.addBusService.findByBusNo(bId);
			model.addAttribute("addBus", addBus);
			model.addAttribute("title", addBus.getBusName());
			return "BusAdmin/bus_detail";
		}
		
		
		/* bus onOff */
		@PostMapping("/busOnOf/{bNo}/{cPage}/{onof}")
		public String busOnOOf(@PathVariable("onof")boolean onof,@PathVariable("bNo")Long bNo ,@PathVariable("cPage")int cPage,HttpSession session) {
				
			AddBus byBusNo = this.addBusService.findByBusNo(bNo);
				if(onof&&byBusNo.isEnabled()) {
					byBusNo.setEnabled(false);
					this.addBusService.saveBus(byBusNo);
					session.setAttribute("message", new Message(byBusNo.getBusName()+"\nSuccessfully Bus Closed!!","alert-success" ));
				}else if(!onof&&!byBusNo.isEnabled()) {
					byBusNo.setEnabled(true);
					this.addBusService.saveBus(byBusNo);
					session.setAttribute("message", new Message(byBusNo.getBusName()+"\nSuccessfully Bus Started !! ","alert-success" ));
				}
			
			return "redirect:/admin/show_bus/"+cPage;
		}
		
//		update  Bus
		@GetMapping("/update_bus/{bNo}")
		public String openUpdateBus(@PathVariable("bNo")Long bNo,Model model) {
			AddBus addBus = this.addBusService.findByBusNo(bNo);
			model.addAttribute("addBus", addBus);
		return "BusAdmin/update_bus";
		}
		@PostMapping("/update_bus")
		public String updateBus(@ModelAttribute AddBus addBus,HttpSession session,Model model) {
			try {
				
				AddBus add_Bus=this.addBusService.saveBus(addBus);
				
				session.setAttribute("message", new Message(add_Bus.getBusName()+"\nSuccessfully Updated !! ","alert-success" ));
				
				
				return "redirect:/admin/show_bus/0";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("addBus", addBus);
			
				session.setAttribute("message", new Message("Somthing Went Wrong !! "+e.getMessage(),"alert-danger" ));
				return "BusAdmin/update_bus";
			}
			
			
		}
}
