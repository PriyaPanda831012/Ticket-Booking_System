
  package org.airline.bus.controller;
  
  import java.util.List;
  
  import org.airline.Entity.AddFlight; 
  import org.airline.Service.AddFlightService;
import org.airline.bus.Entity.AddBus;
import org.airline.bus.Service.AddBusService;
import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.ResponseEntity; 
  import org.springframework.web.bind.annotation.GetMapping; 
  import org.springframework.web.bind.annotation.PathVariable; 
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class BusSearchController {
  
  @Autowired 
  private AddBusService addBusService;
  
  @GetMapping("/busSearch/{query}") 
  public ResponseEntity<?> busSearch(@PathVariable("query")String query)
  { 
	  List<AddBus> addBus=this.addBusService.findByBusName(query);
	  return ResponseEntity.ok(addBus); 
	  }
  
  }
 