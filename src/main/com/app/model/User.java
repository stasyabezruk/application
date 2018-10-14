package com.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable{
    public static final String ROLE_MANAGER = "ADMIN";
    public static final String ROLE_EMPLOYEE = "CUSTOMER";

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String userRole;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
