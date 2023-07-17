package com.example.demo.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
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
                //username duplicate
                return null;
            }
            UsersModel usersModel = new UsersModel();
            
            usersModel.setUsername(username);
            usersModel.setPassword(password);
            if(username.equals("admin")){
                usersModel.setRole("admin");
            }
            else{
                usersModel.setRole("regulars");
            }
            return usersRepository.save(usersModel);
        }
    }

    public UsersModel authenticate(String username, String password){
        return usersRepository.findByUsernameAndPassword(username, password).orElse(null);
    }


    public UsersModel adminAuthenticate(String role){
    List<UsersModel> users = usersRepository.findByRole(role);
    for (UsersModel user : users) {
        if (user.getRole().equalsIgnoreCase("admin")) {
            return user;
        }
    }
    return null;
    }

    
}

