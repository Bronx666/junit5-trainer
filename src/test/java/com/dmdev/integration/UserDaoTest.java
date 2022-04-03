package com.dmdev.integration;

import com.dmdev.dao.UserDao;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest extends IntegrationTestBase {


    @Nested
    @DisplayName("Tests for the method UserDao")
    class UserDaoGroupTest {
        private UserDao userDao;
        User user = new User(
                6,
                "testname",
                LocalDate.now(),
                "email",
                "1111",
                Role.USER,
                Gender.MALE);

        @Test
        @DisplayName("Check how this method DELETE users")
        void shouldDeleteExistedUser() {
            assertAll((
                            () -> assertFalse(UserDao.getInstance().delete(9))),
                    () -> assertTrue(UserDao.getInstance().delete(2)));
        }

        @Test
        @DisplayName("RETURN USER BY ID")
        void shouldReturnUserIfFoundById() {
            var foundedUser = UserDao.getInstance().findById(2);
            assertTrue(foundedUser.isPresent());
        }

        @Test
        @DisplayName("FIND ALL")
        void shouldReturnListAllUsers() {
            var usersList = UserDao.getInstance().findAll();
            assertEquals(5, usersList.size());
        }

        @Test
        @DisplayName("SAVE")
        void saveNewUserToDB() {
            UserDao.getInstance().save(this.user);
            var userFromDB = UserDao.getInstance().findById(this.user.getId());
            assertEquals(this.user, userFromDB.get());
        }

        @Test
        @DisplayName("Find user by Email and password")
        void shouldReturnUserByEmailAndPassword() {

            User checkedUser = new User(
                    1,
                    "Ivan",
                    LocalDate.of(1990, 01, 10),
                    "ivan@gmail.com",
                    "111",
                    Role.ADMIN,
                    Gender.MALE);

            assertAll(
                    () -> assertTrue(UserDao
                            .getInstance()
                            .findByEmailAndPassword("ivan@gmail.com", "111")
                            .get().equals(checkedUser)),
                    () -> assertFalse(UserDao
                            .getInstance()
                            .findByEmailAndPassword("petr@gmail.com", "123")
                            .get().equals(checkedUser)));
        }

    }
}
