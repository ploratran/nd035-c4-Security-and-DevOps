package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.persistence.User;
import org.apache.coyote.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        // inject Autowired fields UserRepository, CartRepository, BCryptPasswordEncoder into UserController:
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);
    }

    // helper class to create new user:
    private User newUser() {
        User user = new User(1L, "Plora", "qwertyuiop");
        return user;
    }

    @Test
    public void testCreateUser() throws Exception {

        // mock BCrypt to encode password with hashed password:
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest mockUser = new CreateUserRequest();

        mockUser.setUsername("testUser");
        mockUser.setPassword("testPassword");
        mockUser.setConfirmPassword("testPassword");

        ResponseEntity<User> actualUser = userController.createUser(mockUser);

        // test response is not null:
        assertNotNull(mockUser);
        // test for successful request:
        assertEquals(HttpStatus.OK, actualUser.getStatusCode());

        // test response body
        User user = actualUser.getBody();
        assertNotNull(user);
        // test if there exists a user:
        assertEquals(0, user.getId());
        // test username and password:
        assertEquals("testUser", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());
    }

    @Test
    public void testFindUserById() {
        // create new mock user:
        User mockUser = new User(1L, "Plora", "qwertyuiop");

        // mock userRepository to find by id:
        when(userRepository.findById(1L)).thenReturn(Optional.of(newUser()));

        // create ResponseEntity<User> for actual user:
        ResponseEntity<User> response = userController.findById(mockUser.getId());
        User actualUser = response.getBody();

        assertNotNull(actualUser);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // test if mockUser properties with actualUser properties:
        assertEquals(mockUser.getId(), actualUser.getId());
        assertEquals(mockUser.getUsername(), actualUser.getUsername());
        assertEquals(mockUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testFindByUsername() {
        // create mock user:
        User mockUser = new User(1L, "Plora", "qwertyuiop");

        // mock userRepository to find by username:
        when(userRepository.findByUsername("Plora")).thenReturn(newUser());

        // create ResponseEntity<User> for actual user:
        ResponseEntity<User> response = userController.findByUserName(mockUser.getUsername());
        User actualUser = response.getBody();

        assertNotNull(response);
        assertNotNull(actualUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(mockUser.getId(), actualUser.getId());
        assertEquals(mockUser.getUsername(), actualUser.getUsername());
        assertEquals(mockUser.getPassword(), actualUser.getPassword());
    }
}
