package com.jasb.toiletproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasb.entities.Rating;
import com.jasb.entities.Role;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.exceptions.ToiletUserNotFoundException;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.service.report.ReportService;
import com.jasb.toiletproject.service.toiletuser.ToiletUserService;
import com.jasb.toiletproject.util.ToiletUserFetcher;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class ToiletprojectApplicationTests {
	private final static String TEST_USER_ID = "user-id-123";
	private final static long TOILET_ID = 1;
	private final static double LATITUDE = 57.706;
	private final static double LONGITUDE = 11.937;
	private final static double AVG_RATING_PLACEHOLDER = 0.0;
	private final static String NOTES = "Important comment";
	private final static int RATING = 3;
	private final static double AVG_RATING = 3.0;
	private final static String LAT_LONG_JSON = "{\"latitude\":57.706,\"longitude\":11.937}";
	private final static Toilet TOILET = new Toilet(1L, LONGITUDE, LATITUDE, AVG_RATING_PLACEHOLDER);
	private final static Toilet TOILET_1 = new Toilet(2L, 57.000, 11.900, AVG_RATING_PLACEHOLDER);
	private final static String RATING_JSON = "{\"rating\":3,\"notes\":\"clean\",\"toiletId\":1}";
	private static final String ROLE_APPUSER = "ROLE_APPUSER";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ToiletRepository toiletRepository;

	@Autowired
	private ToiletUserService toiletUserService;

	@Autowired
	private RatingService ratingService;

	@MockBean
	private ToiletUserFetcher toiletUserFetcher;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ReportService reportService;


	@Test
	void addToiletTest() throws Exception {
		mockMvc.perform(post("/api/v1/toilets/create")
						.with(user("usr").roles("SUPER_ADMIN"))
						.contentType("application/json")
						.content(LAT_LONG_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string("{\"longitude\":11.937,\"latitude\":57.706,\"avgRating\":0.0,\"id\":2}"));
	}

	@Test
	void getToiletByIdTest() throws Exception {
		toiletRepository.save(TOILET);
		mockMvc.perform(get("/api/v1/toilets/1")
						.with(user("usr"))
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().string(
						"{\"longitude\":11.937,\"latitude\":57.706,\"avgRating\":0.0,\"id\":1}"));
	}

	@Test
	void getAllToiletsTest() throws Exception {
		toiletRepository.save(TOILET);
		toiletRepository.save(TOILET_1);
		mockMvc.perform(get("/api/v1/toilets/getalltoilets")
				.with(user("usr").roles("SUPER_ADMIN"))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string
						("{\"toilets\":[{\"longitude\":11.937,\"latitude\":57.706,\"avgRating\":0.0,\"id\":3},{\"longitude\":57.0,\"latitude\":11.9,\"avgRating\":0.0,\"id\":4}]}"));
	}

	@Test
	void setRatingForToiletTest() throws Exception{
		/*mockMvc.perform(post("/api/v1/toilets/create")
						*//*.with(user("usr").roles("SUPER_ADMIN"))*//*
						.contentType("application/json")
						.content(LAT_LONG_JSON))
				.andExpect(status().isCreated());

		try(MockedStatic<ToiletUserFetcher> theMock =
					Mockito.mockStatic(ToiletUserFetcher.class)) {

			String toiletUserJSON = "{\"email\":\"john.doe@mail.com\"," +
					"\"name\":\"John Doe\",\"password\":\"secret\",\"username\":\"jd\"}";

			Role role = new Role(1L, "ROLE_APPUSER");
			Collection<Role> roles = new ArrayList<>();
			roles.add(role);
			ToiletUser toiletUser = new ToiletUser(1L, "John Doe", "jd",
					"secret", "john.doe@mail.com", false, roles);

			theMock.when(ToiletUserFetcher::fetchToiletUserByContext).thenReturn(toiletUser);
			theMock.when(() -> ToiletUserFetcher.fetchToiletUserByUsername("jd")).thenReturn(toiletUser);

			mockMvc.perform(put("/api/v1/toilets/rate")
							.with(SecurityMockMvcRequestPostProcessors.user("jd").roles("APPUSER"))
							.with(jwt())
							.contentType(MediaType.APPLICATION_JSON)
							.content(RATING_JSON))
					.andExpect(status().isOk());
		}*/
	}

	@Test
	void getAllRatingsForToiletTest() throws Exception{
		// TODO: 2022-01-25 try it!
	}

	@Test
	void reportToiletTest() throws Exception{
		// TODO: 2022-01-25 userdetails... 
	}

/*	@WithMockUser
	@Test
	void addSOAToiletTest() throws Exception, ToiletUserNotFoundException {
		*//*given(ToiletUserFetcher.fetchToiletUserByContext()).willReturn(new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*//*
		*//*Mockito.when(toiletUserFetcher.fetchToiletUserByContext()).thenReturn(new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*//*
		*//*Mockito.when(ToiletUserFetcher.fetchToiletUserByContext()).thenAnswer(I -> new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*//*
		*//*JSONObject jsonObject = new JSONObject("{\"longitude\": 11.937," +
				"\"latitude\": 57.706}");
		Role role = new Role(1, "ROLE_APPUSER");
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(1, "testuser", "testuser",
				"testuser", "testuser", false, roles);*//*

		*//*mockMvc.perform(post("/api/user/save")
						.with(user("testuser"))
						.with(csrf())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(toiletUser)))
				.andExpect(status().isCreated());*//*

		try(MockedStatic<ToiletUserFetcher> theMock =
					Mockito.mockStatic(ToiletUserFetcher.class)) {

			Toilet toilet = new Toilet(1L, LONGITUDE, LATITUDE,
					AVG_RATING_PLACEHOLDER);
			Role role = new Role(1L, "ROLE_APPUSER");
			Collection<Role> roles = new ArrayList<>();
			roles.add(role);
			ToiletUser toiletUser = new ToiletUser(1L, "name", "username",
					"password", "name@mail.com", false, roles);
			Rating rating = new Rating(1L, RATING, NOTES);
			rating.setId(1L);
			rating.setToiletUser(toiletUser);
			rating.setToilet(toilet);

			theMock.when(ToiletUserFetcher::fetchToiletUserByContext).thenReturn(toiletUser);
			theMock.when(() -> ToiletUserFetcher.fetchToiletUserByUsername("username")).thenReturn(toiletUser);
			//theMock.when(ToiletUserFetcher::fetchToiletUserByUsername).thenReturn(toiletUser);
			Assertions.assertNotNull(ToiletUserFetcher.fetchToiletUserByContext());
			Assertions.assertEquals(ToiletUserFetcher.fetchToiletUserByContext(), toiletUser);
			//Assertions.assertEquals(ToiletUserFetcher.fetchToiletUserByUsername("username"), toiletUser);

			mockMvc.perform(post("/api/v1/toilets/create")
							*//*.with(user("username"))
							.with(csrf())*//*
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(toilet)))
					.andExpect(status().isCreated());
			Optional<Toilet> toiletA = toiletRepository.findById(1L);
			Assertions.assertEquals(1, toiletA.get().getId());
			Assertions.assertEquals(LONGITUDE, toiletA.get().getLongitude());

			mockMvc.perform(get("/api/v1/toilets/1")
							*//*.with(user("username"))
							.with(csrf())*//*
							.contentType("application/json"))
					.andExpect(status().isOk())
					.andExpect(content().string(
							"{\"longitude\":11.937,\"latitude\":57.706,\"avgRating\":0.0,\"id\":1}"));
			Optional<Toilet> optionalToilet = toiletRepository.findById(TOILET_ID);
			Assertions.assertTrue(optionalToilet.isPresent());
			Assertions.assertEquals(TOILET_ID, optionalToilet.get().getId());
			Assertions.assertEquals(LATITUDE, optionalToilet.get().getLatitude());
			Assertions.assertEquals(LONGITUDE, optionalToilet.get().getLongitude());


			mockMvc.perform(get("/api/v1/toilets/getalltoilets")
							.contentType("application/json"))
					.andExpect(status().isOk());
			List<Toilet> toilets = toiletRepository.findAll();
			Assertions.assertEquals(1, toilets.size());
			Assertions.assertEquals(LONGITUDE, toilets.get(0).getLongitude());
			Assertions.assertEquals(LATITUDE, toilets.get(0).getLatitude());

			mockMvc.perform(put("/api/v1/toilets/rate")
							*//*.with(user("username"))
							.with(csrf())*//*
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(rating)))
					.andExpect(status().isCreated());
			*//*Assertions.assertEquals(optionalToilet.get().getAvgRating(), AVG_RATING);*//*

			mockMvc.perform(get("/api/v1/toilets/1/rating")
							*//*.with(user("username"))
							.with(csrf())*//*
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()); //den är ju tom...
			List<Rating> rating1 = ratingRepository.findAll();
			Assertions.assertTrue(rating1.isEmpty()); //tom tom tom
			*//*Assertions.assertEquals(3.0, ratingRepository.findAvgRating(1));*//*
		}
	}*/
}
