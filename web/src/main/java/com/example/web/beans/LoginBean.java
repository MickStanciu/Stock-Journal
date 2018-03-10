package com.example.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("LoginBean")
@RequestScoped
public class LoginBean {

    private String email;
    private String password;

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

    public void submit() {
        System.out.println("PLM");
        System.out.println("Email: " + email);
        System.out.println("Passw: " + password);
    }
}
