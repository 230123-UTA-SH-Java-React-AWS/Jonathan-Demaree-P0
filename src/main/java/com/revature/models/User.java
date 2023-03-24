package com.revature.models;

public class User {

    public enum Role {
        EMPLOYEE("EMPLOYEE"),
        MANAGER("MANAGER");

        Role(String role) {
        };
    }


    private int userId;

    private String email;
    private String password;

    private Role role;
    

    public User() {
    }
    
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.EMPLOYEE;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String userEmail) {
        email = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String userPassword) {
        password = userPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [Email: " + email + ", User ID =" + userId +  ", Role: " + role + ", Password: " + password +"]";
    }
}
