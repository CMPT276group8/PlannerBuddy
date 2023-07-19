package com.example.demo.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;


public class UsersRepositoryTest {
    @Autowired
    private UsersRepository userRepo;

	@Test
	public void UsersRepository_findAllTest() {
		UsersModel user1 = new UsersModel();
        user1.setUsername("User1");
		user1.setPassword("123");
        
        UsersModel user2 = new UsersModel();
        user1.setUsername("User2");
		user1.setPassword("1235");
		
        userRepo.save(user1);
        userRepo.save(user2);
		
        List<UsersModel> usersList = userRepo.findAll();

        assertNotEquals(usersList, null);
        assertEquals(usersList.size(), 2);
	}
    
    @Test
    void testFindByPassword() {

    }

    @Test
    void testFindByRole() {

    }

    @Test
    void testFindByRoles() {

    }

    @Test
    void testFindByUsername() {

    }

    @Test
    void testFindByUsernameAndPassword() {

    }

    @Test
    void testFindFirstByUsername() {

    }
}
