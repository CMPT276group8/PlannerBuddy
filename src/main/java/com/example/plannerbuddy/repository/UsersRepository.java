package com.example.plannerbuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.plannerbuddy.model.UsersModel;

public interface UsersRepository extends JpaRepository<UsersModel, Integer>{

    Optional<UsersModel> findByUsernameAndPassword(String username, String password);

    Optional<UsersModel> findFirstByUsername(String username);
    
}
