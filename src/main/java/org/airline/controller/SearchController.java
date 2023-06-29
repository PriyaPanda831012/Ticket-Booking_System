package org.airline.controller;

import java.util.List;

import org.airline.Entity.AddFlight;
import org.airline.Service.AddFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
	@Autowired
	private AddFlightService addFlightService;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query")String query){
		List<AddFlight> addFlights=this.addFlightService.findByFlightName(query);
		return ResponseEntity.ok(addFlights);
	}

}
