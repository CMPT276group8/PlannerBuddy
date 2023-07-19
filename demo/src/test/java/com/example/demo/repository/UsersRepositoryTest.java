package com.example.demo.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;

@DataJpaTest
public class UsersRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository userRepo;

    @Test
    void testFindByUsername() {
        UsersModel user = new UsersModel();
        user.setUsername("User");
        user.setPassword("123");
        user.setRole("regular");
        entityManager.persist(user);
        entityManager.flush();

        List<UsersModel> found = userRepo.findByUsername("User");

        assertEquals(found.get(0).getUsername(), user.getUsername());
    }

    @Test
    void testFindAll() {
        UsersModel user1 = new UsersModel();
        user1.setUsername("User1");
        user1.setPassword("123");
        user1.setRole("regular");
        entityManager.persist(user1);
        entityManager.flush();

        UsersModel user2 = new UsersModel();
        user2.setUsername("Admin1");
        user2.setPassword("1234");
        user2.setRole("admin");
        entityManager.persist(user2);
        entityManager.flush();

        userRepo.save(user1);
        userRepo.save(user2);

        List<UsersModel> userList = userRepo.findAll();

        assertNotEquals(userList, null);
        assertEquals(userList.size(), 2);
    }
    @Test
    void testSave() {
        UsersModel user = new UsersModel();
        user.setUsername("User");
        user.setPassword("123");
        user.setRole("regular");
        entityManager.persist(user);
        entityManager.flush();

        UsersModel savedUser = userRepo.save(user);

        assertNotEquals(savedUser, null);
        assertEquals(savedUser.getId(), 1);
    }
    @Test
    void testFindByRole() {
        UsersModel user = new UsersModel();
        user.setUsername("User");
        user.setPassword("123");
        user.setRole("regular");
        entityManager.persist(user);
        entityManager.flush();

        List<UsersModel> found = userRepo.findByRole("regular");

        assertEquals(found.get(0).getRole(), user.getRole());
    }
}
