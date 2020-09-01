package org.backendStoom.Stoom.resource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.backendStoom.Stoom.entity.Address;
import org.backendStoom.Stoom.repository.AddressRepository;
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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rest")
@Api(value = "API Rest Address")
@CrossOrigin(origins = "*")
public class AddressResource {

	// https://maps.googleapis.com/maps/api/geocode/json?address=&key=AIzaSyAR0Gg1As2fFuhfF7Z_n6ors5eQZC1nAnI

	@Autowired
	AddressRepository addressRepository;

	@GetMapping("/addresses")
	@ApiOperation(value = "Returns a list of all addresses")
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@GetMapping("/address/{id}")
	@ApiOperation(value = "Returns a single address")
	public Address findAddressById(@PathVariable(value = "id") long id) {
		return addressRepository.findById(id);
	}

	@PostMapping("/address")
	@ApiOperation(value = "Create a new address register")
	public Address saveAddress(@RequestBody Address address) {

		if (address.getLatitude() == null || address.getLongitude() == null || address.getLatitude().equals("")
				|| address.getLongitude().equals("")) {

			String searchLatitudeAndLongitude = "";
			int countInformation = 0;

			if (address.getStreetName() != null && !address.getStreetName().equals("")) {
				searchLatitudeAndLongitude += address.getStreetName();
				countInformation++;
			}

			if (address.getNumber() != null) {
				if (countInformation >= 1) {
					searchLatitudeAndLongitude += ", " + address.getNumber();
					countInformation++;
				} else {
					searchLatitudeAndLongitude += address.getNumber();
					countInformation++;
				}
			}

			if (address.getCity() != null && !address.getCity().equals("")) {
				if (countInformation >= 1) {
					searchLatitudeAndLongitude += ", " + address.getCity();
					countInformation++;
				} else {
					searchLatitudeAndLongitude += address.getCity();
					countInformation++;
				}
			}

			if (address.getState() != null && !address.getState().equals("")) {
				if (countInformation >= 1) {
					searchLatitudeAndLongitude += ", " + address.getState();
					countInformation++;
				} else {
					searchLatitudeAndLongitude += address.getState();
					countInformation++;
				}
			}

			if (address.getCountry() != null && !address.getCountry().equals("")) {
				if (countInformation >= 1) {
					searchLatitudeAndLongitude += ", " + address.getCountry();
					countInformation++;
				} else {
					searchLatitudeAndLongitude += address.getCountry();
					countInformation++;
				}
			}

			HttpURLConnection connection = null;
			
			try {				  				
				// Create connection
				String stringUrl = "https://maps.googleapis.com/maps/api/geocode/json?language=pt&address=" + searchLatitudeAndLongitude + "&key=AIzaSyAR0Gg1As2fFuhfF7Z_n6ors5eQZC1nAnI"; 
				URL url = new URL(stringUrl.replace(" ","%"));
				String urlParameters = "?language=pt&address=" + searchLatitudeAndLongitude + "&key=AIzaSyAR0Gg1As2fFuhfF7Z_n6ors5eQZC1nAnI";
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Content-Type", "application/json");

				connection.setRequestProperty("Content-Length", Integer.toString(3));
				connection.setRequestProperty("Content-Language", "en-US");

				connection.setUseCaches(false);
				connection.setDoOutput(true);

				// Send request
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.close();

				// Get Response
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
//				System.out.println(response.toString());
				
				JsonElement jsonElement = new JsonParser().parse(response.toString());		        
		        JsonObject jsonObject = jsonElement.getAsJsonObject();
		        JsonObject latitudeAndLongitude = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
		        		        
		        latitudeAndLongitude.get("lat");
		        latitudeAndLongitude.get("lng");
		        
				System.out.println(latitudeAndLongitude.get("lat"));
				System.out.println(latitudeAndLongitude.get("lng"));
				
				address.setLatitude(latitudeAndLongitude.get("lat").getAsString());
				address.setLongitude(latitudeAndLongitude.get("lng").getAsString());
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}

		}

		return addressRepository.save(address);
	}

	@DeleteMapping("/address")
	@ApiOperation(value = "Deletes an address record")
	public void deleteAddress(@RequestBody Address address) {
		addressRepository.delete(address);
	}

	@PutMapping("/address")
	@ApiOperation(value = "Updates an address register")
	public Address updateAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}

}
