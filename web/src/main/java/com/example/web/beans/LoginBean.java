package com.example.web.beans;

import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named("LoginBean")
@RequestScoped
public class LoginBean {

    private static final Logger log = Logger.getLogger(LoginBean.class);

    private String email;
    private String password;
    private boolean loginEnabled = false;

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


    public void setLoginEnabled(boolean loginEnabled) {
        this.loginEnabled = loginEnabled;
    }

    public boolean isLoginEnabled() {
        return loginEnabled;
    }

    public void validateForm(ComponentSystemEvent event) {
        //todo: post submit validation
        log.info("VALIDATE FORM PHASE");

        FacesContext context = FacesContext.getCurrentInstance();
        loginEnabled = !context.isValidationFailed();
    }

    public void submit() {
        if (loginEnabled) {
            log.info("LOGIN WAS ENABLED");
        } else {
            log.info("LOGIN WAS DISABLED");
        }
//        if (validateForm()) {
//            //create token
//            System.out.println("SUCCESS");
//        } else {
//            log.error("Server validation error for email: " + email + " and password: " + password);
//            displayValidationError = true;
//        }
    }

}
