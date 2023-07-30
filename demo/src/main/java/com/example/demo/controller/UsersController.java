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
import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/verify")
    public String verifySession(HttpSession session) {
        // Check if the session is valid here
        UsersModel authenticatedUser = (UsersModel) session.getAttribute("us");
        if (authenticatedUser != null) {
            // Session is valid, redirect to the desired page
            return "redirect:/main/" + authenticatedUser.getId();
        } else {
            // Session is not valid, redirect to the login page
            return "redirect:/login";
        }
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
    public String login(
    @ModelAttribute("loginRequest") UsersModel usersModel,
    BindingResult bindingResult,
    Model model,
    HttpSession session
) {
    System.out.println("Username: " + usersModel.getUsername());
    System.out.println("Password: " + usersModel.getPassword());

    UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
    if (authenticated != null) {
        System.out.println("Authenticated user: " + authenticated.getUsername());
        if (authenticated.getRole().equalsIgnoreCase("admin")) {
            // Admin user authenticated, go to admin page
            List<UsersModel> users = usersRepository.findAll();
            model.addAttribute("us", users);
            return "admin_page";
        } else {
            // Successful login, perform the desired action
            String redirectUrl = "redirect:/main/" + authenticated.getId();
            System.out.println("Redirect URL: " + redirectUrl);
            session.setAttribute("us", authenticated);
            return redirectUrl;
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
    public String showCalendarById(Model model, HttpSession session, @PathVariable Integer id) {
        UsersModel authenticatedUser = (UsersModel) session.getAttribute("us");
        Optional<UsersModel> userOptional = usersRepository.findById(id);
        if (authenticatedUser != null && authenticatedUser.getId() == id){
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
            return "redirect:/login";
        }
    }
    
    @GetMapping("/chatbot/{id}")
    public String showChatbot(Model model, HttpSession session, @PathVariable Integer id) {
        UsersModel authenticatedUser = (UsersModel) session.getAttribute("us");
        Optional<UsersModel> userOptional = usersRepository.findById(id);
        if (authenticatedUser != null && authenticatedUser.getId() == id){
            UsersModel user = userOptional.get();
            model.addAttribute("us", user);
            return "chatbot_page";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/main/{id}")
    public String showById(Model model, @PathVariable Integer id, HttpSession session) {
    UsersModel authenticatedUser = (UsersModel) session.getAttribute("us");
    Optional<UsersModel> userOptional = usersRepository.findById(id);
    
    if (authenticatedUser != null && userOptional.isPresent() && authenticatedUser.getId() == id) {
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
                    temp2.setActivity2(todos2.get(i).getActivity2());
                    temp2.setCompleted(todos2.get(i).getCompleted());

                    //arr[i] = arr[j];
                    todos2.get(i).setUid(todos2.get(j).getUid());
                    todos2.get(i).setActivity2(todos2.get(j).getActivity2());
                    todos2.get(i).setCompleted(todos2.get(j).getCompleted());

                    //arr[j] = temp;
                    todos2.get(j).setUid(temp2.getUid());
                    todos2.get(j).setActivity2(temp2.getActivity2());
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
                    temp.setActivity3(todos3.get(i).getActivity3());
                    temp.setCompleted(todos3.get(i).getCompleted());

                    //arr[i] = arr[j];
                    todos3.get(i).setUid(todos3.get(j).getUid());
                    todos3.get(i).setActivity3(todos3.get(j).getActivity3());
                    todos3.get(i).setCompleted(todos3.get(j).getCompleted());

                    //arr[j] = temp;
                    todos3.get(j).setUid(temp.getUid());
                    todos3.get(j).setActivity3(temp.getActivity3());
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
        return "redirect:/login";
    }
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
    UsersModel authenticatedUser = (UsersModel) model.getAttribute("us");
    model.addAttribute("us", authenticatedUser);
    return "main_page";

    }
    
}


    


