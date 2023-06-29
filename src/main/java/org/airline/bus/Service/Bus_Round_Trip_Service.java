package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.Bus_Round_Trip;

public interface Bus_Round_Trip_Service {

	List<Bus_Round_Trip> findRoundBookingsByUser(Long getuId);

	List<Bus_Round_Trip> findAllTicket();

	void delete(Bus_Round_Trip trip);

	Bus_Round_Trip findById(Long tId);

}
