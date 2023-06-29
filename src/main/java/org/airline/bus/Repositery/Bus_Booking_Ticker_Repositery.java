package org.airline.bus.Repositery;

import java.util.List;

import org.airline.bus.Entity.Bus_Booking_Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Bus_Booking_Ticker_Repositery extends JpaRepository<Bus_Booking_Ticket, Long> {

	@Query("from Bus_Booking_Ticket as t where t.user.uId=:id")
	List<Bus_Booking_Ticket> findBusBookingTicketByUser(@Param("id") Long id);

}
