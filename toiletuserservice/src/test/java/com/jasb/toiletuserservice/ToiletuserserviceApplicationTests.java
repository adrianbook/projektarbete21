package com.jasb.toiletuserservice;

import com.jasb.toiletuserservice.service.ToiletUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A test class for integrations tests by mocking calls to REST-endpoints.
 * Uses an in-memory embedded H2 database.
 *
 * Written by JASB
 */

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class ToiletuserserviceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ToiletUserServiceImpl userService;

	private final static String TOILET_USER_1 = "{\"email\":\"john.doe@mail.com\",\"name\":\"John Doe\",\"password\":\"secret\",\"username\":\"jd\"}";
	private final static String TOILET_USER_2 = "{\"email\":\"joanna.doe@mail.com\",\"name\":\"Joanna Doe\",\"password\":\"secret\",\"username\":\"jo\"}";
	private final static String TOILET_USER_3 = "{\"email\":\"johnny.doe@mail.com\",\"name\":\"Johnny Doe\",\"password\":\"secret\",\"username\":\"jy\"}";
	private final static String ROLE = "{\"name\":\"ROLE_APPUSER\"}";
	private final static String ROLE_TO_USER_FORM = "{\"username\":\"jd\",\"rolename\":\"ROLE_APPUSER\"}";
	private final static String USERNAME = "{\"username\":\"jd\"}";

	@Test
	void saveRoleTest() throws Exception {
		mockMvc.perform(post("/api/role/save")
						.with(user("superduperadmin").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(ROLE))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ROLE_APPUSER"));
	}

	@Test
	void saveToiletUserTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));

		Assertions.assertNotNull(userService.getToiletUser("jd"));
		Assertions.assertEquals("john.doe@mail.com",
				userService.getToiletUser("jd").getEmail());
		Assertions.assertEquals("jd",
				userService.getToiletUser("jd").getUsername());
		Assertions.assertEquals("john doe",
				userService.getToiletUser("jd").getName().toLowerCase(Locale.ROOT));
		Assertions.assertEquals(1,
				userService.getToiletUser("jd").getRoles().size());
	}

	@Test
	void getToiletUserTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));

		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_2))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/user/jd")
						.with(user("user").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(
						"john.doe@mail.com"));

		mockMvc.perform(get("/api/user/jo")
						.with(user("user").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(
						"joanna.doe@mail.com"));
	}

	@Test
	void getToiletAllUsersTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));

		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_2))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_3))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/users")
						.with(user("user").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("jd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("jo"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].username").value("jy"));
	}

	@Test
	void addRoleToUserTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));

		mockMvc.perform(post("/api/role/addtouser")
						.with(user("superduperadmin").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(ROLE_TO_USER_FORM))
				.andExpect(status().isOk());
	}

	@Test
	void blockUserTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));
		mockMvc.perform(put("/api/user/block")
				.with(user("superduperadmin").roles("SUPER_ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(USERNAME))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("jd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.blocked").value(true));
	}
}
