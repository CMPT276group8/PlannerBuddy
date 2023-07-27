package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String activity;
    private String activity2;
    private String activity3;
    private String Date;
    private String Date2;
    private String Date3;
    private Boolean completed;
    private String Place1;
    private String Place2;
    private String Place3; 

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UsersModel users;

    public Todo() {
        // Default constructor
    }

    public Todo(String activity) {
        this.activity = activity;
    }

    // Getters and setters
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }


    public String getActivity2() {
        return activity2;
    }

    public void setActivity2(String activity2) {
        this.activity2 = activity2;
    }

    public String getActivity3() {
        return activity3;
    }

    public void setActivity3(String activity3) {
        this.activity3 = activity3;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDate2() {
        return Date2;
    }

    public void setDate2(String date2) {
        Date2 = date2;
    }

    public String getDate3() {
        return Date3;
    }

    public void setDate3(String date3) {
        Date3 = date3;
    }

    public String getPlace1() {
        return Place1;
    }

    public void setPlace1(String place1) {
        Place1 = place1;
    }

   public String getPlace2() {
        return Place2;
    }

    public void setPlace2(String place2) {
        Place2 = place2;
    }
    public String getPlace3() {
        return Place3;
    }

    public void setPlace3(String place3) {
        Place3 = place3;
    }

    public UsersModel getUsers() {
        return users;
    }

    public void setUsers(UsersModel users) {
        this.users = users;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    

    


}
