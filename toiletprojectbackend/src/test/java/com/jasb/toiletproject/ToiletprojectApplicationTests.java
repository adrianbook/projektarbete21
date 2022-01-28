package com.jasb.toiletproject;

import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.RoleRepo;
import com.jasb.toiletproject.repo.ToiletRepository;

import com.jasb.toiletproject.util.ToiletUserFetcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import com.jasb.toiletproject.repo.ToiletUserRepo;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A test class for integrations tests by mocking calls to REST-endpoints.
 * Uses an in-memory embedded H2 database.
 *
 * Please note that the somewhat unintuitive id numbers returned is the
 * result of the database setup - to automatically generate new id's for
 * every new post in the database. Even thought the actual data is rolled
 * backed att the closing of each method.
 *
 * Written by JASB
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class ToiletprojectApplicationTests {
	private static final String LAT_LONG_JSON = "{\"latitude\":57.706,\"longitude\":11.937}";
	private static final Toilet TOILET_OBJECT_1 = new Toilet(1L, 11.937, 57.706, false,
			false, false, false, false, false, 0.0);
	private static final Toilet TOILET_OBJECT_2 = new Toilet(2L, 57.000, 11.900, false,
			false, false, false, false, false, 0.0);


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
		toiletRepository.save(TOILET_OBJECT_1);
		mockMvc.perform(get("/api/v1/toilets/1")
						.with(user("usr").roles("APPUSER"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706," +
						"\"cost\":false,\"urinal\":false,\"separateGenders\":false,\"changingTable\":false," +
						"\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":1}"));;
	}

	@Test
	void addToiletTest() throws Exception {
		mockMvc.perform(post("/api/v1/toilets/create")
						.with(user("usr").roles("APPUSER"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(LAT_LONG_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(11.937))
				.andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(57.706))
				.andExpect(MockMvcResultMatchers.jsonPath("$.separateGenders").value(false))
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706," +
						"\"cost\":false,\"urinal\":false,\"separateGenders\":false,\"changingTable\":false," +
						"\"shower\":false,\"handicapFriendly\":false,\"avgRating\":0.0,\"id\":2}"));
		Assertions.assertEquals(false, toiletRepository.getById(2L).isHandicapFriendly());
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
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[0].longitude").value(11.937))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[0].latitude").value(57.706))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[0].cost").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[1].longitude").value(57.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[1].latitude").value(11.9))
				.andExpect(MockMvcResultMatchers.jsonPath("$.toilets[1].cost").value(false))
				.andExpect(content().string("{\"toilets\":[{" +
						"\"longitude\":11.937,\"latitude\":57.706,\"cost\":false,\"urinal\":false," +
						"\"separateGenders\":false,\"changingTable\":false,\"shower\":false," +
						"\"handicapFriendly\":false,\"avgRating\":0.0," +
						"\"id\":15}," +
						"{\"longitude\":57.0,\"latitude\":11.9,\"cost\":false,\"urinal\":false," +
						"\"separateGenders\":false,\"changingTable\":false,\"shower\":false," +
						"\"handicapFriendly\":false,\"avgRating\":0.0," +
						"\"id\":16}]}"));
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
		toiletRepository.save(TOILET_OBJECT_1);
		Rating rating = new Rating(toiletRepository.getById(5L),
				toiletUserRepo.findToiletUserByUsername("jd"), 3, "clean");
		ratingRepository.save(rating);

		mockMvc.perform(get("/api/v1/toilets/ratings/5")
						.with(user("usr").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].notes").value("clean"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].toiletUser..name").value("John Doe"));
	}

	@Test
	void setRatingForToiletTest() throws Exception {
		toiletRepository.save(TOILET_OBJECT_1);
		Role role = new Role(12L, "ROLE_APPUSER");
		roleRepo.save(role);
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(13L, "John Doe", "jd",
				"secret", "john.doe@mail.com", false, roles);
		toiletUserRepo.save(toiletUser);

		try (MockedStatic<ToiletUserFetcher> theMock =
					 Mockito.mockStatic(ToiletUserFetcher.class)) {
			theMock.when(() -> ToiletUserFetcher.fetchToiletUserByUsername("jd"))
					.thenReturn(toiletUser);
			Assertions.assertEquals(toiletUser,
					ToiletUserFetcher.fetchToiletUserByUsername("jd"));
			theMock.when(ToiletUserFetcher::fetchToiletUserByContext)
					.thenReturn(toiletUser);
			Assertions.assertEquals(toiletUser, ToiletUserFetcher.fetchToiletUserByContext());

			mockMvc.perform(put("/api/v1/toilets/rate")
					.with(user("usr").roles("ADMIN"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"rating\":5,\"notes\":\"clean\",\"toiletId\":11}"))
					.andExpect(status().isCreated())
					.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5))
					.andExpect(MockMvcResultMatchers.jsonPath("$.toiletUser.username").value("jd"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.toilet.avgRating").value(5.0));
		} catch (ToiletUserNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	void reportToiletTest() throws Exception{
		toiletRepository.save(TOILET_OBJECT_1);
		Role role = new Role(8L, "ROLE_APPUSER");
		roleRepo.save(role);
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(9L, "John Doe", "jd",
				"secret", "john.doe@mail.com", false, roles);
		toiletUserRepo.save(toiletUser);

		try (MockedStatic<ToiletUserFetcher> theMock =
					 Mockito.mockStatic(ToiletUserFetcher.class)) {
			theMock.when(() -> ToiletUserFetcher.fetchToiletUserByUsername("jd"))
					.thenReturn(toiletUser);
			Assertions.assertEquals(toiletUser,
					ToiletUserFetcher.fetchToiletUserByUsername("jd"));
			theMock.when(ToiletUserFetcher::fetchToiletUserByContext)
					.thenReturn(toiletUser);
			Assertions.assertEquals(toiletUser, ToiletUserFetcher.fetchToiletUserByContext());

			mockMvc.perform(post("/api/v1/toilets/reports/report")
					.with(user("usr").roles("APPUSER"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"issue\":\"bad comment\",\"notAToilet\":false,\"toiletId\":7}"))
					.andExpect(status().isCreated())
					.andExpect(MockMvcResultMatchers.jsonPath("$.issue").value("bad comment"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.owningUser.username").value("jd"));
		} catch (ToiletUserNotFoundException e) {
			e.printStackTrace();
		}
	}
}