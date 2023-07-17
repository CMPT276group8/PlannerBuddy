package com.example.demo.service;

//import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UsersRepository;
//import com.example.demo.repository.UsersRepository;
//import com.example.demo.model.UsersModel;
import com.example.demo.model.Todo;
import com.example.demo.model.UsersModel;

/**
public class TodoService {
    

    
    private TodoRepository todoRepository;
    //private UsersRepository usersRepository;

    public Todo todoList( String activity){
        
            Todo todo = new Todo(activity);
            
            todo.setActivity(activity);
            
            return todoRepository.save(todo);
        }
    

}**/

@Service
public class TodoService {
    private final UsersRepository userRepository;
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(UsersRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public void createTodoForUser(Integer id, Todo todo) {
        UsersModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUsers(user); // Establish the relationship

        todoRepository.save(todo); // Save the todo to the database
    }
}
