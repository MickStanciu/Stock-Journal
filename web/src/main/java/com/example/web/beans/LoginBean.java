package com.example.web.beans;

import com.example.web.gateway.AuthToken;
import com.example.web.gateway.GatewayApi;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named("LoginBean")
@RequestScoped
public class LoginBean {

    private static final Logger log = Logger.getLogger(LoginBean.class);

    private String email;
    private String password;
    private boolean loginEnabled = false;

    @Inject
    private GatewayApi gatewayApi;

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

        //todo: invoke custom validator to attempt login
        loginEnabled = !context.isValidationFailed();
    }

    public void submit() {
        if (loginEnabled) {
            log.info("LOGIN WAS ENABLED");
            String tenantId = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
            String tpassword = "secret2333";
            String tname = "mircea.stanciu4444";
            AuthToken authToken = gatewayApi.authenticate(tenantId, tname, tpassword);
            if (authToken == null) {
                log.info("plm");
            } else {
                log.info(authToken.getToken());
            }
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
