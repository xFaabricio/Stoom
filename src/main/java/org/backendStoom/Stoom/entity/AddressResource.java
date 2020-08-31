package org.backendStoom.Stoom.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/rest")
@Api(value="API Rest Address")
@CrossOrigin(origins="*")
public class AddressResource {

	@Autowired
	AddressRepository addressRepository;
	
	@GetMapping("/addresses")
	@ApiOperation(value="Returns a list of all addresses")
	public List<Address> findAll(){
		return addressRepository.findAll();
	}
	
	@GetMapping("/address/{id}")
	@ApiOperation(value="Returns a single address")
	public Address findAddressById(@PathVariable(value="id") long id) {
		return addressRepository.findById(id);
	}
	
	@PostMapping("/address")
	@ApiOperation(value="Create a new address register")
	public Address saveAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}
	
	@DeleteMapping("/address")
	@ApiOperation(value="Deletes an address record")
	public void deleteAddress(@RequestBody Address address) {
		addressRepository.delete(address);
	}
	
	@PutMapping("/address")
	@ApiOperation(value="Updates an address register")
	public Address updateAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}
	
}
