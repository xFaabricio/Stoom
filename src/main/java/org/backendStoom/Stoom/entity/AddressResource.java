package org.backendStoom.Stoom.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/rest")
public class AddressResource {

	@Autowired
	AddressRepository addressRepository;
	
	@GetMapping("/adresses")
	public List<Address> findAll(){
		return addressRepository.findAll();
	}
	
	@GetMapping("/address/{id}")
	public Address findAddressById(@PathVariable(value="id") long id) {
		return addressRepository.findById(id);
	}
	
	@PostMapping("/address")
	public Address saveAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}
	
	@DeleteMapping("/address")
	public void deleteAddress(@RequestBody Address address) {
		addressRepository.delete(address);
	}
	
	@PutMapping("/address")
	public Address updateAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}
	
}
