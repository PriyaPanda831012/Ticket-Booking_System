package org.airline.bus.Repositery;

import java.util.List;

import org.airline.bus.Entity.Bus_Round_Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface Bus_Round_Trip_Repositery extends JpaRepository<Bus_Round_Trip, Long> {

	@Query("from Bus_Round_Trip as t where t.user.uId=:id")
	List<Bus_Round_Trip> findRoundBookingsByUser(@Param("id")Long id);

}
