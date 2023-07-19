package com.example.demo.model;

//import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    Integer id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    String role; 
    

    String passwordConfirm;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Todo> todos;


    public UsersModel(){

    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    public String getPasswordConfirm(){
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm){
        this.passwordConfirm = passwordConfirm;
        checkPassword();
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
        checkPassword();
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public boolean checkPassword() {
        if(this.password == null || this.passwordConfirm == null){
            return false;
        }
        else if(this.password == this.passwordConfirm){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersModel that = (UsersModel) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) 
        && Objects.equals(password, that.password);

    }

    @Override
    public int hashCode(){
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString(){
        return "UsersModel{" + "id"+ id +
                            ", username='" + username + '\''+
                            '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    


}

