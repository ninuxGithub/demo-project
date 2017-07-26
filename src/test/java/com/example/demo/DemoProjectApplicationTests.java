package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoProjectApplicationTests {
	private MockMvc mvc;

	MockHttpSession session;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setupMockMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		session = new MockHttpSession();
	}

	@Test
	public void contextLoads() throws Exception {
		RequestBuilder request = get("/orderList");
		mvc.perform(request).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(MockMvcResultHandlers.print())
				.andReturn();
		mvc.perform(get("/orders/")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		log.info("info....");
		// mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
	}

}
