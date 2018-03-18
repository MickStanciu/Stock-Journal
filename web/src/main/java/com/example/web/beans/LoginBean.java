package com.example.web.beans;

import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("LoginBean")
@RequestScoped
public class LoginBean {

    private static final Logger log = Logger.getLogger(LoginBean.class);

    private String email;
    private String password;
    private boolean displayValidationError = false;

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

    public boolean isDisplayValidationError() {
        return displayValidationError;
    }

    public void submit() {
//        if (validateForm()) {
//            //create token
//            System.out.println("SUCCESS");
//        } else {
//            log.error("Server validation error for email: " + email + " and password: " + password);
//            displayValidationError = true;
//        }
    }

}
