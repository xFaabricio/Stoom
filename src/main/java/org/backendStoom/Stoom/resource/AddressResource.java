package org.backendStoom.Stoom.resource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.backendStoom.Stoom.entity.Address;
import org.backendStoom.Stoom.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/")
@Api(value = "API Rest Address")
@CrossOrigin(origins = "*")
public class AddressResource {

	private static final Logger logger = LogManager.getLogger(AddressResource.class);
	
	@Autowired
	AddressRepository addressRepository;

	/**
	 * Este metodo tem como objetivo retornar todos endereços cadastrados
	 * 
	 * */
	
	@GetMapping("/addresses")
	@ApiOperation(value = "Returns a list of all addresses")
	public List<Address> findAll() {
		logger.info("Retornando lista de Endereços");
		return addressRepository.findAll();
	}

	/**
	 * Este metodo tem como objetivo um endereço cadastrado,
	 * sendo retornado o endereço em que foi informado o id como parametro.
	 * 
	 * */
	
	@GetMapping("/address/{id}")
	@ApiOperation(value = "Returns a single address")
	public Address findById(@PathVariable(value = "id") long id) {
		logger.info("Retornando o endereço de id " +id);
		return addressRepository.findById(id);
	}

	/**
	 * Este metodo tem como objetivo 
	 * de realizar o cadastro de um endereço
	 * */
	
	@PostMapping("/address")
	@ApiOperation(value = "Create a new address register")
	public Address save(@RequestBody Address address) {
		logger.info("Criando o novo Endereço");
		setAddressLatitudeAndLongitude(address);
		return addressRepository.save(address);
	}

	/**
	 * Este metodo tem como objetivo 
	 * de apagar um endereço
	 * */
	
	@DeleteMapping("/address")
	@ApiOperation(value = "Deletes an address record")
	public void delete(@RequestBody Address address) {
		logger.info("Deletando o Endereço");
		addressRepository.delete(address);
	}

	/**
	 * Este metodo tem como objetivo 
	 * de atualizar endereço
	 * */
	
	@PutMapping("/address")
	@ApiOperation(value = "Updates an address register")
	public Address update(@RequestBody Address address) {
		logger.info("Atualizando o endereço");
		return addressRepository.save(address);
	}

	/**
	 * Este metodo tem como objetivo 
	 * de pesquisar a latitude e longitude quando
	 * não informado os valores latitude e longitude na criação
	 * 
	 * A pesquisa é feita com o Google Geocoding API
	 * */
	
	public Address setAddressLatitudeAndLongitude(Address address) {
		
		if (address.getLatitude() == null || address.getLongitude() == null || address.getLatitude().equals("")
				|| address.getLongitude().equals("")) {

			logger.info("Identificado endereço sem Longitude e Latidude");
			logger.info("Buscando informações...");
			
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
				
				JsonElement jsonElement = new JsonParser().parse(response.toString());		        
		        JsonObject jsonObject = jsonElement.getAsJsonObject();
		        JsonObject latitudeAndLongitude = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
		        		        
		        latitudeAndLongitude.get("lat");
		        latitudeAndLongitude.get("lng");		      
				
				logger.info("Informações encontradas, Latitude: " + latitudeAndLongitude.get("lat") + " Longitude: " + latitudeAndLongitude.get("lng"));
				
				address.setLatitude(latitudeAndLongitude.get("lat").getAsString());
				address.setLongitude(latitudeAndLongitude.get("lng").getAsString());
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.fatal("Erro ao se comunicar com API Google Geocoding ");
				returnError();
				return null;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}

		}

		return address;
		
	}
	
	public ResponseEntity<?> returnError() {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
