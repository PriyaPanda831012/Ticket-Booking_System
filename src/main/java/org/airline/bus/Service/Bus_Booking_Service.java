package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.Bus_Booking_Ticket;

public interface Bus_Booking_Service {

	List<Bus_Booking_Ticket> findAllTicket();

	void delete(Bus_Booking_Ticket bus_Book_Ticket);

	Bus_Booking_Ticket findById(Long tId);

	List<Bus_Booking_Ticket> findBookTicketsByUser(Long getuId);

}
