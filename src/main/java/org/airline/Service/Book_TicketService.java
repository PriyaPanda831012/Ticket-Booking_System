package org.airline.Service;

import java.util.List;

import org.airline.Entity.Book_Ticket;

public interface Book_TicketService {

	List<Book_Ticket> findBookTicketsByUser(Long getuId);

	Book_Ticket findById(Long tId);

	void delete(Book_Ticket book_Ticket);

	List<Book_Ticket> findAllTicket();

}
