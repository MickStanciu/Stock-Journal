//package com.example.gateway.api.controller;
//
//import com.example.gateway.api.service.PricingService;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/api/v1/util", produces = "application/json")
//public class UtilController {
//
//    private PricingService pricingService;
//
//    public UtilController(PricingService pricingService) {
//        this.pricingService = pricingService;
//    }
//
//    @RequestMapping(value = "/price-update", method = RequestMethod.GET)
//    public void priceUpdate() {
//        pricingService.poke();
//    }
//}
