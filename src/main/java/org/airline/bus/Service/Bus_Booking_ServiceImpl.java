package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.Bus_Booking_Ticket;
import org.airline.bus.Repositery.Bus_Booking_Ticker_Repositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bus_Booking_ServiceImpl implements Bus_Booking_Service {

	@Autowired
	private Bus_Booking_Ticker_Repositery bus_Booking_Ticker_Repositery;

	@Override
	public List<Bus_Booking_Ticket> findAllTicket() {
	
		return this.bus_Booking_Ticker_Repositery.findAll();
	}

	@Override
	public void delete(Bus_Booking_Ticket bus_Book_Ticket) {
		this.bus_Booking_Ticker_Repositery.delete(bus_Book_Ticket);
		
	}

	@Override
	public Bus_Booking_Ticket findById(Long tId) {
		
		return this.bus_Booking_Ticker_Repositery.findById(tId).get();
	}

	@Override
	public List<Bus_Booking_Ticket> findBookTicketsByUser(Long getuId) {
		
		return this.bus_Booking_Ticker_Repositery.findBusBookingTicketByUser(getuId);
	}
	
}
