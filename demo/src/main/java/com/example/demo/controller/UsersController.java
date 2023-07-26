package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;

@Controller
public class UsersController {

    @Autowired

    private final UsersService usersService;
    private final UsersRepository usersRepository;


    @Autowired
    public UsersController(UsersService usersService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
    }
    @Autowired
    private TodoRepository todoRepository;

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
    public String login(
    @ModelAttribute("loginRequest") UsersModel usersModel,
    BindingResult bindingResult,
    Model model
) {
    UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
    if (authenticated != null) {
        if (authenticated.getRole().equalsIgnoreCase("admin")) {
            // Admin user authenticated, go to admin page
            List<UsersModel> users = usersRepository.findAll();
            model.addAttribute("us", users);
            return "admin_page";
        } else {
            // Successful login, perform the desired action
            model.addAttribute("username", authenticated.getUsername());
            return "redirect:/main/" + authenticated.getId();
        }
    } else {
        // Login failed, display an error message
        model.addAttribute("error", "Invalid username or password");
        return "login_page";
    }
}
    

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

    @GetMapping("/calendar/{id}")
    public String showCalendarById(Model model, HttpServletResponse response, @PathVariable Integer id) {
        Optional<UsersModel> userOptional = usersRepository.findById(id);
        if (userOptional.isPresent()){
            UsersModel user = userOptional.get();
            List<Todo> todos = todoRepository.findByUsers(user);
            List<Todo> todos1 = new ArrayList<Todo>();
            List<Todo> todos2 = new ArrayList<Todo>();
            List<Todo> todos3 = new ArrayList<Todo>();
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getActivity() != null) {
                    todos1.add(todos.get(i));
                }
            }
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getActivity2() != null) {
                    todos2.add(todos.get(i));
                }
            }
            for (int i = 0; i < todos.size(); i++) {
                if (todos.get(i).getActivity3() != null) {
                    todos3.add(todos.get(i));
                }
            }
            model.addAttribute("us", user);
            model.addAttribute("todos1", todos1);
            model.addAttribute("todos2", todos2);
            model.addAttribute("todos3", todos3);
            return "calendar_page";
        } else {
            return "redirect:/error";
        }
    }
    
    @GetMapping("/chatbot/{id}")
    public String showChatbot(Model model, HttpServletResponse response, @PathVariable Integer id) {
        Optional<UsersModel> userOptional = usersRepository.findById(id);
        if (userOptional.isPresent()){
            UsersModel user = userOptional.get();
            model.addAttribute("us", user);
            return "chatbot_page";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/main/{id}")
    public String showById(Model model, HttpServletResponse response, @PathVariable Integer id) {
    Optional<UsersModel> userOptional = usersRepository.findById(id);
    if (userOptional.isPresent()) {
        UsersModel user = userOptional.get();
        List<Todo> todos = todoRepository.findByUsers(user);
        List<Todo> todos1 = new ArrayList<Todo>();
        List<Todo> todos2 = new ArrayList<Todo>();
        List<Todo> todos3 = new ArrayList<Todo>();
        //todos1
        for (int i = 0; i < todos.size(); i++) {
            // System.out.println(todos.get(i));
            // System.out.println(todos.get(i).getActivity());
            if (todos.get(i).getActivity() != null) {
                todos1.add(todos.get(i));
            }
        }

        //todos2
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getActivity2() != null) {
                todos2.add(todos.get(i));
            }
        }

        //todos3
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getActivity3() != null) {
                todos3.add(todos.get(i));
            }
        }

        //sort todos1
        for (int i = 0; i < todos1.size(); i++) {
            for (int j = i + 1; j < todos1.size(); j++) {
                Todo temp = new Todo();
                if (todos1.get(j).getUid() < todos1.get(i).getUid()) {
                    // Swapping
                    temp.setUid(todos1.get(i).getUid());
                    temp.setActivity(todos1.get(i).getActivity());
                    temp.setCompleted(todos1.get(i).getCompleted());

                    //arr[i] = arr[j];
                    todos1.get(i).setUid(todos1.get(j).getUid());
                    todos1.get(i).setActivity(todos1.get(j).getActivity());
                    todos1.get(i).setCompleted(todos1.get(j).getCompleted());

                    //arr[j] = temp;
                    todos1.get(j).setUid(temp.getUid());
                    todos1.get(j).setActivity(temp.getActivity());
                    todos1.get(j).setCompleted(temp.getCompleted());
                }
            }
        }

        //sort todos2
        for (int i = 0; i < todos2.size(); i++) {
            for (int j = i + 1; j < todos2.size(); j++) {
                Todo temp2 = new Todo();
                if (todos2.get(j).getUid() < todos2.get(i).getUid()) {
                    // Swapping
                    temp2.setUid(todos2.get(i).getUid());
                    temp2.setActivity(todos2.get(i).getActivity());
                    temp2.setCompleted(todos2.get(i).getCompleted());

                    //arr[i] = arr[j];
                    todos2.get(i).setUid(todos2.get(j).getUid());
                    todos2.get(i).setActivity(todos2.get(j).getActivity());
                    todos2.get(i).setCompleted(todos2.get(j).getCompleted());

                    //arr[j] = temp;
                    todos2.get(j).setUid(temp2.getUid());
                    todos2.get(j).setActivity(temp2.getActivity());
                    todos2.get(j).setCompleted(temp2.getCompleted());
                }
            }
        }

        //sort todos3
        for (int i = 0; i < todos3.size(); i++) {
            for (int j = i + 1; j < todos3.size(); j++) {
                Todo temp = new Todo();
                if (todos3.get(j).getUid() < todos3.get(i).getUid()) {
                    // Swapping
                    temp.setUid(todos3.get(i).getUid());
                    temp.setActivity(todos3.get(i).getActivity());
                    temp.setCompleted(todos3.get(i).getCompleted());

                    //arr[i] = arr[j];
                    todos3.get(i).setUid(todos3.get(j).getUid());
                    todos3.get(i).setActivity(todos3.get(j).getActivity());
                    todos3.get(i).setCompleted(todos3.get(j).getCompleted());

                    //arr[j] = temp;
                    todos3.get(j).setUid(temp.getUid());
                    todos3.get(j).setActivity(temp.getActivity());
                    todos3.get(j).setCompleted(temp.getCompleted());
                }
            }
        }

        model.addAttribute("us", user);
        model.addAttribute("todos", todos);
        model.addAttribute("todos1", todos1);
        model.addAttribute("todos2", todos2);
        model.addAttribute("todos3", todos3);
        return "main_page";
    } 
    else {
        return "redirect:/error";
    }
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
    UsersModel authenticatedUser = (UsersModel) model.getAttribute("us");
    model.addAttribute("us", authenticatedUser);
    return "main_page";

}
}


    


