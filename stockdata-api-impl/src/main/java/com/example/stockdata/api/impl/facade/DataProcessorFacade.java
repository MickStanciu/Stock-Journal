package com.example.stockdata.api.impl.facade;

import org.springframework.stereotype.Service;


@Service
//@EnableScheduling
public class DataProcessorFacade {

//    @Scheduled(fixedRate = 2000)
    public void bip() {
        System.out.println("bip");
    }

}
