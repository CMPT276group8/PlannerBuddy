package com.example.plannerbuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.plannerbuddy.model.UsersModel;
import com.example.plannerbuddy.service.UsersService;

@Controller
public class UsersController {

    @Autowired
    private final UsersService  usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }
    
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page"; //registerPage 
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page"; //loginPage 
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel){
        
        UsersModel registeredUser = usersService.registerUser(usersModel.getUsername(), usersModel.getPassword());
        return registeredUser == null ? "register_page" : "redirect:/login"; //penyusup
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel){
        
        UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
        if(authenticated != null){
            //model.addAttribute("userLogin", authenticated.getUsername()); //need a new Model model if implement this
            return "main_page";
        }else{
            return "login_page";
        }
    }
}
