package com.example.demo.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        entityManager.persist(user);
        entityManager.flush();

        List<UsersModel> found = userRepo.findByUsername("User");

        assertEquals(found.get(0).getUsername(), user.getUsername());
    }
}
