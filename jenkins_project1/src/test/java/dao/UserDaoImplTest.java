package dao;

import model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {

    UserDao ud = new UserDaoImpl();
    User employee = new User( 1, "testemployee", "password", "John", "Doe", "test@test.com", 1);

    @BeforeEach
    void setUp() {
        TestConfig.h2InitDao();
    }

    @AfterEach
    void tearDown() {
        TestConfig.h2DestroyDao();
    }

    @Test
    void getUserById() {
        assertEquals(employee, ud.getUserById(employee.getUserId()));
    }

    @Test
    void getUserByUserName() {
        assertEquals(employee, ud.getUserByUserName(employee.getUsername()));
    }

    @Test
    void createUser() {
        assertTrue(ud.createUser(employee));
    }

    @Test
    void deleteUser() {
        assertTrue(ud.deleteUser(employee.getUserId()));
    }

}
