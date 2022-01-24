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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

	/*@Autowired
	private ToiletUserService toiletUserService;

	@Autowired
	private RatingService ratingService;*/

	@MockBean
	private ToiletUserFetcher toiletUserFetcher;

	@Autowired
	private RatingRepository ratingRepository;

	/*@Retention(RetentionPolicy.RUNTIME)
	@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
	public @interface WithMockCustomUser {
		Role role1 = new Role(1, "ROLE_APPUSER");

		long id() default 1;
		String username() default "username";
		String name() default "name";
		String password() default "password";
		String email() default "name@mail.com";
		boolean isBlocked() default false;

	}
	static class WithMockCustomUserSecurityContextFactory
			implements WithSecurityContextFactory<WithMockCustomUser> {
		@Override
		public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();

			CustomUserDetails principal =
					new CustomUserDetails(customUser.id(), customUser.name(),
							customUser.username(), customUser.password(),
							customUser.email(), customUser.isBlocked());
			Authentication auth =
					new UsernamePasswordAuthenticationToken(principal,
							"password", principal.loadUserByUsername(
									"username").getAuthorities());
			context.setAuthentication(auth);
			return context;
		}
		class CustomUserDetails implements UserDetailsService {

			public CustomUserDetails(long id, String name, String username,
									 String password, String email,
									 Boolean isBlocked, Collection<Role> roles) {
			}

			public CustomUserDetails(long id, String name, String username, String password, String email, boolean blocked) {
			}

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Role role1 = new Role(1, "ROLE_APPUSER");
				Collection<Role> roles = new ArrayList<>();
				roles.add(role1);
				ToiletUser toiletUser = new ToiletUser(1, "name", "username",
						"password", "name@mail.com", false, roles);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				toiletUser.getRoles().forEach(role -> {
					authorities.add(new SimpleGrantedAuthority(role.getName()));
				});


				return new org.springframework.security.core.userdetails.User(
						toiletUser.getUsername(),
						toiletUser.getPassword(),
						authorities
				);
			}
		}
	}
	*/

	@WithMockUser
	@Test/*(expected = ToiletUserNotFoundException.class)*/
	void addToiletTest() throws Exception, ToiletUserNotFoundException {
		/*given(ToiletUserFetcher.fetchToiletUserByContext()).willReturn(new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*/
		/*Mockito.when(toiletUserFetcher.fetchToiletUserByContext()).thenReturn(new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*/
		/*Mockito.when(ToiletUserFetcher.fetchToiletUserByContext()).thenAnswer(I -> new ToiletUser(
				1, "name", "username", "password",
				"name@mail.com", false, roles));*/
		/*JSONObject jsonObject = new JSONObject("{\"longitude\": 11.937," +
				"\"latitude\": 57.706}");
		Role role = new Role(1, "ROLE_APPUSER");
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		ToiletUser toiletUser = new ToiletUser(1, "testuser", "testuser",
				"testuser", "testuser", false, roles);*/

		/*mockMvc.perform(post("/api/user/save")
						.with(user("testuser"))
						.with(csrf())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(toiletUser)))
				.andExpect(status().isCreated());*/



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
							/*.with(user("username"))
							.with(csrf())*/
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(toilet)))
					.andExpect(status().isCreated());
			Optional<Toilet> toiletA = toiletRepository.findById(1L);
			Assertions.assertEquals(1, toiletA.get().getId());
			Assertions.assertEquals(LONGITUDE, toiletA.get().getLongitude());

			mockMvc.perform(get("/api/v1/toilets/1")
							/*.with(user("username"))
							.with(csrf())*/
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
							/*.with(user("username"))
							.with(csrf())*/
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(rating)))
					/*.andExpect(status().isCreated())*/;
			/*Assertions.assertEquals(optionalToilet.get().getAvgRating(), AVG_RATING);*/

			mockMvc.perform(get("/api/v1/toilets/1/rating")
							/*.with(user("username"))
							.with(csrf())*/
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest()); //den är ju tom...
			List<Rating> rating1 = ratingRepository.findAll();
			Assertions.assertTrue(rating1.isEmpty()); //tom tom tom
			/*Assertions.assertEquals(3.0, ratingRepository.findAvgRating(1));*/
		}
	}
}
