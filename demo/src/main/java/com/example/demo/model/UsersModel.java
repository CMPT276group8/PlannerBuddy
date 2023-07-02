package com.example.demo.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "users_table")
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String username;
    String password;
    
    String passwordConfirm;

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

    private void checkPassword() {
    if(this.password == null || this.passwordConfirm == null){
        return;
    }else if(!this.password.equals(passwordConfirm)){
        this.passwordConfirm = null;
    }
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


}

