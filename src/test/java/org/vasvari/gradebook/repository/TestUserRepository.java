package org.vasvari.gradebook.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vasvari.gradebook.model.User;
import org.vasvari.gradebook.model.UserRole;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestUserRepository {
    private static final String TEST_USER_NAME = "test_user";
    private static final String TEST_USER_PASSWORD = "1234";
    private static final UserRole TEST_USER_ROLE = UserRole.USER;
    private static final boolean TEST_USER_LOCKED = false;
    private static final boolean TEST_USER_ENABLED = true;

    private User testUser;

    @Autowired
    private UserRepository userRepo;

    @Before
    public void init() {
        testUser = new User(
                TEST_USER_NAME,
                TEST_USER_PASSWORD,
                TEST_USER_ROLE,
                TEST_USER_LOCKED,
                TEST_USER_ENABLED
        );
    }

    @Test
    public void TestSaveUser() {
        userRepo.save(testUser);

        Optional<User> foundUser = userRepo.findByUsername(TEST_USER_NAME);

        assertTrue(foundUser.isPresent());
        assertEquals(TEST_USER_NAME, foundUser.get().getUsername());
        assertEquals(TEST_USER_PASSWORD, foundUser.get().getPassword());
        assertEquals(TEST_USER_ROLE, foundUser.get().getUserRole());
        assertEquals(TEST_USER_LOCKED, foundUser.get().getLocked());
        assertEquals(TEST_USER_ENABLED, foundUser.get().getEnabled());
    }

    @Test
    public void TestDeleteUser() {
        userRepo.save(testUser);
        userRepo.delete(testUser);

        Optional<User> foundUser = userRepo.findByUsername(TEST_USER_NAME);

        assertFalse(foundUser.isPresent());
    }
}
