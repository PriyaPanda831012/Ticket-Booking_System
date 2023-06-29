package org.airline.bus.Repositery;

import org.airline.bus.Entity.AddBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddBusRepositery extends JpaRepository<AddBus, Long> {

}
