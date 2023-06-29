package org.airline.Service;

import java.util.List;

import org.airline.Dao.Book_TicketRepository;
import org.airline.Entity.Book_Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class Book_TicketServiceImpl implements Book_TicketService {
	
	@Autowired
	private Book_TicketRepository book_TicketRepository;
	
	@Override
	public List<Book_Ticket> findBookTicketsByUser(Long getuId) {
		System.out.println(getuId);
		return this.book_TicketRepository.findBookTicketsByUser(getuId);
	}

	@Override
	public Book_Ticket findById(Long tId) {
		
		return this.book_TicketRepository.findById(tId).isPresent()? this.book_TicketRepository.findById(tId).get():null;
	}

	@Override
	public void delete(Book_Ticket book_Ticket) {
		this.book_TicketRepository.delete(book_Ticket);
		
	}

	@Override
	public List<Book_Ticket> findAllTicket() {
		
		return this.book_TicketRepository.findAll();
	}

}
