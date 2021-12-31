package com.orbanszlrd.quizapi.user;

import com.orbanszlrd.quizapi.user.dto.AddUser;
import com.orbanszlrd.quizapi.user.dto.GetUser;
import com.orbanszlrd.quizapi.user.dto.UpdateUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/users";
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from user where id>3");
    }
    @Test
    void findAll_returns_every_user_for_admin() {
        final ResponseEntity<GetUser[]> response = testRestTemplate.withBasicAuth("admin", "admin").getForEntity(baseUrl, GetUser[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final GetUser[] users = response.getBody();
        assertEquals(getEntityCount(), users.length);
    }

    @Test
    void add_creates_a_new_user() {
        AddUser peter = new AddUser();
        peter.setUsername("peter.griffin");
        peter.setEmail("peter.griffin@email.com");
        peter.setPassword("password");
        peter.setRole(Role.USER);

        final int countBefore = getEntityCount();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<AddUser> httpEntity = new HttpEntity<>(peter, headers);
        final GetUser getUser = testRestTemplate.withBasicAuth("admin", "admin").postForObject(baseUrl, httpEntity, GetUser.class);

        assertEquals(countBefore + 1, getEntityCount());
        assertEquals(peter.getUsername(), getUser.getUsername());
        assertEquals(peter.getEmail(), getUser.getEmail());
        assertEquals(peter.getRole(), getUser.getRole());
    }

    @Test
    void update_modifies_the_user_correctly() {
        User glenn = new User("glenn.quagmire", "glenn.quagmire@email.com", passwordEncoder.encode("quagmire"), true, Role.USER);
        glenn.setId(4L);

        jdbcTemplate.update("insert into user (id, username, email, password, enabled, role) values (?, ?, ?, ?, ?, ?);", glenn.getId(), glenn.getUsername(), glenn.getEmail(), glenn.getPassword(), glenn.isEnabled(), glenn.getRole().ordinal());

        UpdateUser updateUser = new UpdateUser("glenn.quagmire", "glenn.quagmire@email.com", "glenn", false, Role.USER);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<UpdateUser> httpEntity = new HttpEntity<>(updateUser, headers);

        GetUser getUser = testRestTemplate.withBasicAuth("admin", "admin").exchange(baseUrl + "/" + glenn.getId(), HttpMethod.PUT, httpEntity, GetUser.class).getBody();

        assertEquals(updateUser.getEnabled(), getUser.isEnabled());
        assertEquals(updateUser.getRole(), getUser.getRole());
    }

    @Test
    void findById_returns_the_correct_user() {
        User brian = new User("brian.griffin", "brian.griffin@email.com", passwordEncoder.encode("brian"), true, Role.USER);
        brian.setId(4L);

        jdbcTemplate.update("insert into user (id, username, email, password, enabled, role) values (?, ?, ?, ?, ?, ?);", brian.getId(), brian.getUsername(), brian.getEmail(), brian.getPassword(), brian.isEnabled(), brian.getRole().ordinal());
        GetUser getUser = testRestTemplate.withBasicAuth("admin", "admin").getForObject(baseUrl + "/" + brian.getId(), GetUser.class);

        assertEquals(brian.getId(), getUser.getId());
        assertEquals(brian.getUsername(), getUser.getUsername());
        assertEquals(brian.getEmail(), getUser.getEmail());
        assertEquals(brian.getRole(), getUser.getRole());
    }

    @Test
    void deleteById_removes_the_correct_user() {
        User cleveland = new User("cleveland.brown", "cleveland.brown@email.com", passwordEncoder.encode("cleveland"), true, Role.USER);
        cleveland.setId(4L);

        jdbcTemplate.update("insert into user (id, username, email, password, enabled, role) values (?, ?, ?, ?, ?, ?);", cleveland.getId(), cleveland.getUsername(), cleveland.getEmail(), cleveland.getPassword(), cleveland.isEnabled(), cleveland.getRole().ordinal());

        final int countBefore = getEntityCount();

        testRestTemplate.withBasicAuth("admin", "admin").delete(baseUrl + "/" + cleveland.getId());

        assertEquals(countBefore - 1, getEntityCount());
    }

    private Integer getEntityCount() {
        return jdbcTemplate.queryForObject("select count(1) from user", Integer.class);
    }
}