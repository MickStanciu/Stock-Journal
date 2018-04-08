package com.example.web.beans;

import com.example.web.service.AccountService;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Optional;

@Named("DashboardBean")
@RequestScoped
public class DashboardBean {

    private static final Logger log = Logger.getLogger(DashboardBean.class);

    @Inject
    private AccountService accountService;


    public void onLoad() {
        System.out.println("ON LOAD");
    }

    private Optional<String> getGwCookieToken() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookies = context.getRequestCookieMap();
        if (cookies.containsKey("GW")) {
            return Optional.of((String) cookies.get("GW"));
            //todo: decode token
        } else {
            return Optional.empty();
        }
    }

}
