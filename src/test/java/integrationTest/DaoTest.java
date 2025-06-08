package integrationTest;

import UserStorage.entity.User;
import UserStorage.entity.dao.UserDao;
import UserStorage.entity.dao.impl.UserDaoImpl;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DaoTest {


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.2")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> {

                cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432)));
            });

    private UserDao userDao;


    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    @AfterEach
    void tearDown() {
        userDao.findAll().forEach(userDao::delete);
    }

    @Test
    @DisplayName("Should save and find user by ID")
    void testSaveAndFindById() {
        User user = new User("Test User", "test@example.com", 30);
        userDao.save(user);

        User foundUser = userDao.findById(user.getId());

        assertAll(
                () -> assertNotNull(foundUser),
                () -> assertEquals(user.getName(), foundUser.getName()),
                () -> assertEquals(user.getEmail(), foundUser.getEmail()),
                () -> assertEquals(user.getAge(), foundUser.getAge())
        );
    }

    @Test
    @DisplayName("Should return all users")
    void testFindAll() {
        User user1 = new User("User 1", "user1@test.com", 25);
        User user2 = new User("User 2", "user2@test.com", 30);

        userDao.save(user1);
        userDao.save(user2);

        List<User> users = userDao.findAll();

        assertThat(users)
                .hasSize(2)
                .extracting(User::getEmail)
                .containsExactlyInAnyOrder("user1@test.com", "user2@test.com");
    }

    @Test
    @DisplayName("Should update user")
    void testUpdate() {
        User user = new User("Original", "original@test.com", 20);
        userDao.save(user);

        user.setName("Updated");
        user.setEmail("updated@test.com");
        user.setAge(30);
        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());

        assertAll(
                () -> assertEquals("Updated", updatedUser.getName()),
                () -> assertEquals("updated@test.com", updatedUser.getEmail()),
                () -> assertEquals(30, updatedUser.getAge())
        );
    }

    @Test
    @DisplayName("Should delete user")
    void testDelete() {
        User user = new User("To Delete", "delete@test.com", 40);
        userDao.save(user);

        userDao.delete(user);

        User deletedUser = userDao.findById(user.getId());
        assertNull(deletedUser);
    }

    @Test
    @DisplayName("Should return null when user not found")
    void testFindNonExistentUser() {
        User user = userDao.findById(999L);
        assertNull(user);
    }

    @Test
    @DisplayName("Should handle empty database")
    void testFindAllWhenEmpty() {
        List<User> users = userDao.findAll();
        assertThat(users).isEmpty();
    }
}