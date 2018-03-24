package com.example.web.beans;

import com.example.web.gateway.AuthToken;
import com.example.web.gateway.GatewayApi;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    public String submit() {
        if (loginEnabled) {
            log.info("LOGIN WAS ENABLED");
            String tenantId = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
            String tpassword = "secret";
            String tname = "mircea.stanciu";
//            AuthToken authToken = gatewayApi.authenticate(tenantId, tname, tpassword);
            AuthToken authToken = new AuthToken("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCZW5kaXMiLCJzdWIiOiJhdXRoIiwidGVuYW50SWQiOiJkNzllYzExYS0yMDExLTQ0MjMtYmEwMS0zYWY4ZGUwYTNlMTAiLCJhY2NvdW50TmFtZSI6Im1pcmNlYS5zdGFuY2l1Iiwicm9sZU5hbWUiOiJMMSIsImlhdCI6MTUyMTU3OTAzOCwiZXhwIjoxNTIxNTgyNjM4fQ.56lQYWfS-EHnXNEF2dUkm_wHTKlKVcJ3AU9BKRL5Cz8");
            if (authToken != null) {
//                log.info(authToken.getToken());
                setCookie(authToken.getToken());
                return "success";
            } else {
                //todo: test this branch
                loginEnabled = false; //?
            }
        } else {
            log.info("LOGIN WAS DISABLED");
        }
        return "/xxx.xhtml";

//        if (validateForm()) {
//            //create token
//            System.out.println("SUCCESS");
//        } else {
//            log.error("Server validation error for email: " + email + " and password: " + password);
//            displayValidationError = true;
//        }
    }

    private void setCookie(String value) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", (int) TimeUnit.DAYS.toSeconds(1));
        properties.put("secure", false);
        properties.put("path","/");
        facesContext.getExternalContext().addResponseCookie("GW", value, properties);
    }

}
