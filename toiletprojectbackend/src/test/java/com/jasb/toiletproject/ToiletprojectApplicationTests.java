package com.jasb.toiletproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.ToiletRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ToiletprojectApplicationTests {
	private final static String TEST_USER_ID = "user-id-123";
	private final static long TOILET_ID = 1;
	private final static double LATITUDE = 57.706;
	private final static double LONGITUDE = 11.937;
	private final static double AVG_RATING_PLACEHOLDER = 0.0;
	private final static String NOTES = "Important comment";
	private final static int RATING = 3;
	private final static double AVG_RATING = 3.0;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ToiletRepository toiletRepository;

	@Test
	void addToiletTest() throws Exception {
		Toilet toilet = new Toilet(TOILET_ID, LONGITUDE, LATITUDE, AVG_RATING_PLACEHOLDER);
		/*Rating rating = new Rating(TOILET_ID, RATING, NOTES);
		ToiletUser toiletUser = new ToiletUser(1,"name", "username",
				"password", "email", false, new ArrayList<>());
		rating.setToiletUser(toiletUser);
		rating.setToilet(toilet);*/
		mockMvc.perform(post("/api/v1/toilets/create", 1)
						.with(user(TEST_USER_ID))
						.with(csrf())
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(toilet)))
				.andExpect(status().isCreated());
		/*mockMvc.perform(put("/api/v1/toilets/rate")
				.with(user(TEST_USER_ID))
				.with(csrf())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(rating)))
				.andExpect(status().isCreated());*/
		Optional<Toilet> optionalToilet = toiletRepository.findById(TOILET_ID);
		Assertions.assertTrue(optionalToilet.isPresent());
		Assertions.assertEquals(optionalToilet.get().getId(), TOILET_ID);
		Assertions.assertEquals(optionalToilet.get().getLatitude(), LATITUDE);
		Assertions.assertEquals(optionalToilet.get().getLongitude(), LONGITUDE);
		/*Assertions.assertEquals(optionalToilet.get().getAvgRating(), AVG_RATING);*/

	}

	@Test
	void getAllToiletsTest() throws Exception {
		mockMvc.perform(get("/api/v1/toilets/getalltoilets")
				.contentType("application/json"))
				.andExpect(status().isOk());
		List<Toilet> toilets = toiletRepository.findAll();
		Assertions.assertEquals(toilets.size(), 1);
	}

	@Test
	void get


}
