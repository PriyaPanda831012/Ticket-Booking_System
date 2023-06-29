package org.airline.bus.Service;

import java.util.List;

import org.airline.bus.Entity.AddBus;
import org.airline.bus.Repositery.AddBusRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AddBusServiceImpl implements AddBusService{
	
	@Autowired
	private AddBusRepositery addBusRepositery;

	@Override
	public AddBus saveBus(AddBus addBus) {
		
		return this.addBusRepositery.save(addBus);
	}

	@Override
	public Page<AddBus> findAllBus(PageRequest pageRequest) {
		
		return this.addBusRepositery.findAll(pageRequest);
	}

	@Override
	public long countRecourd() {
		
		return this.addBusRepositery.count();
	}

	@Override
	public AddBus findByBusNo(Long fId) {
		
		return this.addBusRepositery.findById(fId).get();
	}

	@Override
	public void deleteBus(AddBus addBus) {
		this.addBusRepositery.delete(addBus);
		
	}

	@Override
	public List<AddBus> findAllBus() {
		
		return this.addBusRepositery.findAll();
	}

	@Override
	public List<AddBus> findByBusName(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
