package com.example.demo.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;

@Service
public class UsersService {
    
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser( String username, String password){
        if (username == null || password ==null){
            return null;
        }else{
            if(usersRepository.findFirstByUsername(username).isPresent()){
                System.out.println("Duplicate username");
                return null;
            }
            UsersModel usersModel = new UsersModel();
            
            usersModel.setUsername(username);
            usersModel.setPassword(password);
            return usersRepository.save(usersModel);
        }
    }

    public UsersModel authenticate(String username, String password){
        return usersRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}

