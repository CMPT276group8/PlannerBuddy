package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Todo;
import com.example.demo.model.UsersModel;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByActivity(String activity);
    List<Todo> findByActivity2(String activity2);
    List<Todo> findByActivity3(String activity3);
    List<Todo> findByUsers(UsersModel usersModel);
    
}
