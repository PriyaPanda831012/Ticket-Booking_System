package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.AddBus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AddBusService {

	AddBus saveBus(AddBus addBus);

	Page<AddBus> findAllBus(PageRequest pageRequest);

	long countRecourd();

	AddBus findByBusNo(Long fId);

	void deleteBus(AddBus addBus);

	List<AddBus> findAllBus();

	List<AddBus> findByBusName(String query);

}
