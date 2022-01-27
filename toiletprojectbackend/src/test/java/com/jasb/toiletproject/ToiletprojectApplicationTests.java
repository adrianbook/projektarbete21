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
import java.util.List;

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
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706," +
						"\"cost\":false,\"urinal\":false,\"separateGenders\":false,\"changingTable\":false," +
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

		mockMvc.perform(post("/api/v1/toilets/create")
						.with(user("usr").roles("ADMIN"))
						.contentType("application/json")
						.content(LAT_LONG_JSON))
				.andExpect(status().isCreated());

		List<Toilet> list = toiletRepository.findAll();
		for (Toilet t :
				list) {
			System.out.println("THIS is the toiletid" + t.getId());
		}
		System.out.println("THIS is the toiletuser:" + toiletUserRepo.findToiletUserByUsername("jd"));
		Rating rating = new Rating(toiletRepository.getById(5L),
				toiletUserRepo.findToiletUserByUsername("jd"), 3, "clean");
		ratingRepository.save(rating);
		List<Rating>  ratings = ratingRepository.findAll();
		for (Rating r :
				ratings) {
			System.out.println("This is the rating: " + r.getId());
			System.out.println("This is the toilet in rating: "+ r.getToilet().getId());

		}

		mockMvc.perform(get("/api/v1/toilets/ratings/5")
						.with(user("usr").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
		/*Assertions.assertEquals(3.0, ratingRepository.findAvgRating(5L));*/
	}

	@Test
	void setRatingForToiletTest() throws Exception {
		toiletRepository.save(TOILET_OBJECT_1);
		List<Toilet> list = toiletRepository.findAll();
		for (Toilet t :
				list) {
			System.out.println("This is the toiletId:" + t.getId());
		}

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
		List<Toilet> list = toiletRepository.findAll();
		for (Toilet t :
				list) {
			System.out.println("This is the toiletId: "+t.getId());
		}
		Role role = new Role(8L, "ROLE_APPUSER");
		roleRepo.save(role);
		System.out.println("THis is the role:"+roleRepo.findByName(
				"ROLE_APPUSER"));

		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(9L, "John Doe", "jd",
				"secret", "john.doe@mail.com", false, roles);
		toiletUserRepo.save(toiletUser);
		System.out.println(toiletUserRepo.findToiletUserByUsername("jd").getId());

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
					.content("{\"issue\":\"bad comment\"," +
							"\"notAToilet\":false,\"toiletId\":7}"))
					.andExpect(status().isCreated());
		} catch (ToiletUserNotFoundException e) {
			e.printStackTrace();
		}
	}
}