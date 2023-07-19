package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.UsersModel;

public interface UsersRepository extends JpaRepository<UsersModel, Integer>{
    
    Optional<UsersModel> findByUsernameAndPassword(String username, String password);
    List<UsersModel>findByRole(String role);

    Optional<UsersModel> findFirstByUsername(String username);

    List<UsersModel> findByUsername(String username);
    List<UsersModel> findByPassword(String password);
    
    @Query("SELECT u FROM UsersModel u WHERE u.role = :role")
    List<UsersModel> findByRoles(String role);
}
