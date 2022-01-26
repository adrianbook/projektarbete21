package com.jasb.toiletproject;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.RoleRepo;
import com.jasb.toiletproject.repo.ToiletRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.jasb.toiletproject.repo.ToiletUserRepo;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class ToiletprojectApplicationTests {
	private static final String LAT_LONG_JSON = "{\"latitude\":57.706,\"longitude\":11.937}";
	private final static Toilet TOILET_OBJECT_1 = new Toilet(1L, 11.937, 57.706,false,
			false, false, false, false, 0.0);
	private final static Toilet TOILET_OBJECT_2 = new Toilet(2L, 57.000, 11.900, false,
			false, false, false, false, 0.0);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ToiletRepository toiletRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ToiletUserRepo toiletUserRepo;
	@Autowired
	private RoleRepo roleRepo;

	@Test
	void getToiletByIdTest() throws Exception {
		toiletRepository.save(TOILET_OBJECT_1); //toiletId = 1 in db
		mockMvc.perform(get("/api/v1/toilets/1")
						.with(user("usr").roles("APPUSER"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706," +
						"\"urinal\":false,\"separateGenders\":false,\"changingTable\":false," +
						"\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":1}"));;
	}

	@Test
	void addToiletTest() throws Exception {
		mockMvc.perform(post("/api/v1/toilets/create")
						.with(user("usr").roles("APPUSER")) //roleId=1 in db
						.contentType(MediaType.APPLICATION_JSON)
						.content(LAT_LONG_JSON)) //toiletId = 2 in db
				.andExpect(status().isCreated())
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706," +
						"\"urinal\":false,\"separateGenders\":false,\"changingTable\":false," +
						"\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":2}"));
	}

	@Test
	void getAllToiletsTest() throws Exception {
		toiletRepository.save(TOILET_OBJECT_1);
		toiletRepository.save(TOILET_OBJECT_2);
		mockMvc.perform(get("/api/v1/toilets/getalltoilets")
				.with(user("usr"))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string("{\"toilets\":" +
						"[{\"longitude\":11.937,\"latitude\":57.706,\"urinal\":false,\"separateGenders\":false," +
						"\"changingTable\":false,\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":7}," +
						"{\"longitude\":57.0,\"latitude\":11.9,\"urinal\":false,\"separateGenders\":false," +
						"\"changingTable\":false,\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":8}]}"));
	}

	@Test
	void getAllRatingsForToiletTest() throws Exception {
		Role role = new Role(3L, "ROLE_APPUSER");
		roleRepo.save(role);

		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(1L, "John Doe", "jd",
					"secret", "john.doe@mail.com", false, roles);
		toiletUserRepo.save(toiletUser);

		mockMvc.perform(post("/api/v1/toilets/create")
						.with(user("usr").roles("ADMIN"))
						.contentType("application/json")
						.content(LAT_LONG_JSON))
				.andExpect(status().isCreated());

			Rating rating = new Rating(toiletRepository.getById(5L),
					toiletUserRepo.findToiletUserByUsername("jd"), 3, "clean");
			ratingRepository.save(rating);

			mockMvc.perform(get("/api/v1/toilets/5/rating")
							.with(user("usr").roles("ADMIN"))
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isAccepted());
		Assertions.assertEquals(3.0, ratingRepository.findAvgRating(5L));
	}

	@Test
	void setRatingForToiletTest() throws Exception{
		// TODO: 2022-01-26
	}

	@Test
	void reportToiletTest() throws Exception{
		// TODO: 2022-01-25 userdetails... 
	}
}