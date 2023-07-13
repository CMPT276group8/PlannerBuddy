package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.validation.BindingResult;
=======
<<<<<<< HEAD
import org.springframework.validation.BindingResult;
=======
>>>>>>> d2b8ecd525f0925e5a07fb3fd664524d71fd6326
>>>>>>> a2cec180681636b6aabb7785eb31fa7fad9f7e40
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.UsersModel;
import com.example.demo.service.UsersService;

@Controller
public class UsersController {

    @Autowired
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a2cec180681636b6aabb7785eb31fa7fad9f7e40
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
    public String register(@ModelAttribute UsersModel usersModel) {

        UsersModel registeredUser = usersService.registerUser(usersModel.getUsername(), usersModel.getPassword());
        return registeredUser == null ? "register_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") UsersModel usersModel, BindingResult bindingResult, Model model) {
        UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
        if (authenticated != null) {
            // Successful login, perform the desired action
            return "main_page";
        } else {
            // Login failed, display an error message
            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }
    }
}
<<<<<<< HEAD
=======
=======
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
>>>>>>> d2b8ecd525f0925e5a07fb3fd664524d71fd6326
>>>>>>> a2cec180681636b6aabb7785eb31fa7fad9f7e40
