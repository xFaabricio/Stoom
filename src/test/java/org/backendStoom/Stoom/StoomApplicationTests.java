package org.backendStoom.Stoom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.backendStoom.Stoom.resource.AddressResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoomApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private AddressResource addressResource;
	
	long id;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		id = 22;
	}
	
	public String getPath() {
		return "http://localhost:8080";
	}
	
	@Test
	public void testFindAllAddress() throws Exception {
		mockMvc.perform(get(getPath()+"/addresses")).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testCreateAddress() throws Exception{
		InputStream inputStream = getClass().getResourceAsStream("/create.json");		
		byte[] targetArray = new byte[inputStream.available()];				
		inputStream.read(targetArray);		
		mockMvc.perform(post(getPath()+"/address").contentType(MediaType.APPLICATION_JSON).content(targetArray)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testFindAddress() throws Exception {
		mockMvc.perform(get(getPath()+"/address/"+id)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testUpdateAddress() throws Exception {
		InputStream inputStream = getClass().getResourceAsStream("/update.json");		
		byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);	
		mockMvc.perform(put(getPath()+"/address").contentType(MediaType.APPLICATION_JSON).content(targetArray)).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void testDeleteAddress() throws Exception {
		InputStream inputStream = getClass().getResourceAsStream("/remove.json");		
		byte[] targetArray = new byte[inputStream.available()];
		inputStream.read(targetArray);	
		mockMvc.perform(delete(getPath()+"/address").contentType(MediaType.APPLICATION_JSON).content(targetArray)).andExpect(status().isOk()).andDo(print());
	}
}
