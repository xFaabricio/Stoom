package org.backendStoom.Stoom.repository;

import org.backendStoom.Stoom.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findById(long id);
	
}
