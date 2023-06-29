package org.airline.Dao;

import java.util.List;

import org.airline.Entity.Book_Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Book_TicketRepository extends JpaRepository<Book_Ticket, Long>{

	@Query("from Book_Ticket as t where t.user.uId=:id")
	public List<Book_Ticket> findBookTicketsByUser(@Param("id")Long id);
}
