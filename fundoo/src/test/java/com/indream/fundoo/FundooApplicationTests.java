package com.indream.fundoo;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.indream.fundoo.configuration.AppConfiguration;

/**
 * @author Akshay
 *
 */

	
	
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AppConfiguration.class)
public class FundooApplicationTests{

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void loginTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/userapplication/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"akshayguruprasad@gmail.com\", \"password\" : \"ABC\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
}

	
}
