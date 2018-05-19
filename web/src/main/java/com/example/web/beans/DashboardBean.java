package com.example.web.beans;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("DashboardBean")
@RequestScoped
public class DashboardBean extends AbstractBean implements Serializable {

    private static final Logger log = Logger.getLogger(DashboardBean.class);


    @PostConstruct
    public void onLoad() {
        super.initAccount();
    }

}
