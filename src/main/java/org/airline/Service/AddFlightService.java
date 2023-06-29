package org.airline.Service;

import java.util.List;

import org.airline.Entity.AddFlight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AddFlightService {

	AddFlight saveFlight(AddFlight addFlight);

	Page<AddFlight> findAllFlight(PageRequest pageRequest);

	long countRecourd();

	AddFlight findByFlightNo(Long fId);

	void deleteFlight(AddFlight addFlight);

	List<AddFlight> findByFlightName(String query);

	List<AddFlight> findAllFlights();

}
