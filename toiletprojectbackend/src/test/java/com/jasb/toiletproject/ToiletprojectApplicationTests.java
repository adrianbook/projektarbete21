package com.jasb.toiletproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasb.entities.Toilet;
import com.jasb.toiletproject.repo.ToiletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ToiletprojectApplicationTests {
	private final static String TEST_USER_ID = "user-id-123";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ToiletRepository toiletRepository;

	@Test
	void registrationThatWorksThroughAllLayers() throws Exception {
		Toilet toilet = new Toilet(1,11.000, 12.000, 3.5);
		mockMvc.perform(post("/api/v1/toilets/create", 1)
				.with(user(TEST_USER_ID))
				.with(csrf())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(toilet)))
				.andExpect(status().isCreated());
		Optional<Toilet> optionalToilet = toiletRepository.findById(1L);
		Assertions.assertTrue(optionalToilet.isPresent());
		Assertions.assertEquals(optionalToilet.get().getLatitude(), 12.00);
	}
}
