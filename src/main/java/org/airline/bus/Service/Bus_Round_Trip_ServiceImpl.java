package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.Bus_Round_Trip;
import org.airline.bus.Repositery.Bus_Round_Trip_Repositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bus_Round_Trip_ServiceImpl implements Bus_Round_Trip_Service {
	
	@Autowired
	private Bus_Round_Trip_Repositery bus_Round_Trip_Repositery;

	@Override
	public List<Bus_Round_Trip> findRoundBookingsByUser(Long getuId) {
		
		return this.bus_Round_Trip_Repositery.findRoundBookingsByUser(getuId);
	}

	@Override
	public List<Bus_Round_Trip> findAllTicket() {
		
		return this.bus_Round_Trip_Repositery.findAll();
	}

	@Override
	public void delete(Bus_Round_Trip trip) {
		this.bus_Round_Trip_Repositery.delete(trip);
		
	}

	@Override
	public Bus_Round_Trip findById(Long tId) {
		
		return this.bus_Round_Trip_Repositery.findById(tId).get();
	}

}
