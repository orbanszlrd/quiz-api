package com.orbanszlrd.quizapi.modules.user;

import com.orbanszlrd.quizapi.modules.user.dto.InsertUserRequest;
import com.orbanszlrd.quizapi.modules.user.dto.UserResponse;
import com.orbanszlrd.quizapi.modules.user.dto.UpdateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ModelMapper modelMapper;

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
        final ResponseEntity<CollectionModel> response = testRestTemplate.withBasicAuth("admin", "admin").getForEntity(baseUrl, CollectionModel.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final CollectionModel<EntityModel<UserResponse>> collectionModel = response.getBody();
        assertEquals(getEntityCount(), collectionModel.getContent().size());
    }

    @Test
    void add_creates_a_new_user() {
        InsertUserRequest peter = new InsertUserRequest("peter.griffin", "peter.griffin@email.com", "peter.griffin", Role.USER);

        final int countBefore = getEntityCount();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<InsertUserRequest> httpEntity = new HttpEntity<>(peter, headers);

        final EntityModel entityModel = testRestTemplate.withBasicAuth("admin", "admin").postForObject(baseUrl, httpEntity, EntityModel.class);
        UserResponse userResponse = modelMapper.map(entityModel.getContent(), UserResponse.class);

        assertEquals(countBefore + 1, getEntityCount());
        assertEquals(peter.getUsername(), userResponse.getUsername());
        assertEquals(peter.getEmail(), userResponse.getEmail());
        assertEquals(peter.getRole(), userResponse.getRole());
    }

    @Test
    void update_modifies_the_user_correctly() {
        User glenn = new User("glenn.quagmire", "glenn.quagmire@email.com", passwordEncoder.encode("glenn.quagmire"), true, Role.USER);
        glenn.setId(4L);

        jdbcTemplate.update("insert into user (id, username, email, password, enabled, role) values (?, ?, ?, ?, ?, ?);", glenn.getId(), glenn.getUsername(), glenn.getEmail(), glenn.getPassword(), glenn.isEnabled(), glenn.getRole().ordinal());

        UpdateUserRequest updateUserRequest = new UpdateUserRequest("glenn.quagmire", "glenn.quagmire@email.com", "glenn.quagmire.stronger", false, Role.USER);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<UpdateUserRequest> httpEntity = new HttpEntity<>(updateUserRequest, headers);

        final EntityModel entityModel = testRestTemplate.withBasicAuth("admin", "admin").exchange(baseUrl + "/" + glenn.getId(), HttpMethod.PUT, httpEntity, EntityModel.class).getBody();
        UserResponse userResponse = modelMapper.map(entityModel.getContent(), UserResponse.class);

        assertEquals(updateUserRequest.getEnabled(), userResponse.isEnabled());
        assertEquals(updateUserRequest.getRole(), userResponse.getRole());
    }

    @Test
    void findById_returns_the_correct_user() {
        User brian = new User("brian.griffin", "brian.griffin@email.com", passwordEncoder.encode("brian.griffin"), true, Role.USER);
        brian.setId(4L);

        jdbcTemplate.update("insert into user (id, username, email, password, enabled, role) values (?, ?, ?, ?, ?, ?);", brian.getId(), brian.getUsername(), brian.getEmail(), brian.getPassword(), brian.isEnabled(), brian.getRole().ordinal());

        EntityModel entityModel = testRestTemplate.withBasicAuth("admin", "admin").getForObject(baseUrl + "/" + brian.getId(), EntityModel.class);
        UserResponse userResponse = modelMapper.map(entityModel.getContent(), UserResponse.class);

        assertEquals(brian.getId(), userResponse.getId());
        assertEquals(brian.getUsername(), userResponse.getUsername());
        assertEquals(brian.getEmail(), userResponse.getEmail());
        assertEquals(brian.getRole(), userResponse.getRole());
    }

    @Test
    void deleteById_removes_the_correct_user() {
        User cleveland = new User("cleveland.brown", "cleveland.brown@email.com", passwordEncoder.encode("cleveland.brown"), true, Role.USER);
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