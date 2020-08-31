package org.backendStoom.Stoom.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findById(long id);
	
}
