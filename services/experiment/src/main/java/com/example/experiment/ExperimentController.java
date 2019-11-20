package com.example.experiment;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ExperimentController {

    private ProductRevmanScoreDao productRevmanScoreDao;

    public ExperimentController(ProductRevmanScoreDao productRevmanScoreDao) {
        this.productRevmanScoreDao = productRevmanScoreDao;
    }

    @RequestMapping(value = "/test_1", method = RequestMethod.GET)
    public void check1() {
        productRevmanScoreDao.saveUpdate(createTestData());
    }

    @RequestMapping(value = "/test_2", method = RequestMethod.GET)
    public void check2() {
        productRevmanScoreDao.saveUpdateAlternative(createTestData());
    }

    @RequestMapping(value = "/test_3", method = RequestMethod.GET)
    public void check3() {
        productRevmanScoreDao.saveUpdateAlternative2(createTestData());
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String pong() {
        return "PONG";
    }

    private List<ProductScoreModel> createTestData() {
        List<ProductScoreModel> modelList = new ArrayList<>(1000);
        for (int index = 1; index < 500; index ++) {
            modelList.add(new ProductScoreModel("PRODUCT_CODE_" + index, "en", 1000 + index));
        }

        for (int index = 500; index < 1000; index ++) {
            modelList.add(new ProductScoreModel("PRODUCT_CODE_" + index, "en", 2000 + index));
        }
        return modelList;
    }
}
