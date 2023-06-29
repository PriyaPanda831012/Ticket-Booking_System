package org.airline.Dao;

import java.util.List;

import org.airline.Entity.AddFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AddFlightRepository extends JpaRepository<AddFlight, Long>{

	List<AddFlight> findByFlightNameContaining(String query);

}
