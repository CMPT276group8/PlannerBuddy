package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UsersService;

@Controller
public class UsersController {

    @Autowired

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") UsersModel usersModel, BindingResult bindingResult, Model model) {

        UsersModel registeredUser = usersService.registerUser(usersModel.getUsername(), usersModel.getPassword());

        if (registeredUser == null){

            model.addAttribute("error2", "Username duplicate detected");
            return "register_page";
        }
        else{
            return "redirect:/login";
        }
        //return registeredUser == null ? "register_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") UsersModel usersModel, BindingResult bindingResult, Model model) {
        UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
        if (authenticated != null) {
            if (authenticated.getRole().equalsIgnoreCase("admin")) {
                // Admin user authenticated, go to admin page
                List<UsersModel> users = usersRepository.findAll();
            // end of database call
                model.addAttribute("us", users);
                return "admin_page";}
            // Successful login, perform the desired action
             else{return "main_page";
            }
        } else {
            // Login failed, display an error message
            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }
    }

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/admin")
    public String getAllUsers(Model model){
       UsersModel adminAuthenticated = usersService.adminAuthenticate("admin");
        if (adminAuthenticated != null){
            // get all users from database
            List<UsersModel> users = usersRepository.findAll();
            // end of database call
            model.addAttribute("us", users);
        
            return "admin_page";
        }
        else{
            return "login_page";
        }
        
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteById(@PathVariable("id") int id) {
        //System.out.println(uid);
        usersRepository.deleteById(id); //delete user by id
        
        return "admin_page";
    }

    
}

