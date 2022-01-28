package com.jasb.toiletuserservice;

import com.jasb.entities.Role;
import com.jasb.toiletuserservice.repo.RoleRepo;
import com.jasb.toiletuserservice.service.ToiletUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
	private static final String TOILET_USER_1 = "{\"email\":\"john.doe@mail.com\",\"name\":\"John Doe\",\"password\":\"secret\",\"username\":\"jd\"}";
	private static final String TOILET_USER_2 = "{\"email\":\"joanna.doe@mail.com\",\"name\":\"Joanna Doe\",\"password\":\"secret\",\"username\":\"jo\"}";
	private static final String TOILET_USER_3 = "{\"email\":\"johnny.doe@mail.com\",\"name\":\"Johnny Doe\",\"password\":\"secret\",\"username\":\"jy\"}";
	private static final String ROLE_APPUSER = "{\"name\":\"ROLE_APPUSER\"}";
	private static final String ROLE_ADMIN = "{\"name\":\"ROLE_ADMIN\"}";
	private static final String ROLE_SUPER_ADMIN = "{\"name\":\"ROLE_SUPER_ADMIN\"}";
	private static final String ROLE_TO_USER_FORM = "{\"username\":\"jd\",\"rolename\":\"ROLE_ADMIN\"}";
	private static final String USERNAME = "{\"username\":\"jd\"}";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ToiletUserServiceImpl userService;
	@Autowired
	private RoleRepo roleRepo;

	@BeforeEach
	public void intitEach() throws Exception {
		mockMvc.perform(post("/api/role/save")
				.with(user("usr").roles("SUPER_ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(ROLE_APPUSER));
		mockMvc.perform(post("/api/role/save")
				.with(user("usr").roles("SUPER_ADMIN"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(ROLE_ADMIN));
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_1));
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_2));
	}

	@Test
	void saveRoleTest() throws Exception {
		mockMvc.perform(post("/api/role/save")
						.with(user("superduperadmin").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(ROLE_SUPER_ADMIN))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ROLE_SUPER_ADMIN"));
		Assertions.assertEquals("ROLE_APPUSER", roleRepo.findAll().get(0).getName());
		Assertions.assertEquals("ROLE_ADMIN", roleRepo.findAll().get(1).getName());
		Assertions.assertEquals("ROLE_SUPER_ADMIN", roleRepo.findAll().get(2).getName());
	}

	@Test
	void saveToiletUserTest() throws Exception {
		mockMvc.perform(post("/api/user/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TOILET_USER_3))
				.andExpect(status().isCreated())
				.andExpect(content().string("User created"));
		Assertions.assertEquals("johnny.doe@mail.com", userService.getToiletUser("jy").getEmail());
		Assertions.assertEquals("johnny doe", userService.getToiletUser("jy").getName().toLowerCase(Locale.ROOT));
	}

	@Test
	void getToiletUserTest() throws Exception {
		mockMvc.perform(get("/api/user/jo")
						.with(user("user").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("joanna.doe@mail.com"));
	}

	@Test
	void getToiletAllUsersTest() throws Exception {
		mockMvc.perform(get("/api/users")
						.with(user("user").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("jd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("jo"));
	}

	@Test
	void addRoleToUserTest() throws Exception {
		mockMvc.perform(post("/api/role/addtouser")
						.with(user("superduperadmin").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(ROLE_TO_USER_FORM))
				.andExpect(status().isOk());
		Assertions.assertEquals(2, userService.getToiletUser("jd").getRoles().size());
	}

	@Test
	void blockUserTest() throws Exception {
		mockMvc.perform(put("/api/user/block")
						.with(user("superduperadmin").roles("SUPER_ADMIN"))
						.contentType(MediaType.APPLICATION_JSON)
						.content(USERNAME))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("jd"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.blocked").value(true));
		Assertions.assertTrue(userService.getToiletUser("jd").isBlocked());
	}
}
