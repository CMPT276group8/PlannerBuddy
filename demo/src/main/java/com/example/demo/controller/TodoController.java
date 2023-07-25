package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Todo;

import java.util.List;
//import java.util.Map;
import java.util.Optional;

import com.example.demo.repository.TodoRepository;
//import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.UsersRepository;

//import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.service.TodoService;
import com.example.demo.model.Todo;
import com.example.demo.model.UsersModel;

@Controller
public class TodoController {

    //private UsersRepository userRepo;

    @Autowired
    private TodoService todoService;
    
    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository, UsersRepository usersRepository) {
        this.todoRepository = todoRepository;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/users/add")
        public String addUser(@RequestParam("activity") String activity, @RequestParam("date1") String date, @RequestParam("id") Integer id) {
        Todo todo = new Todo();
        todo.setActivity(activity);
        todo.setDate(date);
        todo.setCompleted(false);
        todoService.createTodoForUser(id, todo);
        return "redirect:/main/" + id;
    }

    @PostMapping("/users/add2")
        public String addUser2(@RequestParam("activity2") String activity2, @RequestParam("date2") String date, @RequestParam("id") Integer id) {
        Todo todo = new Todo();
        todo.setActivity2(activity2);
        todo.setDate2(date);
        todo.setCompleted(false);
        todoService.createTodoForUser(id, todo);
        return "redirect:/main/" + id;
    }

    @PostMapping("/users/add3")
        public String addUser3(@RequestParam("activity3") String activity3,@RequestParam("date3") String date3,@RequestParam("id") Integer id) {
        Todo todo = new Todo();
        todo.setActivity3(activity3);
        todo.setDate3(date3); // Set the date for the task
        todo.setCompleted(false);
        todoService.createTodoForUser(id, todo);
        return "redirect:/main/" + id;
    }

    @PostMapping("/date3")
        public String dateUser3(@RequestParam("id") Integer id, @RequestParam("uid") String uid, @RequestParam("date3") String date3, @RequestParam("activity3") String activity3) {
        // Optional<UsersModel> userOptional = usersRepository.findById(id);
        // if (userOptional.isPresent()) {
        //     Optional<Todo> todoOptional = todoRepository.findById(Integer.parseInt(uid));
        //     Todo todo = todoOptional.get();
        //     System.out.println("todo is " + todo.getUid());
        //     System.out.println("todo is " + todo.getActivity3());
        //     todo.setDate3(date3);
        //     todoRepository.save(todo);
        //     return "redirect:/main/" + id;
        // } else {
        //     return "redirect:/error";
        // }
        todoRepository.deleteById(Integer.parseInt(uid)); //delete user by id
        Todo todo = new Todo();
        todo.setActivity3(activity3);
        todo.setDate3(date3); // Set the date for the task
        todo.setCompleted(false);
        todoService.createTodoForUser(id, todo);
        return "redirect:/main/" + id;
    }

    @DeleteMapping("/todo/delete/{uid}")
        public String deleteById(@PathVariable("uid") int uid) {
        //System.out.println(uid);
        todoRepository.deleteById(uid); //delete user by id
        return "main_page";
    }


    @GetMapping("/td1/{id}")
    public String showTodoByUser(Model model, @PathVariable Integer id) {
    Optional<UsersModel> userOptional = usersRepository.findById(id);
    if (userOptional.isPresent()) {
        UsersModel user = userOptional.get();
        List<Todo> todos = todoRepository.findByUsers(user);
        model.addAttribute("us", user);
        model.addAttribute("todos", todos);
        return "main_page";
    } else {
        // Handle the case where the user with the specified ID is not found
        // You can redirect to an error page or perform any other desired action
        return "redirect:/error";
    }
    }

    /**@GetMapping("/chatbot")
    public String getChatbotPage(Model model) {
        model.addAttribute("chatbotRequest", new UsersModel());
        return "chatbot_page";
    }**/

    @RequestMapping(value = "/setTrue", method = RequestMethod.POST)
    public ResponseEntity<String> updateCompletionStatus(@RequestParam("uid") Integer uid, @RequestParam("completed") boolean completed) {
        Optional<Todo> todoOptional = todoRepository.findById(uid);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setCompleted(completed);
            todoRepository.save(todo); // Save the updated entity to the database
            return ResponseEntity.ok().build();
        } else {
            // Todo not found, return an error response
            return ResponseEntity.notFound().build();
        }
    }
}





  


