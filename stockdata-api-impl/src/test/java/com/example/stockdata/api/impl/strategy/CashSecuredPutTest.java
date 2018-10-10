package com.example.stockdata.api.impl.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@SpringBootTest
public class CashSecuredPutTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CashSecuredPut strategy;

    @Test
    public void runTest() {
        strategy.run();
    }


}
