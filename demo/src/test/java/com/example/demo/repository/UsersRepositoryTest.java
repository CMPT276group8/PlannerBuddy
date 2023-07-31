package com.example.demo.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.checkerframework.checker.units.qual.t;
import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.UsersModel;
import com.example.demo.model.Todo;

import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.TodoRepository;


@DataJpaTest
public class UsersRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private TodoRepository todoRepository;

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

    @Test
    void testFindByMap(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Erik");
        todo.setPlace1("New York");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("New York", checkTodo.getPlace1());
    }

    @Test
    void testFindByMap2(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Kevin");
        todo.setPlace2("Las Vegas");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("Las Vegas", checkTodo.getPlace2());
    }

    @Test
    void testFindByMap3(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Marco");
        todo.setPlace3("Seattle");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("Seattle", checkTodo.getPlace3());
    }

    @Test
    void testFindByCalendar(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Marco");
        todo.setDate("7/27/2023");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("7/27/2023", checkTodo.getDate());
    }

    @Test
    void testFindByCalendar2(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Marco");
        todo.setDate2("7/28/2023");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("7/28/2023", checkTodo.getDate2());
    }

     @Test
    void testFindByCalendar3(){
        Todo todo = new Todo();
        todo.setActivity("Eat with Marco");
        todo.setDate3("4/14/2023");
        entityManager.persist(todo);
        entityManager.flush();

        Todo checkTodo = todoRepository.save(todo);

        //UsersModel savedUser = userRepo.save(user);

        assertNotEquals(checkTodo, null);
        assertEquals("4/14/2023", checkTodo.getDate3());
    }
    

}
